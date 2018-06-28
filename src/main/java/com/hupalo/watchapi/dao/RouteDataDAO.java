package com.hupalo.watchapi.dao;

public interface RouteDataDAO {
	boolean existsByDepartureIdAndArrivalId(Integer departureId, Integer arrivalId);
}
