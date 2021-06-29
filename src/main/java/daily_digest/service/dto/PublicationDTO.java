package daily_digest.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link daily_digest.domain.Publication} entity.
 */
public class PublicationDTO implements Serializable {

    private Long id;

    @NotNull
    private String type;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private Long priority;

    private String createdBy;

    private String lastUpdateBy;

    private String orgSignature;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getOrgSignature() {
        return orgSignature;
    }

    public void setOrgSignature(String orgSignature) {
        this.orgSignature = orgSignature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublicationDTO)) {
            return false;
        }

        PublicationDTO publicationDTO = (PublicationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, publicationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicationDTO{" +
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
