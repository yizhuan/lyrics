package com.fafafashop.lyrics.security.providers.jwt;

import com.fafafashop.lyrics.security.providers.google.jwt.GoogleJwtKey;

public class JwtProvider {

	public static JwtKey createKeyInstance(String provider) {
		switch (provider) {

		case "GOOGLE":
			return new GoogleJwtKey();

		case "FACEBOOK":
			return null;

		case "WECHAT":
			return null;
			
		default:
			return null;

		}
	}

}
