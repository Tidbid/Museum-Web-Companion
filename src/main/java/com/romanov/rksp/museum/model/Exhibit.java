package com.romanov.rksp.museum.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Exhibit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate dateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFinish;

    @Column(length=1000)
    private String description;

    @Column(name="description_long", length=15000)
    private String descriptionLong;

    private String imageUrl;

    @OneToMany(mappedBy = "exhibit", cascade = CascadeType.ALL)
    private Collection<Hall> halls;

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

    public Collection<Hall> getHalls() {
        return halls;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public LocalDate getDateFinish() {
        return dateFinish;
    }

    public String getImageUrl() {
        return imageUrl;
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

    public void setHalls(Collection<Hall> halls) {
        this.halls = halls;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateFinish(LocalDate dateFinish) {
        this.dateFinish = dateFinish;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
