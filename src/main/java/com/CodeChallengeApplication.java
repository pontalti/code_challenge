package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.service.Calculator;

@SpringBootApplication
public class CodeChallengeApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(CodeChallengeApplication.class);

	private Calculator calc;
	private ConfigurableApplicationContext context;

	public CodeChallengeApplication(ConfigurableApplicationContext context, Calculator calc) {
		super();
		this.calc = calc;
		this.context = context;
	}

	public static void main(String[] args) {
		SpringApplication.run(CodeChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) {
		LOG.info("Starting \n");
		String filePath = null;
		for(String str : args) {
			if(!str.contains("--spring")) {
				filePath = str;
			}
		}
		this.calc.fileLoader(filePath);
		this.calc.instrumentCalculators();
		LOG.info("finishing \n");
		this.context.close();
	}

}