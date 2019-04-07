package hotel.management.service;

import hotel.management.model.Floor;
import hotel.management.model.Hotel;
import hotel.management.model.MainCorridor;
import hotel.management.model.SubCorridor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class hotelServiceTest {
	Floor Floor_1Main_2Sub_DefaultState = new Floor();
	Hotel hotel = new Hotel();
	Map<Integer, Floor> hotelFloors  = new HashMap<>();

	hotelService hotelService = new hotelService();
	@Autowired
	hotel.management.utility.floorHelper floorHelper;

	@Before
	public void setUp() {

		MainCorridor mainCorridor = new MainCorridor(true, true);
		SubCorridor subCorridorOne = new SubCorridor(false, true);
		SubCorridor subCorridorTwo = new SubCorridor(false, true);
		Map<Integer, MainCorridor> mainCorridors = new HashMap<>();
		mainCorridors.put(1, mainCorridor);
		Map<Integer, SubCorridor> subCorridors = new HashMap<>();
		subCorridors.put(1, subCorridorOne);
		subCorridors.put(2, subCorridorTwo);
		Floor_1Main_2Sub_DefaultState.setMainCorridors(mainCorridors);
		Floor_1Main_2Sub_DefaultState.setSubCorridors(subCorridors);
		hotelFloors.put(1, Floor_1Main_2Sub_DefaultState);
		hotel.setHotelFloors(hotelFloors);
	}

	@Test
	public void printHotelTest() {
		hotelService.printHotel(hotel);
	}

}
