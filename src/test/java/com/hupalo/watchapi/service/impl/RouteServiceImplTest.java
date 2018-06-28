package com.hupalo.watchapi.service.impl;

import com.hupalo.watchapi.dao.RouteDataDAO;
import com.hupalo.watchapi.service.RouteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class RouteServiceImplTest {

	@MockBean
	private RouteDataDAO dataDAO;

	private RouteService routeService;

	@Before
	public void setUp() {
		this.routeService = new RouteServiceImpl(this.dataDAO);
	}

	@Test
	public void testCheckIfDirectRouteExists() {
		Integer depId = 10;
		Integer arrId = 15;
		when(this.dataDAO.existsByDepartureIdAndArrivalId(depId, arrId)).thenReturn(true);
		this.routeService.checkIfDirectRouteExists(depId, arrId);
		verify(this.dataDAO, times(1)).existsByDepartureIdAndArrivalId(depId, arrId);
	}

}