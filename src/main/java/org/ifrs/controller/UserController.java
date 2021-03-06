
package org.ifrs.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import org.ifrs.adapter.UserAdapter;
import org.ifrs.auth.TokenUtils;
import org.ifrs.entity.Error;
import org.ifrs.entity.User;
import org.ifrs.model.EditUserModel;
import org.ifrs.model.UserModel;
import org.ifrs.service.UserService;
import org.ifrs.view.UserInfoView;
import org.ifrs.view.UserView;

@Path("user")
public class UserController {
    UserService userService = new UserService();

    @Inject
    JsonWebToken token;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response listAll() {
        try {
            List<UserView> users = userService.listAll()
                .stream()
                .map(user -> new UserAdapter(user).mapEntityToView())
                .collect(Collectors.toList());

            return Response.ok(users).build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @GET
    @Path("info")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "User" })
    public Response getUserInfo() {
        try {
            Long userId = TokenUtils.getUserId(token);
            User user = userService.getById(userId);

            UserInfoView userInfoView = new UserAdapter(user).mapEntityToInfoView();

            return Response.ok(userInfoView).build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public UserView getById(@PathParam("id") Long id) {
        User user = userService.getById(id);

        if (user == null) {
            return null;
        }

        UserView userView = new UserAdapter(user).mapEntityToView();

        return userView;
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response create(@Valid UserModel user) {
        try {
            UserView userView = new UserAdapter(userService.create(user)).mapEntityToView();
            return Response.ok(userView).build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

    @PUT
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "User" })
    public Response update(@Valid EditUserModel user) {
        try {
            Long userId = TokenUtils.getUserId(token);
            userService.update(user, userId);

            return Response.ok().build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

}
