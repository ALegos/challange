package com.hupalo.watchapi.service.impl;

import com.hupalo.watchapi.dao.RouteDataDAO;
import com.hupalo.watchapi.service.RouteService;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl implements RouteService {

	private final RouteDataDAO routeDataDAO;

	public RouteServiceImpl(RouteDataDAO routeDataDAO) {
		this.routeDataDAO = routeDataDAO;
	}

	@Override
	public boolean checkIfDirectRouteExists(Integer departureId, Integer arrivalId) {
		return this.routeDataDAO.existsByDepartureIdAndArrivalId(departureId, arrivalId);
	}
}
