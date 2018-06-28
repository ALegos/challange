package com.hupalo.watchapi.dao.impl;

import java.nio.file.Path;

import com.hupalo.watchapi.properties.FileProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class RouteDataDaoImplTest {

	@MockBean
	private FileProperties fileProperties;

	private RouteDataDaoImpl routeDataDAO;

	@Before
	public void setUp() {
		this.routeDataDAO = new RouteDataDaoImpl(this.fileProperties);
	}

	@Test
	public void testGetDataFile() throws Exception {
		when(this.fileProperties.isEnable()).thenReturn(true);
		when(this.fileProperties.getPath()).thenReturn("testpath");
		this.routeDataDAO.getDataFile();
		verify(this.fileProperties, times(1)).getPath();
		reset(fileProperties);

		when(this.fileProperties.isEnable()).thenReturn(false);
		Path getDataFile = this.routeDataDAO.getDataFile();
		verify(this.fileProperties, times(0)).getPath();
		assertEquals("test-file.txt", getDataFile.getFileName().toString());
	}

	@Test
	public void testInitDataFromFile() {
		this.routeDataDAO.initDataFromFile();
		assertEquals(11, this.routeDataDAO.getRoutesById().size());
		long countStations = this.routeDataDAO.getRoutesById()
				.values()
				.stream()
				.flatMap(x -> x.getStations().stream())
				.distinct().count();
		assertEquals(countStations, this.routeDataDAO.getStations().size());
	}

}