package br.com.finnet.api.payments.entidy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Entity(name = "seller_info")
public @Data class SellerInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = Access.READ_ONLY) // not accept id of frontend..
	private Long id;
	
	@NotEmpty(message = "orderNumber is required")
	private String orderNumber;
	
	@NotEmpty(message = "softDescriptor is required")
	private String softDescriptor;
	
	private String dynamicMcc;
	
	@NotEmpty(message = "cavvUcaf is required")
	private String cavvUcaf;
	
	@NotEmpty(message = "eci is required")
	private String eci;
	
	@NotEmpty(message = "xid is required")
	private String xid;
	
	@NotEmpty(message = "programProtocol is required")
	private String programProtocol;

}
