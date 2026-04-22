package station.disponibility.cron.job.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "ssh")
public class SshResourcesConfig {
    private String host;
    private Integer port;
    private String user;
    private String password;
    private String path;
}
