package com.sample.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sample.request.IdentityRequest;
import com.sample.request.RefreshTokenRequest;
import com.sample.response.IdentityResponse;
import com.sample.service.ILoginService;
import com.sample.util.RESTEndpointMapper;

@RestController
public class LoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	ILoginService loginService;
	
	/**
	 * This endpoint is used to do SignIn/Login and get AuthCode/AccessToken
	 * 
	 * @author RV
	 * @param identityRequest
	 * @return ResponseEntity<IdentityResponse>
	 */
	@PostMapping(value = RESTEndpointMapper.LOGIN_ACCESS_TOKEN)
	public ResponseEntity<IdentityResponse> doLogin(@RequestBody IdentityRequest identityRequest) {
		LOGGER.info("In Login: Attempting Login...");
		
		IdentityResponse identityResponse = null;
		try {
			identityResponse = loginService.getAccessToken(identityRequest);
			LOGGER.info("Authentication Successful : Access Token Generated");
		
		} catch (Exception e) {
			LOGGER.info("Authentication Failed!");
			LOGGER.debug("Authentication Failed!"+e.getStackTrace());
		}
		
		return new ResponseEntity<IdentityResponse>(identityResponse, HttpStatus.OK);
	}
	
	/**
	 * This endpoint is used to do retrieve fresh the expired access-token with the help of refresh-tokens
	 * 
	 * @author RV
	 * @param identityRequest
	 * @return ResponseEntity<IdentityResponse>
	 */
	@PostMapping(value = RESTEndpointMapper.LOGIN_ACCESS_TOKEN_REFRESH)
	public ResponseEntity<IdentityResponse> getFreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		LOGGER.info("In Login: Attempting to generate Fresh Access-Tokens...");
		
		IdentityResponse identityResponse = null;
		try {
			identityResponse = loginService.getFreshAccessToken(refreshTokenRequest);
			LOGGER.info("Fresh Access Token Generation Successful : Fresh Access Token Generated");
		
		} catch (Exception e) {
			LOGGER.info("Fresh Access Token Generation Failed!");
			LOGGER.debug("Fresh Access Token Generation Failed!"+e.getStackTrace());
		}
		
		return new ResponseEntity<IdentityResponse>(identityResponse, HttpStatus.OK);
	}
}