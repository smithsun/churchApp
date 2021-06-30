package daily_digest.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link daily_digest.domain.DailyVerses} entity.
 */
public class DailyVersesDTO implements Serializable {

    private Long id;

    private String verses;

    private Long order;

    private DigestsDTO digests;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVerses() {
        return verses;
    }

    public void setVerses(String verses) {
        this.verses = verses;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public DigestsDTO getDigests() {
        return digests;
    }

    public void setDigests(DigestsDTO digests) {
        this.digests = digests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DailyVersesDTO)) {
            return false;
        }

        DailyVersesDTO dailyVersesDTO = (DailyVersesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dailyVersesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DailyVersesDTO{" +
            "id=" + getId() +
            ", verses='" + getVerses() + "'" +
            ", order=" + getOrder() +
            ", digests=" + getDigests() +
            "}";
    }
}
