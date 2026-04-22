package station.disponibility.cron.job.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StationDto {
    private Integer year;
    private Integer month;
    private Integer day;


    public StationDto(LocalDate data) {
        this.year = data.getYear();
        this.month = data.getMonth().getValue();
        this.day = data.getDayOfYear();
    }
}
