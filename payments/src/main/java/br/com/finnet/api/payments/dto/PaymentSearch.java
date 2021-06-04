package br.com.finnet.api.payments.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

public @Data class PaymentSearch {
	
	private Long userid;
	
	private String orderNumber;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate date;
	
	@Min(value = 0, message = "min pagenum is zero")
	private int pageNum;
	
	@Min(value = 0, message = "min pagesize is zero")
	private int pagesize;

}
