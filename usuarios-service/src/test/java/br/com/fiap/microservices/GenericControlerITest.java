package br.com.fiap.microservices;
import org.junit.Before;
import org.springframework.boot.web.server.LocalServerPort;

import io.restassured.RestAssured;

public abstract class x {

	@LocalServerPort
	private int randomPort;
	
	private static final String HOST = "http://localhost";
	
	@Before 
	public void before() {
		RestAssured.reset();
		
		RestAssured.baseURI= HOST;
		RestAssured.port= randomPort;
		
	}
}
