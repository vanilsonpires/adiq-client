package br.com.finnet.api.payments.feing;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(name = "auth", path = "/users/")
public interface AuthFeing {
	
	@RequestMapping(method = RequestMethod.GET)
	public Long myId(@Valid @NotEmpty(message = "authorization is required") String authorization);

}
