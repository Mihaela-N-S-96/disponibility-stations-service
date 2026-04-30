package station.disponibility.cron.job.dto;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class StatiiGnss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cod_statie")
    private String codStatie;

    @Column(name = "data_of")
    private LocalDate dataOf;
    private String nume_doc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodStatie() {
        return codStatie;
    }

    public void setCodStatie(String codStatie) {
        this.codStatie = codStatie;
    }

    public LocalDate getDataOf() {
        return dataOf;
    }

    public void setDataOf(LocalDate dataOf) {
        this.dataOf = dataOf;
    }

    public String getNume_doc() {
        return nume_doc;
    }

    public void setNume_doc(String nume_doc) {
        this.nume_doc = nume_doc;
    }
}
