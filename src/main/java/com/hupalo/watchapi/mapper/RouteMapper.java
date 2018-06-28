package com.hupalo.watchapi.mapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.hupalo.watchapi.model.RouteEntity;

public class RouteMapper {

	private RouteMapper() {
		throw new IllegalStateException("Utility class");
	}

	public static RouteEntity createRoute(String data) {
		List<Integer> split = Stream.of(data.split(" ")).map(Integer::valueOf).collect(Collectors.toList());
		RouteEntity entity = new RouteEntity();
		entity.setRouteId(split.get(0));
		entity.setStations(split.subList(1, split.size()));
		return entity;
	}
}
