package Endpoints;

import Models.Kweet;
import Service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Secured;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ApplicationPath("/api")
@Stateless
@Path("/chat")
public class ChatResource extends Application {
    //HATEOAS CODE:

    @Context
    private UriInfo uriInfo;

    @Inject
    ChatService service;

    private ObjectMapper mapper = new ObjectMapper();

    @GET
    @Secured
    @Produces("application/json")
    public Response getIndex(@Context UriInfo uriInfo){
        List<Kweet> kweets = service.getKweets();
        kweets.forEach(p -> initLinks(p, uriInfo));

        GenericEntity<List<Kweet>> genericEntity =
                new GenericEntity<List<Kweet>>(kweets) {};

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
    @Secured
    @Path("addKweet")
    @Consumes("application/json")
    public void addKweet(@QueryParam("content") String message,
                         @QueryParam("author") String author){
        Kweet kweet = new Kweet();
        kweet.setMessage(message);
        kweet.setAuthor(author);
        kweet.setTimestamp(new Date());
        service.addKweet(kweet);

    }

    @GET
    @Secured
    @Path("/all")
    @Produces("application/json")
    public String getAll(){
        List<Kweet> kweets = service.getKweets();
        try {
            return new ObjectMapper().writeValueAsString(kweets);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @GET
    @Secured
    @Path("/searchTerm/{term}")
    @Produces("application/json")
    public String searchKweet(@PathParam("term") String term){
        try {
            return new ObjectMapper().writeValueAsString(service.searchKweet(term));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void initLinks(Kweet person, UriInfo uriInfo) {
        //create self link
        UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();
        uriBuilder = uriBuilder.path(Integer.toString(person.getId()));
        Link.Builder linkBuilder = Link.fromUriBuilder(uriBuilder);
        Link selfLink = linkBuilder.rel("self").build();
        //also we can add other meta-data by using: linkBuilder.param(..),
        // linkBuilder.type(..), linkBuilder.title(..)
        person.setSelfLinks(Arrays.asList(selfLink));
    }
}
