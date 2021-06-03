package br.com.finnet.api.auth.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.finnet.api.auth.services.UserService;
import br.com.finnet.api.auth.utils.JWTUtil;

@RestController
@RequestMapping(name = "users")
public class UserController {
	
	@Autowired
	private UserService usuarioService;

	@Autowired
	private JWTUtil util;

	@GetMapping
	public ResponseEntity<Long> myId(@RequestHeader("Authorization") String authorization) {
		try {
			Map<String, Object> retorno = util.recoveryUsername(authorization);
			return ResponseEntity.ok(usuarioService.findIdByUsername(retorno.get("user_name").toString()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
}
