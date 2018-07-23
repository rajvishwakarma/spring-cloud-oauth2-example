package com.sample.util;

/**
 * Application's REST mapping urls
 * 
 * @author RV
 *
 */
public class RESTEndpointMapper {
	
	public static final String CARDS = "/cards";
	public static final String CARDS_ID = "/cards/{card-id}";
	
	public static final String LOGIN_ACCESS_TOKEN = "/login/access-token";
	public static final String LOGIN_ACCESS_TOKEN_REFRESH = "/login/access-token/refresh";
}