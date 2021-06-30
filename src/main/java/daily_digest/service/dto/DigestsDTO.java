package daily_digest.service.dto;

import daily_digest.domain.enumeration.DigestType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link daily_digest.domain.Digests} entity.
 */
public class DigestsDTO implements Serializable {

    private Long id;

    @NotNull
    private DigestType type;

    @NotNull
    private String title;

    private String imgVerse;

    private String prayReadVerse;

    @Lob
    private String content;

    private String lastUpdateBy;

    private String status;

    private ZonedDateTime eventDate;

    private PublicationDTO publication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DigestType getType() {
        return type;
    }

    public void setType(DigestType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgVerse() {
        return imgVerse;
    }

    public void setImgVerse(String imgVerse) {
        this.imgVerse = imgVerse;
    }

    public String getPrayReadVerse() {
        return prayReadVerse;
    }

    public void setPrayReadVerse(String prayReadVerse) {
        this.prayReadVerse = prayReadVerse;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(ZonedDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public PublicationDTO getPublication() {
        return publication;
    }

    public void setPublication(PublicationDTO publication) {
        this.publication = publication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DigestsDTO)) {
            return false;
        }

        DigestsDTO digestsDTO = (DigestsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, digestsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DigestsDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", title='" + getTitle() + "'" +
            ", imgVerse='" + getImgVerse() + "'" +
            ", prayReadVerse='" + getPrayReadVerse() + "'" +
            ", content='" + getContent() + "'" +
            ", lastUpdateBy='" + getLastUpdateBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", eventDate='" + getEventDate() + "'" +
            ", publication=" + getPublication() +
            "}";
    }
}
