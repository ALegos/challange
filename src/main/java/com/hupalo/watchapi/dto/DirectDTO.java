package com.hupalo.watchapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class DirectDTO {

	private Integer dep_sid;
	private Integer arr_sid;
	@JsonProperty("direct_bus_route")
	private boolean exists;

	public DirectDTO(Integer departureId, Integer arrivalId) {
		this.dep_sid = departureId;
		this.arr_sid = arrivalId;
	}
}

