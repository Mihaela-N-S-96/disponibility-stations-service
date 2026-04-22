package station.disponibility.cron.job.service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import station.disponibility.cron.job.config.SshConfig;
import station.disponibility.cron.job.config.SshResourcesConfig;
import station.disponibility.cron.job.dto.StationDto;
import station.disponibility.cron.job.dto.Stations;

import java.time.LocalDate;
import java.util.spi.LocaleServiceProvider;

@Service
@AllArgsConstructor
public class StationDisponibilityService {
    private SshResourcesConfig sshResourcesConfig;

    public void findDisponibility(Session session) {
        for (Stations station : Stations.values()) {
            String stationPath = getStationPath(station.name());
            System.out.println(stationPath);

            System.out.println( fileExists(session, stationPath));
        }
    }

    private String getStationPath(String stationName){
        StationDto stationDto = new StationDto(LocalDate.now());
        String station = stationName.concat("00ROU_R_").concat(stationDto.getYear().toString())
//                .concat(String.format("%03d",stationDto.getDay()))
                .concat(String.format("%03d",stationDto.getDay()))
                .concat("0000_01D_30S_MO.");
      return  sshResourcesConfig.getPath().concat(stationDto.getYear().toString()).concat("/")
                .concat(String.format("%03d",stationDto.getDay())).concat("/")
                .concat(station);
      //ARYB00ROU_R_20260010000_01D_30S_MO.crx.gz
      // ARYB00ROU_R_20260010000_01D_30S_MO.rnx.gz
    }

    public boolean fileExists(Session session, String path) {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            channelSftp.stat(path); // dacă nu există → aruncă excepție
            return true;

        } catch (SftpException e) {
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Eroare la verificare fișier", e);
        } finally {
            if (channelSftp != null) {
                channelSftp.disconnect();
            }
        }
    }
}
