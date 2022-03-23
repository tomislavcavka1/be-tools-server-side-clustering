package hr.axion.serverside.clustering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ServerSideClusteringDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerSideClusteringDemoApplication.class, args);
	}

}
