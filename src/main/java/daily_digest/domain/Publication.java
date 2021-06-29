package daily_digest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Publication.
 */
@Entity
@Table(name = "publication")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Publication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @Column(name = "priority")
    private Long priority;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_update_by")
    private String lastUpdateBy;

    @Column(name = "org_signature")
    private String orgSignature;

    @JsonIgnoreProperties(value = { "publication" }, allowSetters = true)
    @OneToOne(mappedBy = "publication")
    private Digests digests;

    @JsonIgnoreProperties(value = { "publication" }, allowSetters = true)
    @OneToOne(mappedBy = "publication")
    private PublicNotice publicNotice;

    @JsonIgnoreProperties(value = { "publication" }, allowSetters = true)
    @OneToOne(mappedBy = "publication")
    private Videos videos;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Publication id(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public Publication type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ZonedDateTime getStartDate() {
        return this.startDate;
    }

    public Publication startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return this.endDate;
    }

    public Publication endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getPriority() {
        return this.priority;
    }

    public Publication priority(Long priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Publication createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdateBy() {
        return this.lastUpdateBy;
    }

    public Publication lastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
        return this;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getOrgSignature() {
        return this.orgSignature;
    }

    public Publication orgSignature(String orgSignature) {
        this.orgSignature = orgSignature;
        return this;
    }

    public void setOrgSignature(String orgSignature) {
        this.orgSignature = orgSignature;
    }

    public Digests getDigests() {
        return this.digests;
    }

    public Publication digests(Digests digests) {
        this.setDigests(digests);
        return this;
    }

    public void setDigests(Digests digests) {
        if (this.digests != null) {
            this.digests.setPublication(null);
        }
        if (digests != null) {
            digests.setPublication(this);
        }
        this.digests = digests;
    }

    public PublicNotice getPublicNotice() {
        return this.publicNotice;
    }

    public Publication publicNotice(PublicNotice publicNotice) {
        this.setPublicNotice(publicNotice);
        return this;
    }

    public void setPublicNotice(PublicNotice publicNotice) {
        if (this.publicNotice != null) {
            this.publicNotice.setPublication(null);
        }
        if (publicNotice != null) {
            publicNotice.setPublication(this);
        }
        this.publicNotice = publicNotice;
    }

    public Videos getVideos() {
        return this.videos;
    }

    public Publication videos(Videos videos) {
        this.setVideos(videos);
        return this;
    }

    public void setVideos(Videos videos) {
        if (this.videos != null) {
            this.videos.setPublication(null);
        }
        if (videos != null) {
            videos.setPublication(this);
        }
        this.videos = videos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Publication)) {
            return false;
        }
        return id != null && id.equals(((Publication) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Publication{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", priority=" + getPriority() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastUpdateBy='" + getLastUpdateBy() + "'" +
            ", orgSignature='" + getOrgSignature() + "'" +
            "}";
    }
}
