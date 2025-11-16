package com.ndz;

import java.util.List;
import java.util.Objects;

public class Stock {
    public static List<Produit> stock = List.of(
            new Produit("Campagne", 80),
            new Produit("Kirikou", 75),
            new Produit("Baguette", 105)
    );

    public static Produit findById(String id)
    {
        Produit produit = null;
        for (Produit produit1 : stock) {
            if (produit1.getId().equals(id))
            {
                produit = produit1;
            }
        }
        return produit;
    }
}
