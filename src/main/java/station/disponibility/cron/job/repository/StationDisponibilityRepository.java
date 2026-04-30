package station.disponibility.cron.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import station.disponibility.cron.job.dto.StatiiGnss;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StationDisponibilityRepository extends JpaRepository<StatiiGnss, Long> {

//    Optional<StatiiGnss> findStatiiGnssesByCod_statieAndAndData_of(String cod_statie, LocalDate data_of);
Optional<StatiiGnss> findByCodStatieAndDataOf(
        String codStatie,
        LocalDate dataOf
);
}
