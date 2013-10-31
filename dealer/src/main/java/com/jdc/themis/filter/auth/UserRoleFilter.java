package com.jdc.themis.filter.auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserRoleFilter implements Filter {

  @Override
  public void init(FilterConfig arg0) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain next)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpSession session = request.getSession(false);
 
    String userAlias = (null == session) ? null : (String) session.getAttribute("userAlias");
    String userRole =
        (null == session) ? null : (String) session.getAttribute("userRole");

    next.doFilter(new UserRoleRequestWrapper(request, userAlias, userRole), res);
  }

  @Override
  public void destroy() {
  }
}
