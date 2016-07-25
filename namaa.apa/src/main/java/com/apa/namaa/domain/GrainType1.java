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
 * A GrainType1.
 */
@Entity
@Table(name = "GRAINTYPE1")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GrainType1 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "spelt")
    private Double spelt;

    @Column(name = "corn")
    private Double corn;

    @Column(name = "millet")
    private Double millet;
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

    public Double getSpelt() {
        return spelt;
    }

    public void setSpelt(Double spelt) {
        this.spelt = spelt;
    }

    public Double getCorn() {
        return corn;
    }

    public void setCorn(Double corn) {
        this.corn = corn;
    }

    public Double getMillet() {
        return millet;
    }

    public void setMillet(Double millet) {
        this.millet = millet;
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

        GrainType1 grainType1 = (GrainType1) o;

        if ( ! Objects.equals(id, grainType1.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GrainType1{" +
                "id=" + id +
                ", spelt='" + spelt + "'" +
                ", corn='" + corn + "'" +
                ", millet='" + millet + "'" +
                '}';
    }
}
