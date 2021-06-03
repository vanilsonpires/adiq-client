package br.com.finnet.api.payments.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.finnet.api.payments.dto.PaymentSearch;
import br.com.finnet.api.payments.entidy.Payment;
import br.com.finnet.api.payments.entidy.PaymentAuthorization;
import br.com.finnet.api.payments.service.PaymentService;

@RestController
@RequestMapping
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping
	public ResponseEntity<PaymentAuthorization> requestPayment(@RequestBody @Valid Payment payment){
		return ResponseEntity.ok(paymentService.requestPayment(payment));
	}
	
	@GetMapping
	public ResponseEntity<List<Payment>> searchPayments(@RequestBody PaymentSearch params){
		return ResponseEntity.ok(paymentService.search(params));
	}

}
