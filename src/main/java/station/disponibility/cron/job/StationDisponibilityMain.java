package station.disponibility.cron.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import station.disponibility.cron.job.config.SshResourcesConfig;

@SpringBootApplication
@EnableConfigurationProperties(SshResourcesConfig.class)
public class StationDisponibilityMain {

    public static void main(String[] args) {
        System.out.println("Go!");
        SpringApplication.run(StationDisponibilityMain.class, args);
    }

//    @Bean
//    public CommandLineRunner run(SshConfig sshConfig) {
//        return args -> {
//            sshConfig.connectAndExecute();
//        };
//    }
}
