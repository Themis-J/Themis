package com.jdc.themis.dealer.service.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jdc.themis.dealer.service.UserService;
import com.jdc.themis.dealer.utils.RestServiceErrorHandler;

@Service
public class UserRestService {

	@Autowired
	private UserService userService;

	/**
	 * Query user information.
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("/info")
	@Produces({ "application/json", "application/xml" })
	@RestServiceErrorHandler
	public Response getUser(@QueryParam("username") String username) {
		return Response.ok(
				userService.getUser(username)).build();
	}
}
