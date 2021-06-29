package daily_digest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Videos.
 */
@Entity
@Table(name = "videos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Videos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;

    @Column(name = "content")
    private String content;

    @Column(name = "notes")
    private String notes;

    @Column(name = "key_words")
    private String keyWords;

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

    public Videos id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Videos title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public Videos type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }

    public Videos content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNotes() {
        return this.notes;
    }

    public Videos notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getKeyWords() {
        return this.keyWords;
    }

    public Videos keyWords(String keyWords) {
        this.keyWords = keyWords;
        return this;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getLastUpdateBy() {
        return this.lastUpdateBy;
    }

    public Videos lastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
        return this;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getStatus() {
        return this.status;
    }

    public Videos status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getEventDate() {
        return this.eventDate;
    }

    public Videos eventDate(ZonedDateTime eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    public void setEventDate(ZonedDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public Publication getPublication() {
        return this.publication;
    }

    public Videos publication(Publication publication) {
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
        if (!(o instanceof Videos)) {
            return false;
        }
        return id != null && id.equals(((Videos) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Videos{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", type='" + getType() + "'" +
            ", content='" + getContent() + "'" +
            ", notes='" + getNotes() + "'" +
            ", keyWords='" + getKeyWords() + "'" +
            ", lastUpdateBy='" + getLastUpdateBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", eventDate='" + getEventDate() + "'" +
            "}";
    }
}
