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
 * A Fruit.
 */
@Entity
@Table(name = "FRUIT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fruit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "grapes")
    private Double grapes;

    @Column(name = "dates")
    private Double dates;

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

    public Double getGrapes() {
        return grapes;
    }

    public void setGrapes(Double grapes) {
        this.grapes = grapes;
    }

    public Double getDates() {
        return dates;
    }

    public void setDates(Double dates) {
        this.dates = dates;
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

        Fruit fruit = (Fruit) o;

        if ( ! Objects.equals(id, fruit.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "id=" + id +
                ", grapes='" + grapes + "'" +
                ", dates='" + dates + "'" +
                '}';
    }
}
