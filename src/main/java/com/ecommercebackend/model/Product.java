package com.ecommercebackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "short_description", nullable = false)
    private String shortDescription;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "price", nullable = false)
    private Double price;

    // ####### Popular column updating START
    @Column(name = "popular", nullable = false)
    private  boolean popular = false;

    public boolean isPopular() { return popular; }

    public void setPopular(boolean popular) { this.popular = popular; }

    // ####### Popular column updating END


    @Column(name = "season_novelties", nullable = false)
    boolean seasonNovelties = false;

    public boolean isSeasonNovelties() {
        return seasonNovelties;
    }

    public void setSeasonNovelties(boolean seasonNovelties) {
        this.seasonNovelties = seasonNovelties;
    }

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(mappedBy = "product", cascade =
            CascadeType.REMOVE, optional = false, orphanRemoval = true)
    private Inventory inventory;


    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }



}