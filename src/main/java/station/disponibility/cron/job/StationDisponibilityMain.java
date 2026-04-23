package station.disponibility.cron.job;

import com.jcraft.jsch.JSchException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import station.disponibility.cron.job.config.SshConfig;
import station.disponibility.cron.job.config.SshResourcesConfig;

@SpringBootApplication
@EnableConfigurationProperties(SshResourcesConfig.class)
public class StationDisponibilityMain {
//    public static void main(String args[]) throws JSchException {
//        System.out.println("Go!");
//
//        var context = SpringApplication.run(StationDisponibilityMain.class, args);
//
//        SshConfig sshConfig = context.getBean(SshConfig.class);
//        sshConfig.connectAndExecute();
//    }

    public static void main(String[] args) {
        System.out.println("Go!");
        SpringApplication.run(StationDisponibilityMain.class, args);
    }

    @Bean
    public CommandLineRunner run(SshConfig sshConfig) {
        return args -> {
            sshConfig.connectAndExecute();
        };
    }
}
