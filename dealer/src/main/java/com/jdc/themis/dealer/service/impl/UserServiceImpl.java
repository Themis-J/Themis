package com.jdc.themis.dealer.service.impl;

import javax.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.jdc.themis.dealer.data.dao.UserDAO;
import com.jdc.themis.dealer.domain.UserInfo;
import com.jdc.themis.dealer.service.RefDataQueryService;
import com.jdc.themis.dealer.service.UserService;
import com.jdc.themis.dealer.utils.Performance;
import com.jdc.themis.dealer.web.domain.AddNewUserRequest;
import com.jdc.themis.dealer.web.domain.GetUserInfoResponse;
import com.jdc.themis.dealer.web.domain.ModifyUserRequest;
import com.jdc.themis.dealer.web.domain.ResetPasswordRequest;

/**
 * Service layer to manage user information.
 * 
 * @author Kai Chen
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAL;
	public void setUserDAL(UserDAO userDAL) {
		this.userDAL = userDAL;
	}

	@Autowired
	private RefDataQueryService refDataQueryDAL;

	public void setRefDataQueryDAL(RefDataQueryService refDataQueryDAL) {
		this.refDataQueryDAL = refDataQueryDAL;
	}

	@Override
	@Performance
	public Instant addNewUser(final AddNewUserRequest request) {
		Preconditions.checkNotNull(request.getUsername(), "user name can't be null");
		Preconditions.checkArgument(userDAL.getUser(request.getUsername()).isNone(), "user name already exists");
		Preconditions.checkNotNull(request.getPassword(), "password can't be null");
		Preconditions.checkNotNull(request.getUserRole(), "user role can't be null");
		
		final UserInfo user = new UserInfo();
		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword());
		user.setActive(Boolean.TRUE);
		if ( request.getDealerID() != null ) {
			user.setDealerID(refDataQueryDAL.getDealer(request.getDealerID()).getId());
		} 
		return userDAL.saveOrUpdateUser(user);
	}

	@Override
	@Performance
	public Instant disableUser(final String username) {
		Preconditions.checkNotNull(username, "username can't be null");
		Preconditions.checkArgument(userDAL.getUser(username).isSome(), "unknown user name");

		final UserInfo user = userDAL.getUser(username).some();
		user.setActive(Boolean.FALSE);
		return userDAL.saveOrUpdateUser(user);
	}

	@Override
	@Performance
	public GetUserInfoResponse getUser(final String username) {
		Preconditions.checkNotNull(username, "username can't be null");
		Preconditions.checkArgument(userDAL.getUser(username).isSome(), "unknown user name");

		final GetUserInfoResponse response = new GetUserInfoResponse();
		response.setUsername(username);
		if ( userDAL.getUser(username).some().getDealerID() != null ) {
			response.setDealer(refDataQueryDAL.getDealer(userDAL.getUser(username).some().getDealerID()));
		} 
		response.setRole(userDAL.getUserRole(userDAL.getUser(username).some().getUserRoleID()).some().getName());
		return response;
	}

	@Override
	public Instant enableUser(final String username) {
		Preconditions.checkNotNull(username, "username can't be null");
		Preconditions.checkNotNull(userDAL.getUser(username).isSome(), "unknown user name");

		final UserInfo user = userDAL.getUser(username).some();
		user.setActive(Boolean.TRUE);
		return userDAL.saveOrUpdateUser(user);
	}

	@Override
	public Instant resetPassword(final ResetPasswordRequest request) {
		Preconditions.checkNotNull(request.getUsername(), "user name can't be null");
		Preconditions.checkNotNull(request.getOldPassword(), "old password can't be null");
		Preconditions.checkNotNull(request.getNewPassword(), "new password can't be null");
		Preconditions.checkArgument(userDAL.getUser(request.getUsername()).isSome(), "user name does not exists");
		Preconditions.checkArgument(!request.getNewPassword().equals(request.getOldPassword()), 
				"old password equals to the new password");
		Preconditions.checkArgument(request.getOldPassword().equals(userDAL.getUser(request.getUsername()).some().getPassword()), 
				"password doesn't match");
		
		final UserInfo user = userDAL.getUser(request.getUsername()).some();
		user.setPassword(request.getNewPassword());
		return userDAL.saveOrUpdateUser(user);
	}

	@Override
	public Instant modifyUser(ModifyUserRequest request) {
		Preconditions.checkNotNull(request.getUsername(), "user name can't be null");
		Preconditions.checkNotNull(request.getPassword(), "password can't be null");
		Preconditions.checkArgument(userDAL.getUser(request.getUsername()).isSome(), "user name does not exists");
		Preconditions.checkArgument(request.getPassword().equals(userDAL.getUser(request.getUsername()).some().getPassword()), 
				"password doesn't match");
		
		final UserInfo user = userDAL.getUser(request.getUsername()).some();
		user.setDealerID(request.getDealerID());
		if ( request.getUserRole() != null ) {
			user.setUserRoleID(request.getUserRole());
		} 
		return userDAL.saveOrUpdateUser(user);
	}

}
