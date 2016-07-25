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
 * A GrainType2.
 */
@Entity
@Table(name = "GRAINTYPE2")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GrainType2 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "wheat")
    private Double wheat;

    @Column(name = "barley")
    private Double barley;
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

    public Double getWheat() {
        return wheat;
    }

    public void setWheat(Double wheat) {
        this.wheat = wheat;
    }

    public Double getBarley() {
        return barley;
    }

    public void setBarley(Double barley) {
        this.barley = barley;
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

        GrainType2 grainType2 = (GrainType2) o;

        if ( ! Objects.equals(id, grainType2.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GrainType2{" +
                "id=" + id +
                ", wheat='" + wheat + "'" +
                ", barley='" + barley + "'" +
                '}';
    }
}
