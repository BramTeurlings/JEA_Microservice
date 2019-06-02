/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Websockets;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * Sets a handshaking user's {@link HttpSession} in the {@link ServerEndpointConfig#getUserProperties()}
 * under {@link HttpSession}'s FQN
 * @author jgeenen
 */
public class HttpSessionProvider extends ServerEndpointConfig.Configurator{
    
    public static HttpSession provide(EndpointConfig config){
        return (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        config.getUserProperties().put(HttpSession.class.getName(), request.getHttpSession());
    }
    
}
