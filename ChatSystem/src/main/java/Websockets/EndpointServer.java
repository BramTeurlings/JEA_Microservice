package Websockets;

import models.AuthenticationFilter;
import Models.Kweet;
import models.LoginResponse;
import Service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(
        value = "/main-socket/{token}",
        encoders = JsonEncoder.class,
        decoders = JsonDecoder.class,
        configurator = HttpSessionProvider.class
)
public class EndpointServer {
    private static final Logger LOG = Logger.getLogger(EndpointServer.class.getName());

    @Inject
    ConnectionTestBean ECHO_BEAN;

    @Inject
    ChatService service;

    private AuthenticationFilter filter = new AuthenticationFilter();

    private HttpSession httpSession;

    private ObjectMapper mapper = new ObjectMapper();

    private Session session;

    public EndpointServer(){
        this.LOG.setLevel(Level.ALL);
    }

    @OnOpen
    public void onOpen(EndpointConfig endpointConfig, Session session){
        this.httpSession = HttpSessionProvider.provide(endpointConfig);
        this.session = session;
        LOG.log(Level.ALL, "onOpen: endpointConfig: {0}, session: {1}", new Object[]{endpointConfig, session});
    }

    @OnMessage
    public void onMessage(Session session, Message message){
        String username = parseSession(session);

        //We are going to assume that the username is correct since the sessions was validated
        //user = userService.getUserByUsername(user.getUsername());

        //Check which followers are live at the moment and send them the message.
        List<Session> liveSessions = liveSessions();

        //Todo: Parse message to proper kweet, append kweet to database, display kweet in client.
        Kweet kweet = new Kweet();

        kweet.setMessage(message.getText());
        kweet.setAuthor(username);
        kweet.setTimestamp(new Date());

        //Add kweet to database
        service.addKweet(kweet);

        //Send to clients
        LOG.log(Level.ALL, "received message with text: {0}", message.getText());
        for(Session liveSession : liveSessions){
            ECHO_BEAN.send(liveSession, kweet, 0, 1000, 1.2);
        }

    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason){
        LOG.log(Level.ALL, "session {0} closed with reason {1}", new Object[]{session, closeReason});
        try{
            //httpSession.invalidate();
            int i =session.getOpenSessions().size();
            System.out.println("Test");
            //session.close();
        } catch(IllegalStateException ise){
            //swallow: httpSession already expired
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable){
        LOG.log(
                Level.WARNING,
                new StringBuilder("an error occured for session ").append(session).toString(),
                throwable
        );
    }

    public String parseSession(Session session){
        //Check if token valid.
        try {
            //Convert to login object:
            String token = session.getRequestURI().toString().replace("ChatSystem-1.0-SNAPSHOT/main-socket/", "").replace("/", "");
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            String loginResponse = mapper.writeValueAsString(response);
            String username = filter.validateToken(loginResponse);
            return username;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Session> liveSessions(){
        List<Session> liveSessions = new ArrayList<>();

        for(Session sessions : session.getOpenSessions()){
            try{
                liveSessions.add(sessions);
            }catch (Exception e){
                System.out.println("User's token is expired");
            }
        }

        return liveSessions;
    }

    public Session getSession() {
        return session;
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }

}
