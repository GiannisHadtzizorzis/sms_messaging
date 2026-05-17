package dev.giannishadjizorzis;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@ApplicationScoped
@Path("/api/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

    @Inject
    MessageService messageService;

    @POST
    public Response sendMessage(@Valid SendMessageRequest request) {
        SendMessageResponse response = messageService.sendMessage(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    public Response listMessages() {
        return Response.ok(messageService.listAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getMessage(@PathParam("id") UUID id) {
        return messageService.findById(id)
                .map(msg -> Response.ok(msg).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
