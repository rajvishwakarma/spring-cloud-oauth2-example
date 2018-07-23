package com.sample.request;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * RefreshTokenRequest Pojo
 * 
 * @author RV
 *
 */
@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class RefreshTokenRequest {
	
	@NotEmpty
	private String refreshToken;
	
	@NotEmpty
	private String grantType;
	
	@NotEmpty
	private String redirectUri;
}
