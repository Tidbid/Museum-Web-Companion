package com.romanov.rksp.museum.model;

import com.sun.xml.bind.v2.TODO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Showpiece implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length=15000)
    private String description;

    @Column(name="description_long", length=15000)
    private String descriptionLong;

    private String imageUrl;
    //cascade is not OK here mb
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hall_id")
    private Hall hall;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionLong() {
        return descriptionLong;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Hall getHall() {
        return hall;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDescriptionLong(String descriptionLong) {
        this.descriptionLong = descriptionLong;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }
}
