package br.com.finnet.api.payments.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

public @Data class PaymentSearch {
	
	private Long userid;
	
	private String orderNumber;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate date;

}
