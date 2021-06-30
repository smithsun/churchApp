package daily_digest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import daily_digest.domain.enumeration.DigestType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Digests.
 */
@Entity
@Table(name = "digests")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Digests implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private DigestType type;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "img_verse")
    private String imgVerse;

    @Column(name = "pray_read_verse")
    private String prayReadVerse;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "last_update_by")
    private String lastUpdateBy;

    @Column(name = "status")
    private String status;

    @Column(name = "event_date")
    private ZonedDateTime eventDate;

    @JsonIgnoreProperties(value = { "digests", "publicNotice", "videos" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Publication publication;

    @OneToMany(mappedBy = "digests")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "digests" }, allowSetters = true)
    private Set<DailyVerses> dailyVerses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Digests id(Long id) {
        this.id = id;
        return this;
    }

    public DigestType getType() {
        return this.type;
    }

    public Digests type(DigestType type) {
        this.type = type;
        return this;
    }

    public void setType(DigestType type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public Digests title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgVerse() {
        return this.imgVerse;
    }

    public Digests imgVerse(String imgVerse) {
        this.imgVerse = imgVerse;
        return this;
    }

    public void setImgVerse(String imgVerse) {
        this.imgVerse = imgVerse;
    }

    public String getPrayReadVerse() {
        return this.prayReadVerse;
    }

    public Digests prayReadVerse(String prayReadVerse) {
        this.prayReadVerse = prayReadVerse;
        return this;
    }

    public void setPrayReadVerse(String prayReadVerse) {
        this.prayReadVerse = prayReadVerse;
    }

    public String getContent() {
        return this.content;
    }

    public Digests content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLastUpdateBy() {
        return this.lastUpdateBy;
    }

    public Digests lastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
        return this;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getStatus() {
        return this.status;
    }

    public Digests status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getEventDate() {
        return this.eventDate;
    }

    public Digests eventDate(ZonedDateTime eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    public void setEventDate(ZonedDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public Publication getPublication() {
        return this.publication;
    }

    public Digests publication(Publication publication) {
        this.setPublication(publication);
        return this;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public Set<DailyVerses> getDailyVerses() {
        return this.dailyVerses;
    }

    public Digests dailyVerses(Set<DailyVerses> dailyVerses) {
        this.setDailyVerses(dailyVerses);
        return this;
    }

    public Digests addDailyVerses(DailyVerses dailyVerses) {
        this.dailyVerses.add(dailyVerses);
        dailyVerses.setDigests(this);
        return this;
    }

    public Digests removeDailyVerses(DailyVerses dailyVerses) {
        this.dailyVerses.remove(dailyVerses);
        dailyVerses.setDigests(null);
        return this;
    }

    public void setDailyVerses(Set<DailyVerses> dailyVerses) {
        if (this.dailyVerses != null) {
            this.dailyVerses.forEach(i -> i.setDigests(null));
        }
        if (dailyVerses != null) {
            dailyVerses.forEach(i -> i.setDigests(this));
        }
        this.dailyVerses = dailyVerses;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Digests)) {
            return false;
        }
        return id != null && id.equals(((Digests) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Digests{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", title='" + getTitle() + "'" +
            ", imgVerse='" + getImgVerse() + "'" +
            ", prayReadVerse='" + getPrayReadVerse() + "'" +
            ", content='" + getContent() + "'" +
            ", lastUpdateBy='" + getLastUpdateBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", eventDate='" + getEventDate() + "'" +
            "}";
    }
}
