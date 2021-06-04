package br.com.finnet.api.payments.entidy;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity(name = "seller")
public class Seller {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = Access.READ_ONLY) // not accept id of frontend..
	private Long id;
	
	@NotNull(message = "amount is required")
	@DecimalMin(value = "0.0", message = "min amount is zero")
	private BigDecimal amount;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "seller_id")
	private List<SellerItem> items;
	
}
