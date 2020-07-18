/*
 * Copyright 2020 fafafashop.com. All rights reserved. 
 */
package com.fafafashop.lyrics.security.providers.facebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FacebookAccessToken {
	
	private final Logger log = LoggerFactory.getLogger(FacebookAccessToken.class);
	
	public static final String httpsUrl = "https://www.facebook.com/connect/login_success.html";
	
	private String accessToken;

	public FacebookAccessToken(String accessToken) {
		super();
		this.accessToken = accessToken;
	}
	
	public boolean verify() {
		try {
			return verifyToken();
		} catch (IOException e) {
			log.info("Facebook AccessToken verificationn failed: ", e);			
			return false;			
		}
	}
	
	private boolean verifyToken() throws IOException{
		
		URL url = new URL(httpsUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "text/plain"); 
	    con.setRequestProperty("charset", "utf-8");
	    con.setDoOutput(true);
	    
	    String data = "access_token="+accessToken;
	    con.getOutputStream().write(data.getBytes("UTF-8"));
	    
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String resp = br.readLine();
				
		return resp.startsWith("Success") ? true:false;
	}
}
