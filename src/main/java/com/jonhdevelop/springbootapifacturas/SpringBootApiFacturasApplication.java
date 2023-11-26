package com.jonhdevelop.springbootapifacturas;

import com.jonhdevelop.springbootapifacturas.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootApiFacturasApplication implements CommandLineRunner {

	@Autowired
	UploadFileService uploadFileService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApiFacturasApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		uploadFileService.deleteAll();
		uploadFileService.init();
	}
}
