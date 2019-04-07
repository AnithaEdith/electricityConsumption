package hotel.management.service;

import hotel.management.model.Floor;
import hotel.management.model.Hotel;
import hotel.management.model.MainCorridor;
import hotel.management.model.SubCorridor;
import hotel.management.utility.floorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class hotelService {
	Logger logger = LoggerFactory.getLogger(hotelService.class);
	@Autowired
	floorHelper floorHelper;

	public Hotel createHotel(int floorCount, int mainCorridorCount, int subCorridorCount) {
		Hotel hotel = new Hotel();
		Map<Integer, Floor> floors = new HashMap<>();
		if (floorCount > 0) {
			for (int i = 1; i <= floorCount; i++) {
				Floor floor = new Floor();
				Map<Integer, MainCorridor> mainCorridors = createMainCorridors(mainCorridorCount);
				Map<Integer, SubCorridor> subCorridors = createSubCorridors(subCorridorCount);
				floor.setFloorName(floor.getFloorName() + i);
				floor.setMainCorridors(mainCorridors);
				floor.setSubCorridors(subCorridors);
				floors.put(i, floor);
			}
		}
		hotel.setHotelFloors(floors);
		return hotel;
	}

	private static Map<Integer, MainCorridor> createMainCorridors(int mainCorridorCount) {
		Map<Integer, MainCorridor> mainCorridors = new HashMap<>();
		if (mainCorridorCount > 0) {
			for (int i = 1; i <= mainCorridorCount; i++) {
				MainCorridor mainCorridor = new MainCorridor(true, true);
				mainCorridors.put(i, mainCorridor);
			}
		}
		return mainCorridors;
	}

	private static Map<Integer, SubCorridor> createSubCorridors(int subCorridorCount) {
		Map<Integer, SubCorridor> subCorridors = new HashMap<>();
		if (subCorridorCount > 0) {
			for (int i = 1; i <= subCorridorCount; i++) {
				SubCorridor mainCorridor = new SubCorridor(false, true);
				subCorridors.put(i, mainCorridor);
			}
		}
		return subCorridors;
	}

	public Hotel handleFloorMovementDetected(Hotel hotel, int floor, int subCorridor) {
		hotel.getHotelFloors().entrySet().stream()
				.filter(floors -> floors.getKey() == floor)
				.map(floors -> {
					floorHelper.handleSubCorridorMovement(subCorridor,floor, floors.getValue());
					return subCorridor;
				})
				.collect(Collectors.toList());
		logger.info("\n After Floor Movement has been Detected for Floor -" + floor + " subCorridor " + subCorridor);
		printHotel(hotel);

		return hotel;
	}

	public void printHotel(Hotel hotel) {
		hotel.getHotelFloors().entrySet().iterator().forEachRemaining(
				floors -> {
					logger.info(floors.getValue().getFloorName());

					floors.getValue().getMainCorridors().entrySet().iterator().forEachRemaining(
							MainCorridor -> {
								logger.info("Main Corridor " + MainCorridor.getKey()
										+ " Light " + MainCorridor.getKey() + " : "
										+ (MainCorridor.getValue().isLight() ? "ON" : "OFF")
										+ " AC :" + (MainCorridor.getValue().isAc() ? "ON" : "OFF"));
							}
					);
					floors.getValue().getSubCorridors().entrySet().iterator().forEachRemaining(
							SubCorridor -> {
								logger.info("Sub Corridor  " + SubCorridor.getKey()
										+ " Light " + SubCorridor.getKey() + " : "
										+ (SubCorridor.getValue().isLight() ? "ON" : "OFF")
										+ " AC :" + (SubCorridor.getValue().isAc() ? "ON" : "OFF"));
							}
					);
				}
		);
	}

}

