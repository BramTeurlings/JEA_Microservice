package Endpoints;

import Authentication.AuthenticationUtils;
import Models.Secured;
import Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Models.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

@ApplicationPath("/api")
@Path("/user")
public class UserResource extends Application {
    //HATEOAS CODE:

    @Context
    private UriInfo uriInfo;

    @Inject
    UserService service;

    private ObjectMapper mapper = new ObjectMapper();

    @GET
    @Secured
    @Produces("application/json")
    public Response getIndex(@Context UriInfo uriInfo){
        List<User> users = service.getUsers();
        users.forEach(u -> initLinks(u, uriInfo));

        GenericEntity<List<User>> genericEntity =
                new GenericEntity<List<User>>(users) {};

        Link self = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
                .rel("self").build();
        Link allLink = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder()
                .path("/all")).build();
        Link searchLink = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder()
                .path("/searchTerm")).build();

        return Response.ok(genericEntity)
                .links(self, allLink, searchLink).build();
    }

    @POST
    @Path("register")
    @Consumes(APPLICATION_FORM_URLENCODED)
    public void addUser(@FormParam("username") String username,
                        @FormParam("password") String password
    ) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = new User(username, AuthenticationUtils.encodeSHA256(password));
        service.addUser(user);
    }

    @GET
    @Secured
    @Path("/all")
    @Produces("application/json")
    public String getUsers(){
        List<User> users = service.getUsers();
        try {
            return new ObjectMapper().writeValueAsString(users);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @GET
    @Secured
    @Path("{username}")
    @Produces("application/json")
    public String getUser(@PathParam("username") String username){
        try {
            return new ObjectMapper().writeValueAsString(service.searchUser(username));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }


    private void initLinks(User user, UriInfo uriInfo) {
        //create self link
        UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();
        uriBuilder = uriBuilder.path(user.getUsername());
        Link.Builder linkBuilder = Link.fromUriBuilder(uriBuilder);
        Link selfLink = linkBuilder.rel("self").build();
        //also we can add other meta-data by using: linkBuilder.param(..),
        // linkBuilder.type(..), linkBuilder.title(..)
        user.setSelfLinks(Arrays.asList(selfLink));
    }
}
