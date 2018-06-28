package com.hupalo.watchapi.service.impl;

import com.hupalo.watchapi.service.ApplicationContextService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "input-data.file.enable", havingValue = "true")
public class ApplicationContextServiceImpl implements ApplicationContextService {

	private final RestartEndpoint restartEndpoint;

	public ApplicationContextServiceImpl(ApplicationContext applicationContext, RestartEndpoint restartEndpoint) {
		this.restartEndpoint = restartEndpoint;
		((ConfigurableApplicationContext) applicationContext).registerShutdownHook();
	}

	@Override
	public void reload() {
		Thread thread = new Thread(restartEndpoint::restart);
		thread.setDaemon(false);
		thread.start();
	}
}
