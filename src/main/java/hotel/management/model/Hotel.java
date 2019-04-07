package hotel.management.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hotel {
	public Map<Integer, Floor> getHotelFloors() {
		return hotelFloors;
	}

	public void setHotelFloors(Map<Integer, Floor> hotelFloors) {
		this.hotelFloors = hotelFloors;
	}

	private Map<Integer, Floor> hotelFloors = new HashMap<>();

}
