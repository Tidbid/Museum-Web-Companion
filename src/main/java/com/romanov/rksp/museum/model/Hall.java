package com.romanov.rksp.museum.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Hall implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer number;

    private String name;

    @Column(nullable = false, length=15000)
    private String description;

    @Column(name="description_long", length=15000)
    private String descriptionLong;

    @OneToMany(mappedBy = "hall")
    private Collection<Showpiece> showpieces;

    //cascade ALL may result in bugs: deleting hall
    // from exhibit may delete the entire exhibit
    @ManyToOne()
    @JoinColumn(name = "exhibit_id")
    private Exhibit exhibit;

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDescriptionLong(String descriptionLong) {
        this.descriptionLong = descriptionLong;
    }

    public void setShowpieces(Collection<Showpiece> showpieces) {
        this.showpieces = showpieces;
    }

    public void setExhibit(Exhibit exhibit) {
        this.exhibit = exhibit;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getNumber() {
        return number;
    }

    public Long getId() {
        return id;
    }

    public Collection<Showpiece> getShowpieces() {
        return showpieces;
    }

    public String getDescriptionLong() {
        return descriptionLong;
    }

    public Exhibit getExhibit() {
        return exhibit;
    }
}
