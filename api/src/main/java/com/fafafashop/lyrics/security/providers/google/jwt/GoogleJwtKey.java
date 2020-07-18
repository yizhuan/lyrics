package com.fafafashop.lyrics.security.providers.google.jwt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.fafafashop.lyrics.security.providers.jwt.JwtKey;
import com.fafafashop.lyrics.security.providers.jwt.JwtProviderException;
import com.google.gson.Gson;

public class GoogleJwtKey implements JwtKey {

	private static final String httpsUrl = "https://www.googleapis.com/oauth2/v1/certs";
	
	private static HashMap<String, GooglePublicKey> keyMap = new HashMap<String, GooglePublicKey>();
		
	public GoogleJwtKey() {

	}

	@Override
	public PublicKey get(String kid) throws JwtProviderException {

		try {
			GooglePublicKey pk = find(kid);
			return getPublicKey(pk.getCertificate());
		} catch (IOException e) {
			throw new JwtProviderException("Failed to fetch Google X509 certificate at " + httpsUrl, e);
		} catch (CertificateException e) {
			throw new JwtProviderException("Cannot parse the X509 certificate", e);
		}
	}

	@SuppressWarnings("unchecked")
	private GooglePublicKey find(String kid) throws IOException {
		if (keyMap.containsKey(kid)) {
			GooglePublicKey googlePublicKey = keyMap.get(kid);
			if(googlePublicKey != null) {				
				return googlePublicKey;				
			}		
		}
		
		URL url = new URL(httpsUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		Map<String, String> map = new HashMap<String, String>();
		map = (Map<String, String>) new Gson().fromJson(br, map.getClass());

		String certificate = map.get(kid).replaceAll("\\n", "")
				.replace("-----BEGIN CERTIFICATE-----", "-----BEGIN CERTIFICATE-----\n")
				.replace("-----END CERTIFICATE-----", "\n-----END CERTIFICATE-----");

		// System.out.println(certificate);
		GooglePublicKey googlePublicKey = new GooglePublicKey(kid, certificate);
		keyMap.put(kid, googlePublicKey);
		
		return googlePublicKey;
	}

	private static PublicKey getPublicKey(String certificate) throws CertificateException, IOException {
		CertificateFactory fact = CertificateFactory.getInstance("X.509");
		X509Certificate cer = (X509Certificate) fact.generateCertificate(IOUtils.toInputStream(certificate, "UTF-8"));
		return cer.getPublicKey();
	}

}
