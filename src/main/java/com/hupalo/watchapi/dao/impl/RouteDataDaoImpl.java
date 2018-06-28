package com.hupalo.watchapi.dao.impl;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.hupalo.watchapi.dao.RouteDataDAO;
import com.hupalo.watchapi.mapper.RouteMapper;
import com.hupalo.watchapi.model.RouteEntity;
import com.hupalo.watchapi.properties.FileProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

@Slf4j
@Repository
public class RouteDataDaoImpl implements RouteDataDAO {

	private static final String CLASSPATH_TEST_FILE_TXT = "classpath:test-file.txt";

	private final FileProperties fileProperties;

	@Getter
	private Map<Integer, RouteEntity> routesById;

	@Getter
	private Map<Integer, List<Integer>> stations;

	public RouteDataDaoImpl(FileProperties fileProperties) {
		this.fileProperties = fileProperties;
	}

	@PostConstruct
	public void initDataFromFile() {
		try (Stream<String> stream = Files.lines(this.getDataFile())) {
//			this.createTestFile();
			log.info("Started data file processing");
			routesById = stream.skip(1)
					.map(RouteMapper::createRoute)
					.collect(Collectors.toMap(RouteEntity::getRouteId, Function.identity()));
			log.info("Data file processing in progress");
			stations = routesById.values()
					.stream()
					.flatMap(r -> r.getStations()
							.stream()
							.map(s -> new AbstractMap.SimpleEntry<>(s, r.getRouteId())))
					.collect(Collectors.groupingBy(Map.Entry::getKey,
							Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
			log.info("Data file processing in finished");
		} catch (IOException e) {
			throw new BeanInitializationException("Couldn't initialize data from file", e);
		}
	}

	@Override
	public boolean existsByDepartureIdAndArrivalId(Integer departureId, Integer arrivalId) {
		List<Integer> integers = this.stations.get(departureId);
		boolean result = false;
		if (Objects.nonNull(integers)) {
			for (Integer routeId : integers) {
				if (Boolean.FALSE.equals(result)) {
					result = routesById.get(routeId).getStations()
							.stream()
							.skip(routesById.get(routeId).getStations().indexOf(departureId) + 1)
							.anyMatch(x -> x.equals(arrivalId));
				}
			}
		}
		return result;
	}

	//public only for test purposes
	public Path getDataFile() throws FileNotFoundException {
		Path result;
		if (Boolean.TRUE.equals(this.fileProperties.isEnable())) {
			return Paths.get(this.fileProperties.getPath());
		} else {
			result = ResourceUtils.getFile(CLASSPATH_TEST_FILE_TXT).toPath();
		}
		return result;
	}


	private void createTestFile() throws IOException {
		List<String> lines = new ArrayList<>(1000000 + 1);
		lines.add("1000000");
		for (int i = 0; i < 100000; i++) {
			Integer capacity = new Random().nextInt(999) + 2;
			List<Integer> list = new ArrayList<>(capacity + 1);
			list.add(i);
			IntStream.range(0, capacity).forEach(x -> list.add(new Random().nextInt(1000000) + 1));
			lines.add(list.stream().map(Object::toString).collect(Collectors.joining(" ")));
		}
		Path file = Paths.get("file.txt");
		Files.write(file, lines);
	}

}
