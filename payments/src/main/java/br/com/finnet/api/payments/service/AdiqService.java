package br.com.finnet.api.payments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.finnet.api.payments.entidy.Payment;
import br.com.finnet.api.payments.entidy.PaymentAuthorization;

@Service
public class AdiqService {
	
	@Autowired
    @Qualifier("adiq")
	private WebClient webClient;
	
	public PaymentAuthorization requestPayment(Payment payment) {			
		
		//code here...
		
		return new PaymentAuthorization();
	}
	
	public PaymentAuthorization cancelPayment(PaymentAuthorization authorization) {
		//code here...
		
		return new PaymentAuthorization();
	}

}
