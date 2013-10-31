package com.jdc.themis.filter.auth;

import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.jdc.themis.dealer.utils.Constant;

@Resource
public class UserRoleRequestWrapper extends HttpServletRequestWrapper {

  String user ;
  String role = null;
  HttpServletRequest request;

  public UserRoleRequestWrapper(HttpServletRequest request, String user, String role) {
    super(request);

    this.request = request;
    this.user = user;
    this.role = role;
  }

  @Override
  public boolean isUserInRole(String role) {
    if (this.role == null) {
      // If no role on current user, treat him as a guest.
      if (Constant.GUEST_ROLE.equals(role))
      {
        return true;
      }
      return this.request.isUserInRole(role);
    }
    return this.role.equals(role);
  }

  @Override
  public Principal getUserPrincipal() {
    return new Principal() {
      @Override
      public String getName() {
        return user;
      }
    };
  }
}
