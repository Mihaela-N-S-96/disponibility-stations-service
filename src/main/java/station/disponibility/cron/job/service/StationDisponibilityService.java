package station.disponibility.cron.job.service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import station.disponibility.cron.job.config.SshResourcesConfig;
import station.disponibility.cron.job.dto.StatiiGnss;
import station.disponibility.cron.job.dto.StationDto;
import station.disponibility.cron.job.dto.Stations;
import station.disponibility.cron.job.repository.StationDisponibilityRepository;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class StationDisponibilityService {
    private SshResourcesConfig sshResourcesConfig;
    private StationDisponibilityRepository stationDisponibilityRepository;

    public void findDisponibility(Session session) {
        for(int i=1; i<=118; i++) { //TODO: this will be deleted

            for (Stations station : Stations.values()) {
                String stationPath = getStationPath(station.name(), i);

                if (fileExists(session, stationPath)) {
                    StatiiGnss statiiGnss = new StatiiGnss();
                    statiiGnss.setCod_statie(station.name());
//                    statiiGnss.setData_of(LocalDate.now());
                    statiiGnss.setData_of(LocalDate.ofYearDay(2026, i));
                    statiiGnss.setNume_doc(stationPath.substring(stationPath.length() - 41));
//                    System.out.println(statiiGnss.getNume_doc());
                    stationDisponibilityRepository.save(statiiGnss);
                }
            }
        }
    }

    private String getStationPath(String stationName, int nmbOfDay) {
        StationDto stationDto = new StationDto(LocalDate.now());
        String station = stationName.concat("00ROU_R_").concat(stationDto.getYear().toString())
//                .concat(String.format("%03d",stationDto.getDay()))
                .concat(String.format("%03d",nmbOfDay))
                .concat("0000_01D_30S_MO.crx.gz");
        return sshResourcesConfig.getPath().concat(stationDto.getYear().toString()).concat("/")
//                .concat(String.format("%03d", stationDto.getDay()))
                .concat(String.format("%03d",nmbOfDay))
                .concat("/")
                .concat(station);
    }

    public boolean fileExists(Session session, String path) {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            channelSftp.stat(path);
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
