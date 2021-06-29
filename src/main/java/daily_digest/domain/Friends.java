package daily_digest.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Friends.
 */
@Entity
@Table(name = "friends")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Friends implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "cell_phone")
    private String cellPhone;

    @Column(name = "last_used_time")
    private ZonedDateTime lastUsedTime;

    @Column(name = "intimacy_order")
    private Long intimacyOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Friends id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Friends name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Friends lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCellPhone() {
        return this.cellPhone;
    }

    public Friends cellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
        return this;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public ZonedDateTime getLastUsedTime() {
        return this.lastUsedTime;
    }

    public Friends lastUsedTime(ZonedDateTime lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
        return this;
    }

    public void setLastUsedTime(ZonedDateTime lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    public Long getIntimacyOrder() {
        return this.intimacyOrder;
    }

    public Friends intimacyOrder(Long intimacyOrder) {
        this.intimacyOrder = intimacyOrder;
        return this;
    }

    public void setIntimacyOrder(Long intimacyOrder) {
        this.intimacyOrder = intimacyOrder;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Friends)) {
            return false;
        }
        return id != null && id.equals(((Friends) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Friends{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", cellPhone='" + getCellPhone() + "'" +
            ", lastUsedTime='" + getLastUsedTime() + "'" +
            ", intimacyOrder=" + getIntimacyOrder() +
            "}";
    }
}
