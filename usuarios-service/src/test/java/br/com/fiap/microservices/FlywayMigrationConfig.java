//package br.com.fiap.microservices;
//
//import org.flywaydb.core.Flyway;
//import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//
//@TestConfiguration
//public class FlywayMigrationConfig {
//
//@Bean
//public static FlywayMigrationStrategy cleanMigrateStrategy(){
//
//        return flyway -> {
//                flyway.clean();
//                flyway.migrate();
//                };
//        }
//}