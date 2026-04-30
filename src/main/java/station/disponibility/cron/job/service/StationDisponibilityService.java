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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
@AllArgsConstructor
public class StationDisponibilityService {
    private SshResourcesConfig sshResourcesConfig;
    private StationDisponibilityRepository stationDisponibilityRepository;

    public void findDisponibility(Session session) {
        LocalDate today = LocalDate.now();
        LocalDate lastWednesday = today.with(TemporalAdjusters.previous(DayOfWeek.WEDNESDAY));
        int startDay = lastWednesday.getDayOfYear();
        int finDay = startDay + 7;

        for (int i = 108; i <= 114; i++) {
            if (verifyBasePath(today.getYear(), i) != null) {
                for (Stations station : Stations.values()) {
                    String stationPath = getStationPath(station.name(), i);

                    if (fileExists(session, stationPath)) {
                        StatiiGnss statiiGnss = new StatiiGnss();
                        statiiGnss.setCodStatie(station.name());
                        statiiGnss.setDataOf(LocalDate.ofYearDay(2026, i));
                        statiiGnss.setNume_doc(stationPath.substring(stationPath.length() - 41));
                        if (stationDisponibilityRepository.findByCodStatieAndDataOf(statiiGnss.getCodStatie(), statiiGnss.getDataOf()).isEmpty()) {
                            stationDisponibilityRepository.save(statiiGnss);
                        }
                    }
                }
            } else {
                System.out.println("This day: " + i + " was not uploaded yet! ");
            }
        }
    }

    private String getStationPath(String stationName, int nmbOfDay) {
        StationDto stationDto = new StationDto(LocalDate.now());
        String station = stationName.concat("00ROU_R_").concat(stationDto.getYear().toString())
//                .concat(String.format("%03d",stationDto.getDay()))
                .concat(String.format("%03d", nmbOfDay))
                .concat("0000_01D_30S_MO.crx.gz");
        return sshResourcesConfig.getPath().concat(stationDto.getYear().toString()).concat("/")
//                .concat(String.format("%03d", stationDto.getDay()))
                .concat(String.format("%03d", nmbOfDay))
                .concat("/")
                .concat(station);
    }

    private String verifyBasePath(int year, int nmbOfDay) {
        System.out.println(sshResourcesConfig.getPath().concat(String.valueOf(year)).concat("/")
                .concat(String.format("%03d", nmbOfDay)));
        return sshResourcesConfig.getPath().concat(String.valueOf(year)).concat("/")
                .concat(String.format("%03d", nmbOfDay));
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
