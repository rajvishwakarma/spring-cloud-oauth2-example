package com.sample.service;

import com.sample.request.IdentityRequest;
import com.sample.request.RefreshTokenRequest;
import com.sample.response.IdentityResponse;

public interface ILoginService {

	IdentityResponse getAccessToken(IdentityRequest identityRequest);

	IdentityResponse getFreshAccessToken(RefreshTokenRequest refreshTokenRequest);

}
