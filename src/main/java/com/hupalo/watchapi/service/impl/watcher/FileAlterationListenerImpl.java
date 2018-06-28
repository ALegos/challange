package com.hupalo.watchapi.service.impl.watcher;

import java.io.File;

import com.hupalo.watchapi.service.ApplicationContextService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

@Slf4j
public class FileAlterationListenerImpl extends FileAlterationListenerAdaptor {

	private final ApplicationContextService applicationContextService;

	public FileAlterationListenerImpl(ApplicationContextService applicationContextService) {
		this.applicationContextService = applicationContextService;
	}

	@Override
	public void onFileCreate(File file) {
		log.debug("File \"{}\" was created", file.getName());
		this.applicationContextService.reload();
	}

	@Override
	public void onFileChange(File file) {
		log.debug("File \"{}\" was changed", file.getName());
		this.applicationContextService.reload();
	}

	@Override
	public void onFileDelete(File file) {
		//context will be in previous state and when you will add new file context will reload
		log.debug("File \"{}\" was deleted", file.getName());
	}
}
