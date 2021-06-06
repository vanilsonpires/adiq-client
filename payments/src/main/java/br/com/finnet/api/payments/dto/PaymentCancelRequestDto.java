package br.com.finnet.api.payments.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import br.com.finnet.api.payments.entidy.Seller;
import lombok.Data;

public @Data class PaymentCancelRequestDto {
	
	@NotNull(message = "id is required")
	@Min(value = 1, message = "min id is one")
	private Long id;
	
	@NotNull(message = "amount is required")
	@DecimalMin(value = "0.0", message = "min amount is zero")
	private BigDecimal amount;
	
	@Valid
	private List<Seller> sellers;

}
