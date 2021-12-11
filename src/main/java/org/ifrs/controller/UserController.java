
package org.ifrs.controller;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.ifrs.entity.Error;
import org.ifrs.model.UserModel;
import org.ifrs.service.UserService;

@Path("user")
public class UserController {
    UserService userService = new UserService();

    @Inject
    JsonWebToken token;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "User" })
    public Response listAll() {
        try {
            return Response.ok(userService.listAll()).build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "User" })
    public Response getById(@PathParam("id") Long id) {
        try {
            return Response.ok(userService.getById(id)).build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response create(@Valid UserModel user) {
        try {
            return Response.ok(userService.create(user)).build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @PUT
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "User" })
    public Response update(@Valid UserModel user) {
        try {
            System.out.println(token.claim("userId").get());
            Long userId = Long.parseLong(token.claim("userId").get().toString());
            userService.update(user, userId);

            return Response.ok().build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

}