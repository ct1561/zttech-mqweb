package com.zttech.mq.web.springboot;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootRunner {

	
	public static void main(String[] args) {
		String property = System.getProperty("user.dir");
		property = property.substring(0, property.lastIndexOf("\\") + 1);
		String webConf = property + "conf" + File.separator + "application-web.properties";
		WebApplicationType type = null;
		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(webConf));
			Properties properties = new Properties();
			properties.load(inputStream);
			type = WebApplicationType.valueOf(properties.get("web-server-open-type").toString());
		} catch (IOException e) {
			type = WebApplicationType.SERVLET;
		}
		
		SpringApplication app = new SpringApplication(SpringBootRunner.class);
		app.setWebApplicationType(type);
		app.run(args);
	}
}
