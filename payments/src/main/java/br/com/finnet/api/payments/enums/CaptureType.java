package br.com.finnet.api.payments.enums;

public enum CaptureType {
	
	ac("Autoriza e captura"),
	pa("Pré-Autoriza");
	
	private String description;
	
	CaptureType(String description){
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
