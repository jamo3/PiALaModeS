package com.issinc.pialamodes;

import com.issinc.pialamodes.ingest.StratuxFeedIngest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
        //StaticDataIngest sdIngest = new StaticDataIngest();
        //sdIngest.ingest("C:\\ACFTREF.txt", "C:\\MASTER.txt");
        StratuxFeedIngest sfIngest = new StratuxFeedIngest();
        sfIngest.readFeed();
    }
}
