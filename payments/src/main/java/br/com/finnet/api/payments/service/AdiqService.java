package br.com.finnet.api.payments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.finnet.api.payments.dto.PaymentCancelRequestDto;
import br.com.finnet.api.payments.entidy.Payment;
import br.com.finnet.api.payments.entidy.PaymentAuthorization;
import reactor.core.publisher.Mono;

@Service
public class AdiqService {
	
	@Autowired
    @Qualifier("adiq")
	private WebClient webClient;
	
	public PaymentAuthorization requestPayment(Payment payment) {
	 	Mono<PaymentAuthorization> aut = webClient
			.post()
			.uri("/v1/payments")
			.body(Mono.just(payment),Payment.class)
			.retrieve()
			.bodyToMono(PaymentAuthorization.class);
	 	return aut.block();
	}
	
	public PaymentAuthorization cancelPayment(String paymentid, PaymentCancelRequestDto cancelRequest) {
		Mono<PaymentAuthorization> aut = webClient
				.put()
				.uri("/v1/payments/"+paymentid+"/cancel")
				.body(Mono.just(cancelRequest),PaymentCancelRequestDto.class)
				.retrieve()
				.bodyToMono(PaymentAuthorization.class);		
		return aut.block();
	}

}
