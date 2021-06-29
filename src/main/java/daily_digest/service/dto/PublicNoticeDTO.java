package daily_digest.service.dto;

import daily_digest.domain.enumeration.NoticeType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link daily_digest.domain.PublicNotice} entity.
 */
public class PublicNoticeDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private NoticeType type;

    @NotNull
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NoticeType getType() {
        return type;
    }

    public void setType(NoticeType type) {
        this.type = type;
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
        if (!(o instanceof PublicNoticeDTO)) {
            return false;
        }

        PublicNoticeDTO publicNoticeDTO = (PublicNoticeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, publicNoticeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicNoticeDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", type='" + getType() + "'" +
            ", content='" + getContent() + "'" +
            ", lastUpdateBy='" + getLastUpdateBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", eventDate='" + getEventDate() + "'" +
            ", publication=" + getPublication() +
            "}";
    }
}
