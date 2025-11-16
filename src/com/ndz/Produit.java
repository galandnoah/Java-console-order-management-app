package com.ndz;

public class Produit {
    private String id;
    private String name;
    private double price;

    public Produit(String name, double price) {
        this.id = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name+", prix: "+price+ " XAF";
    }
}
