package br.com.finnet.api.payments.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.finnet.api.payments.entidy.PaymentAuthorization;

@Repository
public interface PaymentAuthorizationRepository extends JpaRepository<PaymentAuthorization, Long> {
	
	public Optional<PaymentAuthorization> findByPaymentIdAndValidIsTrue(String payment);

}
