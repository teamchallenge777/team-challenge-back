package team.challenge.MobileStore;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import team.challenge.MobileStore.config.AppProperties;

@SpringBootApplication
@EnableMongock
@EnableConfigurationProperties(AppProperties.class)
public class MobileStoreApplication {


	public static void main(String[] args) {
		SpringApplication.run(MobileStoreApplication.class, args);
	}
}