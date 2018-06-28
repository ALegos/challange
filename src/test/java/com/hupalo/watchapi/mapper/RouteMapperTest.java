package com.hupalo.watchapi.mapper;

import com.hupalo.watchapi.model.RouteEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class RouteMapperTest {

	@Test
	public void testGetDataFile() {
		String inputData = "10 814992 503959 971871 536189 573389 250670 340711 921428 892711 657589 363309 472672 203958 813858 286007 679945 220310 707752 " +
				"671999 547905 135635 880799";
		String[] split = inputData.split(" ");
		RouteEntity route = RouteMapper.createRoute(inputData);
		assertEquals(Integer.valueOf(split[0]), route.getRouteId());
		assertEquals(split.length - 1, route.getStations().size());
	}

}
