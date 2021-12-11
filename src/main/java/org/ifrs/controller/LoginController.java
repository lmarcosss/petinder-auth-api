package org.ifrs.controller;

import javax.annotation.security.PermitAll;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ifrs.auth.TokenUtils;
import org.ifrs.entity.Error;
import org.ifrs.model.AuthModel;
import org.ifrs.model.LoginModel;
import org.ifrs.service.UserService;

@Path("login")
public class LoginController {
    UserService userService = new UserService();

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response login(@Valid LoginModel login) {
        try {
            Long userId = userService.login(login);

            TokenUtils.generateToken(login.email, userId);

            AuthModel auth = new AuthModel();
            auth.token = TokenUtils.generateToken(login.email, userId);
            auth.tokenType = "Bearer";
            return Response.ok(auth).build();
        } catch (ClientErrorException e) {
            return new Error().toResponse(e);
        }
    }

}
