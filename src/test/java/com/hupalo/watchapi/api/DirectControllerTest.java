package com.hupalo.watchapi.api;

import com.hupalo.watchapi.service.RouteService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DirectControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	private RouteService routeService;

	@Test
	public void testCheckDirectRouteValidResultExists() throws Exception {
		this.checkResult(true);
	}

	@Test
	public void testCheckDirectRouteValidResultNotExists() throws Exception {
		this.checkResult(false);
	}

	@Test
	public void testCheckDirectRouteBadRequest() throws Exception {
		String validPath = DirectController.API_DIRECT;
		this.mockMvc.perform(get(validPath))
				.andExpect(status().isBadRequest());
	}

	private void checkResult(boolean expectedResult) throws Exception {
		Integer depId = 10;
		Integer arrId = 15;

		String validPath = DirectController.API_DIRECT +
				"?dep_sid=" +
				depId +
				"&arr_sid=" +
				arrId;
		Mockito.when(this.routeService.checkIfDirectRouteExists(depId, arrId)).thenReturn(expectedResult);
		this.mockMvc.perform(get(validPath))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.direct_bus_route", Matchers.equalTo(expectedResult)));
	}


}
