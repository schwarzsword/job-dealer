package edu.netcracker.jobdealer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "edu.netcracker.jobdealer")
public class JobDealerApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobDealerApplication.class, args);
    }
}
