package com.hupalo.watchapi.model;

import java.util.List;

import lombok.Data;

@Data
public class RouteEntity {
	private Integer routeId;
	private List<Integer> stations;
}
