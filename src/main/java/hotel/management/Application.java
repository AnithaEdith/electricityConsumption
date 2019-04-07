package hotel.management;

import com.google.gson.Gson;
import hotel.management.model.Hotel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import hotel.management.service.hotelService;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


@SpringBootApplication
public class Application {

	@Autowired
	private hotelService hotelService;

	@Value("${hotel.floorCount}")
	private int floorCount = 2;

	@Value("${hotel.mainCorridorCount}")
	private int mainCorridorCount = 1;

	@Value("${hotel.subCorridorCount}")
	private int subCorridorCount = 2;

	public static void main(String args[]) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Hotel createHotel() {
		Hotel hotel = hotelService.createHotel(floorCount, mainCorridorCount, subCorridorCount);
		Gson gson = new Gson();
		System.out.println(gson.toJson(hotel));
		return hotel;
	}

	@Bean
	ExecutorService taskExecutor() {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		return executor;
	}

}
