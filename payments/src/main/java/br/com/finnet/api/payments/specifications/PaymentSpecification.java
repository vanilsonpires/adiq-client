package br.com.finnet.api.payments.specifications;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.finnet.api.payments.dto.PaymentSearch;
import br.com.finnet.api.payments.entidy.Payment;

public interface PaymentSpecification {

	public static Specification<Payment> search(PaymentSearch searchParams) {
		return (root, query, criteriaBuilder) -> {
			
			List<Predicate> predicates = new ArrayList<Predicate>();
			
			if(searchParams.getUserid()!=null && searchParams.getUserid()!=0L)
				predicates.add(criteriaBuilder.equal(root.get("userid"), searchParams.getUserid()));
			
			if(searchParams.getOrderNumber()!=null && !searchParams.getOrderNumber().trim().isBlank() )
				predicates.add(criteriaBuilder.equal(root.join("sellerInfo").get("orderNumber"), searchParams.getOrderNumber()));
			
			if(searchParams.getDate()!=null) {
				//ignoring time...
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("timestamp"), searchParams.getDate().atTime(0, 0, 0)));
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("timestamp"), searchParams.getDate().atTime(23, 59, 59)));
			}
			
			query.orderBy(criteriaBuilder.asc(root.get("timestamp")));
			
			return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
		};
	}
}
