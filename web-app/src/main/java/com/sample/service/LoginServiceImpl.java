package com.sample.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sample.request.IdentityRequest;
import com.sample.request.RefreshTokenRequest;
import com.sample.response.IdentityResponse;

/**
 * This service class serves the business logic and Login/Access-token based implementation
 * 
 * @author RV
 *
 */
@Service
public class LoginServiceImpl implements ILoginService {

	private RestTemplate restTemplate = new RestTemplate();

	@Value("${security.oauth2.client.accessTokenUri}")
	private String accessTokenUri;

	@Value("${security.oauth2.client.clientId}")
	private String clientId;

	@Value("${security.oauth2.client.clientSecret}")
	private String clientSecret;

	@Override
	public IdentityResponse getAccessToken(IdentityRequest identityRequest) {

		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(accessTokenUri);

		if(null != identityRequest.getGrantType())
			sbUrl.append("?grant_type="+identityRequest.getGrantType());
		else
			sbUrl.append("?grant_type=password");

		if(null != identityRequest.getRedirectUri())
			sbUrl.append("&redirect_uri="+identityRequest.getRedirectUri());
		else
			sbUrl.append("&redirect_uri=http://www.xyz.com");
		
		sbUrl.append("&username="+identityRequest.getUserName());
		sbUrl.append("&password="+identityRequest.getPassword());

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Authorization", "Basic "+Base64.getEncoder().encodeToString((clientId+":"+clientSecret).getBytes(StandardCharsets.UTF_8)));
		requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<String> httpEntity = new HttpEntity<String>(requestHeaders);

		ResponseEntity<IdentityResponse> response = restTemplate.exchange(sbUrl.toString(), HttpMethod.POST, httpEntity,  IdentityResponse.class);

		if(response.getStatusCode() != null && !HttpStatus.OK.equals(response.getStatusCode())){
			return null;
		}
		return response.getBody();
	}

	@Override
	public IdentityResponse getFreshAccessToken(RefreshTokenRequest refreshTokenRequest) {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(accessTokenUri);

		if(null != refreshTokenRequest.getGrantType())
			sbUrl.append("?grant_type="+refreshTokenRequest.getGrantType());
		else
			sbUrl.append("?grant_type=refresh_token");

		if(null != refreshTokenRequest.getRedirectUri())
			sbUrl.append("&redirect_uri="+refreshTokenRequest.getRedirectUri());
		else
			sbUrl.append("&redirect_uri=http://www.xyz.com");
		
		sbUrl.append("&refresh_token="+refreshTokenRequest.getRefreshToken());

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Authorization", "Basic "+Base64.getEncoder().encodeToString((clientId+":"+clientSecret).getBytes(StandardCharsets.UTF_8)));
		requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<String> httpEntity = new HttpEntity<String>(requestHeaders);

		ResponseEntity<IdentityResponse> response = restTemplate.exchange(sbUrl.toString(), HttpMethod.POST, httpEntity,  IdentityResponse.class);

		if(response.getStatusCode() != null && !HttpStatus.OK.equals(response.getStatusCode())){
			return null;
		}
		return response.getBody();
	}

}
