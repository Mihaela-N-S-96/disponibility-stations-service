package station.disponibility.cron.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import station.disponibility.cron.job.dto.StatiiGnss;

@Repository
public interface StationDisponibilityRepository extends JpaRepository<StatiiGnss, Long> {

}
