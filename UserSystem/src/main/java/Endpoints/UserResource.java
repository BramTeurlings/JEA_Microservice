package Endpoints;

import Models.Secured;
import Models.User;
import Service.KwetterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationPath("/api")
@Path("/user")
public class UserResource {
    //HATEOAS CODE:

    @Context
    private UriInfo uriInfo;

    @Inject
    KwetterService service;

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
