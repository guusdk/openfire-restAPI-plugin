package org.jivesoftware.openfire.plugin.rest.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jivesoftware.openfire.plugin.rest.controller.MessageController;
import org.jivesoftware.openfire.plugin.rest.entity.MessageEntity;
import org.jivesoftware.openfire.plugin.rest.exceptions.ServiceException;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("restapi/v1/messages")
@Tag(name = "Message", description = "Sending (chat) messages to users.")
public class MessageService {

    private MessageController messageController;

    @PostConstruct
    public void init() {
        messageController = MessageController.getInstance();
    }

    @POST
    @Path("/users")
    @Operation( summary = "Broadcast",
        description = "Sends a message to all users that are currently online.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Message is sent."),
            @ApiResponse(responseCode = "400", description = "The message content is empty or missing."),
        })
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response sendBroadcastMessage(@RequestBody(description = "The message that is to be broadcast.", required = true) MessageEntity messageEntity)
        throws ServiceException
    {
        messageController.sendBroadcastMessage(messageEntity);
        return Response.status(Response.Status.CREATED).build();
    }
}
