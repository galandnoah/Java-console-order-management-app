package com.ndz;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Commande {
    private String id;
    private String idClient;
    List<Map<String, Integer>> listeDesProduits;

    public Commande(String idClient) {
        this.id = UUID.randomUUID().toString();
        this.idClient = idClient;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public List<Map<String, Integer>> getListeDesProduits() {
        return listeDesProduits;
    }

    public void setListeDesProduits(List<Map<String, Integer>> listeDesProduits) {
        this.listeDesProduits = listeDesProduits;
    }
}
