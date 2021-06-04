package br.com.finnet.api.payments.entidy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.finnet.api.payments.enums.CaptureType;
import br.com.finnet.api.payments.enums.CurrencyCode;
import br.com.finnet.api.payments.enums.ProductType;
import br.com.finnet.api.payments.enums.TransactionType;
import lombok.Data;

@Entity(name = "payment")
public @Data class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = Access.READ_ONLY) // not accept id of frontend..
	private Long id;
	
	@NotNull(message = "transactionType is required")
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	@NotNull(message = "amount is required")
	@DecimalMin(value = "0.0", message = "min amount is zero")
	private BigDecimal amount;
	
	@NotNull(message = "currencyCode is required")
	@Enumerated(EnumType.STRING)
	private CurrencyCode currencyCode;
	
	@NotNull(message = "productType is required")
	@Enumerated(EnumType.STRING)
	private ProductType productType;
	
	@NotNull(message = "installments is required")
	@Min(value = 1, message = "min installments is one")
	private Integer installments;
	
	@NotNull(message = "captureType is required")
	@Enumerated(EnumType.STRING)
	private CaptureType captureType;
	
	@Column(columnDefinition = "boolean default false", nullable = false)
	private boolean recurrent;
	
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cardinfo_id")
	private CardInfo cardInfo;
	
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "seller_info")
	private SellerInfo sellerInfo;
	
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "payment_id")
	private List<Seller> sellers;

	// not accept userid of frontend.. this must be identified by the token
	@JsonProperty(access = Access.READ_ONLY)
	private Long userid;
	
	// not accept date of frontend.. this must be get timestamp of server
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime timestamp;

}
