package com.jdc.themis.dealer.service.rest;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jdc.themis.dealer.service.UserService;
import com.jdc.themis.dealer.web.domain.GetUserInfoResponse;
import com.jdc.themis.dealer.web.domain.LoginRequest;
import com.jdc.themis.dealer.utils.Constant;

@Service
@Path("/guest")
@RolesAllowed({Constant.GUEST_ROLE})
public class GuestRestService {
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
        return Response.status(Status.FORBIDDEN).build();
      }

      HttpSession session = req.getSession(true);
      session.setAttribute("userAlias", user.getUsername());
      session.setAttribute("userRole", user.getRole());
    } catch (Exception e) {
      return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }

    return Response.ok(user).build();
  }
}
