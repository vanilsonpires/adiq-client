package br.com.finnet.api.payments.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.finnet.api.payments.dto.PaymentSearch;
import br.com.finnet.api.payments.entidy.Payment;
import br.com.finnet.api.payments.entidy.PaymentAuthorization;
import br.com.finnet.api.payments.exceptions.NotFoundException;
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
	public ResponseEntity<Page<Payment>> searchPayments(@Valid @RequestBody(required = false) PaymentSearch params){
		return ResponseEntity.ok(paymentService.search(Optional.ofNullable(params).orElse(new PaymentSearch())));
	}
	
	@PutMapping("/cancel/{payment}")
	public ResponseEntity<PaymentAuthorization> cancel(@PathVariable String payment) throws NotFoundException{
		return ResponseEntity.ok(paymentService.cancelPayment(payment));
	}
	
	@PutMapping("/cancel/header")
	public ResponseEntity<PaymentAuthorization> cancelUsingHeadeParam(@RequestHeader String payment) throws NotFoundException{
		return ResponseEntity.ok(paymentService.cancelPayment(payment));
	}
	
	@PutMapping("/cancel/param")
	public ResponseEntity<PaymentAuthorization> cancelUsingParam(@RequestParam String payment) throws NotFoundException{
		return ResponseEntity.ok(paymentService.cancelPayment(payment));
	}

}
