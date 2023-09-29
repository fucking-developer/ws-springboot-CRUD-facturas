package com.jonhdevelop.springbootapifacturas;

import com.jonhdevelop.springbootapifacturas.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringBootApiFacturasApplication {

	@Autowired
	UploadFileService uploadFileService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootApiFacturasApplication.class, args);
	}
}
