package com.ndz;

import java.util.UUID;

public class LigneCommande {
    private String idCommande;
    private String idProduit;
    private int qty;
    private String idLigneCommande;

    public LigneCommande(String idCommande, String idProduit, int qty) {
        this.idCommande = idCommande;
        this.idProduit = idProduit;
        this.qty = qty;
        this.idLigneCommande = UUID.randomUUID().toString();
    }

    public String getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(String idCommande) {
        this.idCommande = idCommande;
    }

    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
