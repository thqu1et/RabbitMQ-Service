package kz.thquiet.data_streaming;

import kz.thquiet.data_streaming.config.RabbitConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class DataStreamingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataStreamingApplication.class, args);
    }

}
