package hotel.management.controller;

import hotel.management.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import hotel.management.service.hotelService;

@RestController
public class hotelController {
	@Autowired
	Hotel hotel;

	@Autowired
	hotelService hotelService;

	@GetMapping("hotel")
	public Hotel printHotel() {
		hotelService.printHotel(hotel);
		return hotel;
	}

	@GetMapping("hotel/movement/{floor}/{subCorridor}")
	public Hotel floorMovementDetected(@PathVariable int floor, @PathVariable int subCorridor) {
		Hotel hotelAfterFloorMovement = hotelService.handleFloorMovementDetected(hotel, floor, subCorridor);
		return hotelAfterFloorMovement;
	}
}
