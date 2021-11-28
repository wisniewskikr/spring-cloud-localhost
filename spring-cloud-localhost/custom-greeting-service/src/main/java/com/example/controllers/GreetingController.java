package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.responses.GreetingResponse;
import com.example.responses.TextResponse;

@RestController
public class GreetingController {

	private static final String VERSION = "1";
	
	@Autowired
	private Environment environment;
	
	private RestTemplate restTemplate;
	
	@Autowired
	public GreetingController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping(value="/greeting/lang/{lang}/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GreetingResponse> greetingGet(
			@PathVariable(value = "lang") String lang,
			@PathVariable(value = "name") String name) {
		
		ResponseEntity<TextResponse> textResponseEntity = restTemplate.getForEntity("http://localhost:9090/text/lang/" + lang, TextResponse.class);
		TextResponse textResponse = textResponseEntity.getBody();
		
		String port = environment.getProperty("local.server.port");
		GreetingResponse response = new GreetingResponse(
				textResponse.getText() + " " + name, 
				port, 
				VERSION, 
				textResponse.getPort(), 
				textResponse.getVersion());
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}	
	
}