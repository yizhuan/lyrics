package com.fafafashop.lyrics.security.providers.google.jwt;

public class GooglePublicKey {
	private String kid;
	private String certificate;
	public GooglePublicKey(String kid, String certificate) {
		super();
		this.kid = kid;
		this.certificate = certificate;
	}
	public String getKid() {
		return kid;
	}
	public void setKid(String kid) {
		this.kid = kid;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}	
}
