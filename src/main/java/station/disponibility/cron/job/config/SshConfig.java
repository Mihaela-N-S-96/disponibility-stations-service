package station.disponibility.cron.job.config;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import station.disponibility.cron.job.service.StationDisponibilityService;

@Component
public class SshConfig {
    private SshResourcesConfig sshResourcesConfig;
    private StationDisponibilityService service;

    public SshConfig(SshResourcesConfig sshResourcesConfig,
                     StationDisponibilityService service) {
        this.sshResourcesConfig = sshResourcesConfig;
        this.service = service;
    }

    public Session connectAndExecute() throws JSchException {
        Session session = null;

        JSch jsch = new JSch();
        try {
            session = jsch.getSession(sshResourcesConfig.getUser(), sshResourcesConfig.getHost(), sshResourcesConfig.getPort());
            session.setPassword(sshResourcesConfig.getPassword());

            session.setConfig("StrictHostKeyChecking", "no");

            session.connect(5000);
            service.findDisponibility(session);
            System.out.println("Succes!");
        } catch (Exception ex) {
            System.out.println("exception on connection!");
        }
        return session;

    }
}
