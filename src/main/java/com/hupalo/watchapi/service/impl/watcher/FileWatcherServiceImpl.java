package com.hupalo.watchapi.service.impl.watcher;

import javax.annotation.PreDestroy;
import java.io.File;

import com.hupalo.watchapi.properties.FileProperties;
import com.hupalo.watchapi.service.ApplicationContextService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnProperty(name = "input-data.file.enable", havingValue = "true")
public class FileWatcherServiceImpl {

	private final FileAlterationMonitor monitor = new FileAlterationMonitor();

	public FileWatcherServiceImpl(FileProperties fileProperties, ApplicationContextService applicationContextService) throws Exception {
		final File directory = new File(fileProperties.getDirectory());
		FileAlterationObserver fao = new FileAlterationObserver(directory);
		fao.addListener(new FileAlterationListenerImpl(applicationContextService));
		this.monitor.addObserver(fao);
		monitor.start();
	}

	@PreDestroy
	public void destroy() throws Exception {
		log.info("Stopping monitor.");
		monitor.stop();
	}


}
