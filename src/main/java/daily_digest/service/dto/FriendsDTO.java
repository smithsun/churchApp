package daily_digest.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link daily_digest.domain.Friends} entity.
 */
public class FriendsDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    private String cellPhone;

    private ZonedDateTime lastUsedTime;

    private Long intimacyOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public ZonedDateTime getLastUsedTime() {
        return lastUsedTime;
    }

    public void setLastUsedTime(ZonedDateTime lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    public Long getIntimacyOrder() {
        return intimacyOrder;
    }

    public void setIntimacyOrder(Long intimacyOrder) {
        this.intimacyOrder = intimacyOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FriendsDTO)) {
            return false;
        }

        FriendsDTO friendsDTO = (FriendsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, friendsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FriendsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", cellPhone='" + getCellPhone() + "'" +
            ", lastUsedTime='" + getLastUsedTime() + "'" +
            ", intimacyOrder=" + getIntimacyOrder() +
            "}";
    }
}
