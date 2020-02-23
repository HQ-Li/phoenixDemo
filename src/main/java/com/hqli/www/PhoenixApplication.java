package com.hqli.www;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class PhoenixApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhoenixApplication.class, args);
    }

    @Configuration
    @Profile("default")
    static class LocalConfiguration {
        Logger logger = LoggerFactory.getLogger(LocalConfiguration.class);

        @Value("${purl}")
        private String databaseUri;

        @Value("${pdriver}")
        private String databaseDriver;

        @Bean
        public Connection connection(){
            logger.info("databaseUri:" + databaseUri);
            logger.info("databaseDriver:" + databaseDriver);
            Connection con = null;
            try {
                Class.forName(databaseDriver);

                con = DriverManager.getConnection(databaseUri);
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("Connection fail: ", e);
            }catch (Exception e){
                logger.error(e.toString());
            }

            //dataSource.setDriverClassName("org.apache.phoenix.jdbc.PhoenixDriver");
            //logger.error("Initialized hbase");
            return con;
        }
    }
}
