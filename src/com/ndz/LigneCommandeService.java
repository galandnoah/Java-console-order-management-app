package com.ndz;

import java.util.ArrayList;
import java.util.List;

public class LigneCommandeService {
    public static List<LigneCommande> tableLigneCommande = new ArrayList<>();
    public static void ajouterUneLigneDeCommande(LigneCommande ligneCommande)
    {
        tableLigneCommande.add(ligneCommande);
    }
}
