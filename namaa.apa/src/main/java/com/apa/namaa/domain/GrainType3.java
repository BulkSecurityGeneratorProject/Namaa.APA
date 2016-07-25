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
 * A GrainType3.
 */
@Entity
@Table(name = "GRAINTYPE3")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GrainType3 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "chickpeas")
    private Double chickpeas;

    @Column(name = "beans")
    private Double beans;

    @Column(name = "cowpea")
    private Double cowpea;

    @Column(name = "lathyrus")
    private Double lathyrus;
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

    public Double getChickpeas() {
        return chickpeas;
    }

    public void setChickpeas(Double chickpeas) {
        this.chickpeas = chickpeas;
    }

    public Double getBeans() {
        return beans;
    }

    public void setBeans(Double beans) {
        this.beans = beans;
    }

    public Double getCowpea() {
        return cowpea;
    }

    public void setCowpea(Double cowpea) {
        this.cowpea = cowpea;
    }

    public Double getLathyrus() {
        return lathyrus;
    }

    public void setLathyrus(Double lathyrus) {
        this.lathyrus = lathyrus;
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

        GrainType3 grainType3 = (GrainType3) o;

        if ( ! Objects.equals(id, grainType3.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GrainType3{" +
                "id=" + id +
                ", chickpeas='" + chickpeas + "'" +
                ", beans='" + beans + "'" +
                ", cowpea='" + cowpea + "'" +
                ", lathyrus='" + lathyrus + "'" +
                '}';
    }
}
