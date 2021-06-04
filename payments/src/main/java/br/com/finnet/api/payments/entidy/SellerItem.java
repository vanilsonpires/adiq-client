package br.com.finnet.api.payments.entidy;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Entity(name = "seller_item")
public @Data class SellerItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = Access.READ_ONLY) // not accept id of frontend..
	private Long id;
	
	@NotEmpty(message = "description is required")
	private String description;
	
	@NotNull(message = "amount is required")
	@DecimalMin(value = "0.0", message = "min amount is zero")
	private BigDecimal amount;
	
	@NotNull(message = "ratePercent is required")
	@DecimalMin(value = "0.0", message = "min ratePercent is zero")
	private BigDecimal ratePercent;
	
	@NotNull(message = "rateAmount is required")
	@DecimalMin(value = "0.0", message = "min rateAmount is zero")
	private BigDecimal rateAmount;
	
}
