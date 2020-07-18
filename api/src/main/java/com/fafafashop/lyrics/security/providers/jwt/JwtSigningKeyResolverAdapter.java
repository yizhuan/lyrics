package com.fafafashop.lyrics.security.providers.jwt;

import com.fafafashop.lyrics.security.providers.google.jwt.GoogleJwtSigningKeyResolverAdapter;

import io.jsonwebtoken.SigningKeyResolverAdapter;

public class JwtSigningKeyResolverAdapter {
	
	public static SigningKeyResolverAdapter get(String provider) {
		switch (provider) {

		case "GOOGLE":
			return new GoogleJwtSigningKeyResolverAdapter();

		case "FACEBOOK":
			return null;

		case "WECHAT":
			return null;
			
		default:
			return null;

		}
	}
}
