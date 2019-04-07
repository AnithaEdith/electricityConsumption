package hotel.management.utility;

import hotel.management.model.Floor;
import hotel.management.model.MainCorridor;
import hotel.management.model.SubCorridor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class floorHelper {

	@Value("${subCorridor.WaitPeriod}")
	private int WaitPeriod = 2000;

	@Value("${subCorridor.LightPowerConsumptionPerUnit}")
	private int LightPowerConsumptionPerUnit = 5;

	@Value("${subCorridor.AcPowerConsumptionPerUnit}")
	private int AcPowerConsumptionPerUnit = 10;

	@Value("${hotel.MainCorridorsPowerConsumption}")
	private int MainCorridorsPowerConsumption = 15;

	@Value("${hotel.SubCorridorsPowerConsumption}")
	private int SubCorridorsPowerConsumption = 10;

	@Autowired
	private ExecutorService executorService;

	private Map<Integer, Future<Long>> floorFutures = new HashMap<>();

	public Floor handleSubCorridorMovement(int subCorridorIndex, int floorIndex,  Floor floorInMovement) {
		Floor floor = toggleSubCorridorLight(subCorridorIndex, floorInMovement, true);

		if (hasExceededPowerConsumption(floorInMovement)) {
			floor = toggleSubCorridorAc(subCorridorIndex, floor, false);
		}

		scheduleResetSubCorridor(subCorridorIndex, floorIndex, floorInMovement);
		return floor;
	}

	public void scheduleResetSubCorridor(int subCorridorIndex, int floorIndex, Floor floorInMovement) {
		Future future = floorFutures.get(floorIndex);
		Callable callableTask = () -> {
			try {
				TimeUnit.SECONDS.sleep(WaitPeriod);
				toggleSubCorridorLight(subCorridorIndex, floorInMovement, false);
				toggleSubCorridorAc(subCorridorIndex, floorInMovement, true);
			} catch (InterruptedException e) {
			}
			return floorInMovement;
		};

		if (future != null) {
			future.cancel(true);
		}

		future = executorService.submit(callableTask);
		floorFutures.put(floorIndex,future);
	}

	public Floor toggleSubCorridorLight(int subCorridorIndex, Floor floorInMovement, boolean flag) {
		floorInMovement.getSubCorridors().entrySet().stream()
				.filter(subCorridor -> subCorridor.getKey() == subCorridorIndex)
				.map(subCorridor -> {
					subCorridor.getValue().setLight(flag);
					return subCorridor;
				})
				.collect(Collectors.toList());
		return floorInMovement;
	}

	public Floor toggleSubCorridorAc(int subCorridorIndex, Floor floorInMovement, boolean flag) {
		floorInMovement.getSubCorridors().entrySet().stream()
				.filter(subCorridor -> subCorridor.getKey() == subCorridorIndex)
				.map(subCorridor -> {
					subCorridor.getValue().setAc(flag);
					return subCorridor;
				})
				.collect(Collectors.toList());
		return floorInMovement;
	}

	public boolean hasExceededPowerConsumption(Floor floorInMovement) {
		int totalAllowedPowerConsumption = getAllowedPowerConsumption(floorInMovement);
		int actualPowerConsumption = getActualPowerConsumption(floorInMovement);

		return actualPowerConsumption > totalAllowedPowerConsumption;
	}

	public int getActualPowerConsumption(Floor floorInMovement) {
		AtomicInteger atomicInteger = new AtomicInteger(0);

		floorInMovement.getMainCorridors().forEach((index, mainCorridor) -> {
			atomicInteger.addAndGet((mainCorridor.isLight() ? LightPowerConsumptionPerUnit : 0) + (mainCorridor.isAc() ? AcPowerConsumptionPerUnit : 0));
		});

		floorInMovement.getSubCorridors().forEach((index, subCorridor) -> {
			atomicInteger.addAndGet((subCorridor.isLight() ? LightPowerConsumptionPerUnit : 0) + (subCorridor.isAc() ? AcPowerConsumptionPerUnit : 0));
		});

		return atomicInteger.get();
	}

	public int getAllowedPowerConsumption(Floor floorInMovement) {
		int numberOfMainCorridors = floorInMovement.getMainCorridors().size();
		int numberOfSubCorridors = floorInMovement.getSubCorridors().size();

		return numberOfMainCorridors * MainCorridorsPowerConsumption + numberOfSubCorridors * SubCorridorsPowerConsumption;
	}
}
