package br.com.finnet.api.payments.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.finnet.api.payments.dto.PaymentSearch;
import br.com.finnet.api.payments.entidy.Payment;
import br.com.finnet.api.payments.entidy.PaymentAuthorization;
import br.com.finnet.api.payments.feing.AuthFeing;
import br.com.finnet.api.payments.repository.PaymentAuthorizationRepository;
import br.com.finnet.api.payments.repository.PaymentRepository;
import br.com.finnet.api.payments.specifications.PaymentSpecification;

@Service
public class PaymentService {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private AuthFeing authFeing;
	
	@Autowired
	private PaymentRepository repository;
	
	@Autowired
	private AdiqService adiqService;
	
	@Autowired
	private PaymentAuthorizationRepository paymentAuhorizationRepository;
	
	public PaymentAuthorization requestPayment(@Valid Payment payment) {
		
		//setting current timestamp...
		payment.setTimestamp(LocalDateTime.now());
		
		//set user id
		payment.setUserid(authFeing.myId(request.getHeader("Authorization")));		
		
		//saving...
		repository.save(payment);
		
		return paymentAuhorizationRepository.save(adiqService.requestPayment(payment).setPayment(payment));
	}
	
	public List<Payment> search(PaymentSearch params){
		return repository.findAll(PaymentSpecification.search(params));
	}
	

}
