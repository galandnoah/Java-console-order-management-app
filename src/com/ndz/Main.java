package com.ndz;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Les produits et leurs prix");
        Stock.stock.forEach(produit -> System.out.println(produit));

        System.out.print("Tapez 1 pour passer une commande ou 0 pour sortir : ");
        int choix = lireEntier(scanner, 0, 1);

        if (choix == 1) {

            // ─── CLIENT ───────────────────────────────────────────────
            System.out.print("Votre nom : ");
            String name = scanner.nextLine();
            Client client = new Client(name);

            // ─── COMMANDE ─────────────────────────────────────────────
            Commande commande = new Commande(client.getId());

            AtomicInteger i = new AtomicInteger(0);
            Stock.stock.forEach(
                    produit -> System.out.println(i.getAndIncrement() + " : " + produit.getName())
            );

            System.out.println("Pour ajouter un produit entrer le numero devant le produit puis la quantité.");

            boolean continuer = true;

            while (continuer) {

                // ─── CHOIX PRODUIT ─────────────────────────────────────
                System.out.print("Produit: ");
                int numero = lireEntier(scanner, 0, Stock.stock.size() - 1);

                Produit produit = Stock.stock.get(numero);

                // ─── QUANTITE─────────────────────────────────────────
                System.out.print("Quantité : ");
                int quantite = lireEntierMinimum(scanner, 1);

                System.out.println(quantite + " " + produit.getName() + "(s)");

                LigneCommande ligneCommande =
                        new LigneCommande(commande.getId(), produit.getId(), quantite);
                LigneCommandeService.ajouterUneLigneDeCommande(ligneCommande);

                // ─── AJOUTER UN AUTRE PRODUIT ? ───────────────────────
                System.out.print("Ajouter un autre produit ? (0 = non 1 = oui) : ");
                int choixAjout = lireEntier(scanner, 0, 1);

                if (choixAjout == 0) {
                    continuer = false;
                }
            }

            // ─── AFFICHAGE DE LA COMMANDE ─────────────────────────────

            System.out.println("\nVotre commande :");

            Function<LigneCommande, Map<String, Integer>> ligneCommandeMapper = lc -> {
                Produit produitTrouve = Stock.stock.stream()
                        .filter(p -> p.getId().equals(lc.getIdProduit()))
                        .findFirst()
                        .orElse(null);

                Map<String, Integer> map = new HashMap<>();
                map.put(produitTrouve.getName(), lc.getQty());
                return map;
            };

            List<Map<String, Integer>> listCommande =
                    LigneCommandeService.tableLigneCommande.stream()
                            .map(ligneCommandeMapper)
                            .toList();

            // Affichage
            listCommande.forEach(map -> {
                map.forEach((nom, qte) ->
                        System.out.println(qte + " " + nom + "(s)"));
            });

            // Total
            double total = LigneCommandeService.tableLigneCommande.stream()
                    .mapToDouble(lc -> {
                        Produit p = Stock.stock.stream()
                                .filter(prod -> prod.getId().equals(lc.getIdProduit()))
                                .findFirst()
                                .get();
                        return p.getPrice() * lc.getQty();
                    })
                    .sum();

            System.out.println("Prix total : " + total + " XAF");
        } else {
            System.out.println("Au revoir");
        }
    }


    // ─────────────────────────────────────────────────────────────
    //         UTILITAIRES DE LECTURE 
    // ─────────────────────────────────────────────────────────────

    public static int lireEntier(Scanner sc, int min, int max) {
        while (true) {
            if (sc.hasNextInt()) {
                int n = sc.nextInt();
                sc.nextLine();
                if (n >= min && n <= max) {
                    return n;
                }
                System.out.println("La valeur doit être comprise entre " + min + " et " + max);
            } else {
                System.out.println("Veuillez entrer un entier valide.");
                sc.next();
            }
        }
    }

    public static int lireEntierMinimum(Scanner sc, int min) {
        while (true) {
            if (sc.hasNextInt()) {
                int n = sc.nextInt();
                sc.nextLine();
                if (n >= min) {
                    return n;
                }
                System.out.println("La valeur doit être superieur ou egale " + min);
            } else {
                System.out.println("Veuillez entrer un entier valide.");
                sc.next();
            }
        }
    }
}
