package br.com.finnet.api.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class DefaultController {

	@GetMapping(value = "/me")
	public ResponseEntity<?> me() {
		return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}

	@GetMapping({"","/docs,","/documentacao","/documentation"})
	public void swagger(HttpServletResponse response) throws IOException {
		response.sendRedirect("/swagger-ui/");
	}

}
