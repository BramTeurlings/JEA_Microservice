package Endpoints;

import models.SimpleKeyGenerator;
import Models.LoginResponse;
import Service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import models.User;
import org.glassfish.jersey.process.internal.RequestScoped;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

@ApplicationPath("/api")
@Stateless
@Path("/authentication")
public class AuthenticationResource extends Application{
    //HATEOAS CODE:

    @Context
    private UriInfo uriInfo;

    @Inject
    AuthenticationService service;

    @Inject
    private SimpleKeyGenerator keyGenerator;

    private ObjectMapper mapper = new ObjectMapper();

    @POST
    @Path("/login")
    @Consumes(APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("username") String username,
                                     @FormParam("password") String password) {
        try {

            // Authenticate the user using the credentials provided
            User user = authenticate(username, password);

            // Issue a token for the user
            String token = issueToken(username);

            // Return the token on the response
            return Response.ok().entity(new LoginResponse(token, user)).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private User authenticate(String username, String password) throws Exception {
        User user = service.authenticateUser(username, password);

        if (user == null){
            throw new SecurityException("Invalid user/password");
        }

        return user;
    }

    private String issueToken(String login) {
        Key key = keyGenerator.generateKey();
        return  Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
