package daily_digest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import daily_digest.domain.enumeration.DigestType;
import java.io.Serializable;
import java.time.ZonedDateTime;
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
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private DigestType type;

    @Lob
    @Column(name = "content", nullable = false)
    private byte[] content;

    @Column(name = "content_content_type", nullable = false)
    private String contentContentType;

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

    public byte[] getContent() {
        return this.content;
    }

    public Digests content(byte[] content) {
        this.content = content;
        return this;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return this.contentContentType;
    }

    public Digests contentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
        return this;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
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
            ", title='" + getTitle() + "'" +
            ", type='" + getType() + "'" +
            ", content='" + getContent() + "'" +
            ", contentContentType='" + getContentContentType() + "'" +
            ", lastUpdateBy='" + getLastUpdateBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", eventDate='" + getEventDate() + "'" +
            "}";
    }
}
