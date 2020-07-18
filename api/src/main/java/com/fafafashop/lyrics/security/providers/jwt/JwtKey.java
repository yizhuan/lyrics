package com.fafafashop.lyrics.security.providers.jwt;

import java.security.PublicKey;

public interface JwtKey {
	public PublicKey get(String kid) throws JwtProviderException;
}
