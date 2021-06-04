package br.com.finnet.api.payments.entidy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "payment_authorization")
public @Data class PaymentAuthorization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = Access.READ_ONLY) // not accept id of frontend..
	private Long id;
	
	private int returnCode;
	
	private String description;
	
	private String paymentId;
	
	private String authorizationCode;
	
	private String orderNumber;
	
	private LocalDateTime expireAt;
	
	private BigDecimal amount;
	
	private LocalDateTime releaseAt;
	
	@Column(columnDefinition = "boolean default true", nullable = false)
	private boolean valid = true;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "payment", nullable = false)
	@JsonIgnore
	private Payment payment;
	
	public PaymentAuthorization setPayment(Payment payment) {
		setPayment(payment);
		return this;
	}
	
	public PaymentAuthorization invalid() {
		this.valid = false;
		return this;
	}
}
