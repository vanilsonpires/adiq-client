package br.com.finnet.api.payments.entidy;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.finnet.api.payments.enums.Brand;
import lombok.Data;

@Entity(name = "card_info")
public @Data class CardInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = Access.READ_ONLY) // not accept id of frontend..
	private Long id;
	
	private String vaultId;
	
	@NotEmpty(message = "numberToken is required")
	private String numberToken;
	
	@NotEmpty(message = "cardholderName is required")
	private String cardholderName;
	
	@Min(value = 0, message = "invalid securityCode, min value is 0")
	@Max(value = 999, message = "invalid securityCode, max value is 999")
	private int securityCode;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "brand is required")
	private Brand brand;
	
	@Min(value = 1, message = "invalid expirationMonth, min value is 1")
	@Max(value = 12, message = "invalid expirationMonth, max value is 12")
	private int expirationMonth;
	
	@Min(value = 21, message = "invalid expirationYear, min value is 21")
	@Max(value = 99, message = "invalid expirationYear, max value is 99")
	private int expirationYear;

}
