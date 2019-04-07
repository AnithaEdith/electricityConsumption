package hotel.management.utility;

import hotel.management.model.Floor;
import hotel.management.model.MainCorridor;
import hotel.management.model.SubCorridor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class floorHelperTest {

	Floor Floor_1Main_2Sub = new Floor();

	floorHelper floorHelper = new floorHelper();

	Floor Floor_1Main_1Sub = new Floor();
	Floor Floor_1Main_2Sub_DefaultState = new Floor();

	@Autowired
	floorHelper mockFloorHelper;

	@Before
	public void setUp() {

		MainCorridor mainCorridor = new MainCorridor(true, true);
		SubCorridor subCorridorOne = new SubCorridor(true, true);
		SubCorridor subCorridorTwo = new SubCorridor(true, true);
		Map<Integer, MainCorridor> mainCorridors = new HashMap<>();
		mainCorridors.put(1, mainCorridor);
		Map<Integer, SubCorridor> subCorridors = new HashMap<>();
		subCorridors.put(1, subCorridorOne);
		subCorridors.put(2, subCorridorTwo);
		Floor_1Main_2Sub.setMainCorridors(mainCorridors);
		Floor_1Main_2Sub.setSubCorridors(subCorridors);

		Map<Integer, SubCorridor> subCorridors_1Sub = new HashMap<>();
		subCorridors_1Sub.put(1, subCorridorOne);
		Floor_1Main_1Sub.setMainCorridors(mainCorridors);
		Floor_1Main_1Sub.setSubCorridors(subCorridors_1Sub);

		SubCorridor subCorridorOneDefault = new SubCorridor(false, true);
		SubCorridor subCorridorTwoDefault = new SubCorridor(false, true);
		Map<Integer, SubCorridor> subCorridorsDefault = new HashMap<>();
		subCorridorsDefault.put(1, subCorridorOneDefault);
		subCorridorsDefault.put(2, subCorridorTwoDefault);
		Floor_1Main_2Sub_DefaultState.setMainCorridors(mainCorridors);
		Floor_1Main_2Sub_DefaultState.setSubCorridors(subCorridorsDefault);

	}

	@Test
	public void getAllowedPowerConsumption() {
		int allowedPowerConsumption = floorHelper.getAllowedPowerConsumption(Floor_1Main_2Sub);
		Assert.assertEquals(35, allowedPowerConsumption);
	}

	@Test
	public void getActualPowerConsumption_1Main_2Sub_Corridors() {
		int actualPowerConsumption = floorHelper.getActualPowerConsumption(Floor_1Main_2Sub);
		Assert.assertEquals(45, actualPowerConsumption);
	}

	@Test
	public void getActualPowerConsumption_1Main_1Sub_Corridors() {

		int actualPowerConsumption = floorHelper.getActualPowerConsumption(Floor_1Main_1Sub);
		Assert.assertEquals(30, actualPowerConsumption);
	}

	@Test
	public void getActualPowerConsumption_1Main_2Sub_Corridors_DefaultState() {
		int actualPowerConsumption = floorHelper.getActualPowerConsumption(Floor_1Main_2Sub_DefaultState);
		Assert.assertEquals(35, actualPowerConsumption);
	}

	@Test
	public void getActualPowerConsumption_0Main_0Sub_Corridors() {
		Floor Floor_0Main_0Sub = new Floor();
		int actualPowerConsumption = floorHelper.getActualPowerConsumption(Floor_0Main_0Sub);
		Assert.assertEquals(0, actualPowerConsumption);
	}

	@Test
	public void calculatePowerConsumptionConsumption_0Main_0Sub_Corridors() {
		Floor Floor_0Main_0Sub = new Floor();
		boolean powerConsumption = floorHelper.hasExceededPowerConsumption(Floor_0Main_0Sub);
		Assert.assertEquals(false, powerConsumption);
	}

	@Test
	public void calculatePowerConsumptionConsumption_1Main_2Sub_Corridors_DefaultState() {
		boolean powerConsumption = floorHelper.hasExceededPowerConsumption(Floor_1Main_2Sub_DefaultState);
		Assert.assertEquals(false, powerConsumption);
	}

	@Test
	public void calculatePowerConsumptionConsumption_1Main_2Sub_Corridors() {
		boolean powerConsumption = floorHelper.hasExceededPowerConsumption(Floor_1Main_2Sub);
		Assert.assertEquals(true, powerConsumption);
	}

	@Test
	public void calculatePowerConsumptionConsumption_1Main_1Sub_Corridors() {
		boolean powerConsumption = floorHelper.hasExceededPowerConsumption(Floor_1Main_1Sub);
		Assert.assertEquals(true, powerConsumption);
	}

	@Test
	public void toggleSubCorridorLight_DefaultState() {
		Assert.assertEquals(false, Floor_1Main_2Sub_DefaultState.getSubCorridors().get(1).isLight());
		Floor subCorridorMovement = floorHelper.toggleSubCorridorLight(1, Floor_1Main_2Sub_DefaultState, true);
		Assert.assertEquals(true, subCorridorMovement.getSubCorridors().get(1).isLight());
	}

	@Test
	public void toggleSubCorridorLight_1Main_1Sub_Corridors() {
		Assert.assertEquals(true, Floor_1Main_1Sub.getSubCorridors().get(1).isLight());
		Floor subCorridorMovement = floorHelper.toggleSubCorridorLight(1, Floor_1Main_1Sub, false);
		Assert.assertEquals(false, subCorridorMovement.getSubCorridors().get(1).isLight());
	}

	@Test
	public void toggleSubCorridorAc_DefaultState() {
		Assert.assertEquals(false, Floor_1Main_2Sub_DefaultState.getSubCorridors().get(1).isLight());
		Floor subCorridorMovement = floorHelper.toggleSubCorridorAc(1, Floor_1Main_2Sub_DefaultState, true);
		Assert.assertEquals(true, subCorridorMovement.getSubCorridors().get(1).isAc());
	}

	@Test
	public void toggleSubCorridorAc_1Main_1Sub_Corridors() {
		Assert.assertEquals(true, Floor_1Main_1Sub.getSubCorridors().get(1).isAc());
		Floor subCorridorMovement = floorHelper.toggleSubCorridorAc(1, Floor_1Main_1Sub, false);
		Assert.assertEquals(false, subCorridorMovement.getSubCorridors().get(1).isAc());
	}
/*
	@Test
	public void handleSubCorridorMovement_DefaultState() {
		Assert.assertEquals(false, Floor_1Main_2Sub_DefaultState.getSubCorridors().get(1).isLight());
		Assert.assertEquals(true, Floor_1Main_2Sub_DefaultState.getSubCorridors().get(1).isAc());
		Floor subCorridorMovement = floorHelper.handleSubCorridorMovement(1, Floor_1Main_2Sub_DefaultState);
		Assert.assertEquals(true, subCorridorMovement.getSubCorridors().get(1).isLight());
		Assert.assertEquals(false, subCorridorMovement.getSubCorridors().get(1).isAc());
	}

	@Test
	public void handleSubCorridorMovement_1Main_1Sub_Corridors() {
		Assert.assertEquals(true, Floor_1Main_1Sub.getSubCorridors().get(1).isLight());
		Assert.assertEquals(true, Floor_1Main_1Sub.getSubCorridors().get(1).isAc());
		Floor subCorridorMovement = mockFloorHelper.handleSubCorridorMovement(1, Floor_1Main_1Sub);
		Assert.assertEquals(true, subCorridorMovement.getSubCorridors().get(1).isLight());
		Assert.assertEquals(false, subCorridorMovement.getSubCorridors().get(1).isAc());
	}

	@Test
	public void handleSubCorridorMovement_DefaultState_AterDelay() throws InterruptedException {
		Assert.assertEquals(false, Floor_1Main_2Sub_DefaultState.getSubCorridors().get(1).isLight());
		Assert.assertEquals(true, Floor_1Main_2Sub_DefaultState.getSubCorridors().get(1).isAc());
		//Mockito.doNothing().when(mockfloorHelper).scheduleResetSubCorridor(Mockito.any(Integer.class), Mockito.any(Floor.class));
		Floor subCorridorMovement = mockFloorHelper.handleSubCorridorMovement(1, Floor_1Main_2Sub_DefaultState);
		Assert.assertEquals(false, subCorridorMovement.getSubCorridors().get(1).isLight());
//		Assert.assertEquals(true, subCorridorMovement.getSubCorridors().get(1).isAc());
	}
*/
}
