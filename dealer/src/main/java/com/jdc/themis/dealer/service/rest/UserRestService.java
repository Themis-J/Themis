package com.jdc.themis.dealer.service.rest;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.jdc.themis.dealer.service.UserService;
import com.jdc.themis.dealer.utils.Constant;
import com.jdc.themis.dealer.utils.RestServiceErrorHandler;
import com.jdc.themis.dealer.utils.Utils;
import com.jdc.themis.dealer.web.domain.AddNewUserRequest;
import com.jdc.themis.dealer.web.domain.GeneralResponse;
import com.jdc.themis.dealer.web.domain.GetUserInfoResponse;
import com.jdc.themis.dealer.web.domain.ResetPasswordRequest;

@Produces("application/json")
@RolesAllowed({Constant.DEALER_ROLE, Constant.HEAD_ROLE, Constant.ADMIN_ROLE, Constant.SUPER_ROLE})
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
	@Path("/roles")
	@RestServiceErrorHandler
	public Response getUserRoles() {
		return Response.ok(userService.getUserRoles()).build();
	}
	
	/**
	 * Query user information.
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("/info")
	@RestServiceErrorHandler
	public Response getUser(@QueryParam("username") String username) {
		return Response.ok(userService.getUser(username)).build();
	}

	@POST
	@Path("/add")
	@RolesAllowed({Constant.ADMIN_ROLE, Constant.SUPER_ROLE})
	@RestServiceErrorHandler
	public Response addNewUser(final AddNewUserRequest request,
			@Context HttpServletRequest req) {
		final GeneralResponse response = new GeneralResponse();
		response.setErrorMsg("");
		response.setSuccess(true);
		GetUserInfoResponse curUser = userService.getCurrentUser(req);
		
		if (!Constant.SUPER_ROLE.equals(curUser.getRole()) &&
				(request.getUserRole()==0))
		{
			response.setSuccess(false);
			response.setErrorMsg("PERMISSION_DENIED");
			return Response.status(500).entity(response).build();
		}
		userService.addNewUser(request);
		response.setTimestamp(Utils.currentTimestamp());
		return Response.ok(response).build();
	}
	
	@POST
	@Path("/disable")
	@RolesAllowed({Constant.ADMIN_ROLE, Constant.SUPER_ROLE})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@RestServiceErrorHandler
	public Response disableUser(@QueryParam("username") String username,
			@Context HttpServletRequest req) {
		final GeneralResponse response = new GeneralResponse();
		response.setErrorMsg("");
		response.setSuccess(true);
		GetUserInfoResponse curUser = userService.getCurrentUser(req);
		GetUserInfoResponse userToDisable = userService.getUser(username);
		
		if (Constant.SUPER_ROLE.equals(userToDisable.getRole()) &&
				!Constant.SUPER_ROLE.equals(curUser.getRole()))
		{
			response.setSuccess(false);
			response.setErrorMsg("PERMISSION_DENIED");
			return Response.status(500).entity(response).build();
		}
		
		try
		{
			userService.disableUser(username);
		}
		catch(Exception e)
		{
			response.setSuccess(false);
			response.setErrorMsg("failed");
			return Response.status(500).entity(response).build();
		}
		
		response.setTimestamp(Utils.currentTimestamp());
		return Response.ok(response).build();
	}
	
	@POST
	@Path("/enable")
	@RolesAllowed({Constant.ADMIN_ROLE, Constant.SUPER_ROLE})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@RestServiceErrorHandler
	public Response enableUser(@QueryParam("username") String username,
			@Context HttpServletRequest req) {
		final GeneralResponse response = new GeneralResponse();
		response.setErrorMsg("");
		response.setSuccess(true);
		GetUserInfoResponse curUser = userService.getCurrentUser(req);
		GetUserInfoResponse userToDisable = userService.getUser(username);
		
		if (Constant.SUPER_ROLE.equals(userToDisable.getRole()) &&
				!Constant.SUPER_ROLE.equals(curUser.getRole()))
		{
			response.setSuccess(false);
			response.setErrorMsg("PERMISSION_DENIED");
			return Response.status(500).entity(response).build();
		}
		
		try
		{
			userService.enableUser(username);
		}
		catch(Exception e)
		{
			response.setSuccess(false);
			response.setErrorMsg("failed");
			return Response.status(500).entity(response).build();
		}
		
		response.setTimestamp(Utils.currentTimestamp());
		return Response.ok(response).build();
	}

	@POST
	@Path("/resetpwd")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@RestServiceErrorHandler
	public Response resetPassword(final ResetPasswordRequest request) {
		final GeneralResponse response = new GeneralResponse();
		response.setErrorMsg("");
		response.setSuccess(true);
		userService.resetPassword(request);
		response.setTimestamp(Utils.currentTimestamp());
		return Response.ok(response).build();
	}
}
