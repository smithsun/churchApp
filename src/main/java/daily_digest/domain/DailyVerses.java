package daily_digest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DailyVerses.
 */
@Entity
@Table(name = "daily_verses")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DailyVerses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "verses")
    private String verses;

    @Column(name = "jhi_order")
    private Long order;

    @ManyToOne
    @JsonIgnoreProperties(value = { "publication", "dailyVerses" }, allowSetters = true)
    private Digests digests;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DailyVerses id(Long id) {
        this.id = id;
        return this;
    }

    public String getVerses() {
        return this.verses;
    }

    public DailyVerses verses(String verses) {
        this.verses = verses;
        return this;
    }

    public void setVerses(String verses) {
        this.verses = verses;
    }

    public Long getOrder() {
        return this.order;
    }

    public DailyVerses order(Long order) {
        this.order = order;
        return this;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Digests getDigests() {
        return this.digests;
    }

    public DailyVerses digests(Digests digests) {
        this.setDigests(digests);
        return this;
    }

    public void setDigests(Digests digests) {
        this.digests = digests;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DailyVerses)) {
            return false;
        }
        return id != null && id.equals(((DailyVerses) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DailyVerses{" +
            "id=" + getId() +
            ", verses='" + getVerses() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
