package com.hupalo.watchapi.api;


import java.util.Objects;

import com.hupalo.watchapi.dto.DirectDTO;
import com.hupalo.watchapi.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(DirectController.API_DIRECT)
public class DirectController {

	public static final String API_DIRECT = "/api/direct";
	private final RouteService routeService;

	public DirectController(RouteService routeService) {
		this.routeService = routeService;
	}

	@GetMapping
	public ResponseEntity<DirectDTO> checkDirectRoute(DirectDTO input) {
		if (Objects.isNull(input) || Objects.isNull(input.getArr_sid()) || Objects.isNull(input.getDep_sid())) {
			return ResponseEntity.badRequest().build();
		} else {
			input.setExists(this.routeService.checkIfDirectRouteExists(input.getDep_sid(), input.getArr_sid()));
			return ResponseEntity.ok(input);
		}
	}

}
