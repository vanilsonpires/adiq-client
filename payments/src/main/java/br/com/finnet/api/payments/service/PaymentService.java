package br.com.finnet.api.payments.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.finnet.api.payments.dto.PaymentCancelRequestDto;
import br.com.finnet.api.payments.dto.PaymentSearchDto;
import br.com.finnet.api.payments.entidy.Payment;
import br.com.finnet.api.payments.entidy.PaymentAuthorization;
import br.com.finnet.api.payments.exceptions.NotFoundException;
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
	
	@Transactional
	public PaymentAuthorization cancelPayment(String payment, @Valid PaymentCancelRequestDto cancelRequest) throws NotFoundException {		
		Optional<PaymentAuthorization> auth = paymentAuhorizationRepository.findByPaymentIdAndValidIsTrue(payment);
		if(auth.isPresent()) {
			return paymentAuhorizationRepository.save(paymentAuhorizationRepository.save(adiqService.cancelPayment(payment, cancelRequest)));
		}
		throw new NotFoundException("payment not found or is invalid");
	}
	
	public Page<Payment> search(PaymentSearchDto params){
		return repository.findAll(PaymentSpecification.search(params), PageRequest.of(params.getPageNum(), params.getPagesize()==0 ? 20 : params.getPagesize()));
	}
	

}
