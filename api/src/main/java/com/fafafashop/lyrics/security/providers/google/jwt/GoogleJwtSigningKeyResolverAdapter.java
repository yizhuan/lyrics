package com.fafafashop.lyrics.security.providers.google.jwt;

import java.security.Key;

import com.fafafashop.lyrics.security.providers.jwt.JwtKey;
import com.fafafashop.lyrics.security.providers.jwt.JwtProvider;
import com.fafafashop.lyrics.security.providers.jwt.JwtProviderException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolverAdapter;

public class GoogleJwtSigningKeyResolverAdapter extends SigningKeyResolverAdapter {

	@Override
	public Key resolveSigningKey(JwsHeader header, Claims claims) {
		JwtKey jk = JwtProvider.createKeyInstance("GOOGLE");
		try {
			return jk.get(header.getKeyId());
		} catch (JwtProviderException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Key resolveSigningKey(JwsHeader header, String plaintext) {
		JwtKey jk = JwtProvider.createKeyInstance("GOOGLE");
		try {
			return jk.get(header.getKeyId());
		} catch (JwtProviderException e) {
			e.printStackTrace();
			return null;
		}
	}

}