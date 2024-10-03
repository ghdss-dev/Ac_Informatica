package com.ghdss.dev.ac_informatica_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class AcInformaticaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcInformaticaServiceApplication.class, args);
	}

}
