package station.disponibility.cron.job.config;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Scheduled(cron = "0 0 12 * * WED")
    public void connectAndExecute() throws JSchException {
        Session session = null;
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(
                    sshResourcesConfig.getUser(),
                    sshResourcesConfig.getHost(),
                    sshResourcesConfig.getPort());
            session.setPassword(sshResourcesConfig.getPassword());

            session.setConfig("StrictHostKeyChecking", "no");

            session.connect(10000);
            service.findDisponibility(session);
        } catch (Exception ex) {
            System.out.println("exception on connection!"+ ex.getMessage()+ " - "+ ex + " - "+ex.getStackTrace());
        } finally {
            session.disconnect();
        }

    }
}
