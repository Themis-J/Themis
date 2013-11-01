package com.jdc.themis.dealer.service.rest;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import com.jdc.themis.dealer.service.UserService;
import com.jdc.themis.dealer.utils.Constant;
import com.jdc.themis.dealer.web.domain.GetUserInfoResponse;
import com.jdc.themis.dealer.web.domain.LoginRequest;

@Path("/")
@Produces("application/json")
@RolesAllowed({Constant.GUEST_ROLE, Constant.DEALER_ROLE, Constant.HEAD_ROLE, Constant.ADMIN_ROLE,
    Constant.SUPER_ROLE})
public class AuthRestService {
  @Autowired
  private UserService userService;

  @Context
  private HttpServletRequest req;

  @POST
  @Path("/login")
  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response login(LoginRequest loginRequest) {
    GetUserInfoResponse user = null;

    try {
      user = userService.getUser(loginRequest.getUsername());

      if (null == user) {
        return Response.status(Status.NOT_FOUND).build();
      }

      if (!userService.authVerity(loginRequest.getUsername(), loginRequest.getPassword())) {
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
      }

      HttpSession session = req.getSession(true);
      session.setAttribute("userAlias", user.getUsername());
      session.setAttribute("userRole", user.getRole());
    } catch (Exception e) {
      return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

    return Response.ok(user).build();
  }

  @GET
  @Path("/isAlive")
  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response isAlive() {
    GetUserInfoResponse user = null;
    try {
      user = userService.getCurrentUser(req);

      if (null == user) {
        return Response.status(Status.UNAUTHORIZED).build();
      }
    } catch (Exception e) {
      return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

    return Response.ok(user).build();
  }

  @POST
  @Path("/logout")
  public Response logout() {
    GetUserInfoResponse user = null;
    try {
      user = userService.getCurrentUser(req);

      if (null == user) {
        return Response.status(Status.OK).build();
      }
      HttpSession session = req.getSession(false);

      if (null != session) {
        session.invalidate();
      }
    } catch (Exception e) {
      return Response.status(Status.UNAUTHORIZED).build();
    }

    return Response.status(Status.OK).build();
  }
  
}
