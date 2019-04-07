package hotel.management.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Floor {

	private String floorName = "Floor ";
	private Map<Integer, MainCorridor> mainCorridors = new HashMap<>();
	private Map<Integer, SubCorridor> subCorridors = new HashMap<>();

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public Map<Integer, MainCorridor> getMainCorridors() {
		return mainCorridors;
	}

	public void setMainCorridors(Map<Integer, MainCorridor> mainCorridors) {
		this.mainCorridors = mainCorridors;
	}

	public Map<Integer, SubCorridor> getSubCorridors() {
		return subCorridors;
	}

	public void setSubCorridors(Map<Integer, SubCorridor> subCorridors) {
		this.subCorridors = subCorridors;
	}
}
