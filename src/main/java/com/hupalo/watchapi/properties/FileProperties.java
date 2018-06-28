package com.hupalo.watchapi.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "input-data.file")
@Component
@Data
@Slf4j
public class FileProperties {
	private boolean enable;
	private String directory;
	private String path;
}
