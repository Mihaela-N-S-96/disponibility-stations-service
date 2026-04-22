package station.disponibility.cron.job.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class StatiiGnss {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cod_statie;
    private String nume_doc;
    private LocalDate data_of;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCod_statie() {
        return cod_statie;
    }

    public void setCod_statie(String cod_statie) {
        this.cod_statie = cod_statie;
    }

    public String getNume_doc() {
        return nume_doc;
    }

    public void setNume_doc(String nume_doc) {
        this.nume_doc = nume_doc;
    }

    public LocalDate getData_of() {
        return data_of;
    }

    public void setData_of(LocalDate data_of) {
        this.data_of = data_of;
    }
}
