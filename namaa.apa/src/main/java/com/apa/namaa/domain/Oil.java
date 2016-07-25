package com.apa.namaa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Oil.
 */
@Entity
@Table(name = "OIL")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Oil implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "sesame")
    private Double sesame;

    @Column(name = "safflower")
    private Double safflower;

    @Column(name = "radish")
    private Double radish;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "dateAdded", nullable = true)
    private DateTime dateAdded = null;

    @Column(name = "watered")
    private Double watered;

    @Column(name = "nonWatered")
    private Double nonWatered;

    public DateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded() {
        this.dateAdded = DateTime.now();
    }

    public Double getWatered() {
        return watered;
    }

    public void setWatered(Double watered) {
        this.watered = watered;
    }

    public Double getNonWatered() {
        return nonWatered;
    }

    public void setNonWatered(Double nonWatered) {
        this.nonWatered = nonWatered;
    }
    @ManyToOne
    private User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSesame() {
        return sesame;
    }

    public void setSesame(Double sesame) {
        this.sesame = sesame;
    }

    public Double getSafflower() {
        return safflower;
    }

    public void setSafflower(Double safflower) {
        this.safflower = safflower;
    }

    public Double getRadish() {
        return radish;
    }

    public void setRadish(Double radish) {
        this.radish = radish;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Oil oil = (Oil) o;

        if ( ! Objects.equals(id, oil.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Oil{" +
                "id=" + id +
                ", sesame='" + sesame + "'" +
                ", safflower='" + safflower + "'" +
                ", radish='" + radish + "'" +
                '}';
    }
}
