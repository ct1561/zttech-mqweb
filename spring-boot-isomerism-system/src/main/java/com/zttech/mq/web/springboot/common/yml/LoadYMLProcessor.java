package com.zttech.mq.web.springboot.common.yml;

import java.io.File;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.FileSystemResource;

public class LoadYMLProcessor implements EnvironmentPostProcessor {

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		String property = System.getProperty("user.dir");
		property = property.substring(0, property.lastIndexOf("\\") + 1);
		
		String clientPath = property + "conf" + File.separator + "application-client.yml";
		String serverPath = property + "conf" + File.separator + "application-server.yml";
		
		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
		yaml.setResources(new FileSystemResource(clientPath));
		MutablePropertySources propertySources = environment.getPropertySources();
		propertySources.addFirst(new PropertiesPropertySource("configClient", yaml.getObject()));
		
		yaml.setResources(new FileSystemResource(serverPath));
		
		propertySources.addFirst(new PropertiesPropertySource("configServer", yaml.getObject()));
	}
	
	public static void main(String[] args) {
		String property = "E:\\wrapperTest-3.5.14 - 3.12\\bin";
		property = property.substring(0, property.lastIndexOf("\\") + 1);
		System.out.println(property);
	}

}
