package com.ndz;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner  = new Scanner(System.in);
        System.out.println("Les produits et leurs prix");
        Stock.stock.forEach(produit -> System.out.println(produit.toString()));
        System.out.print("Taper 1 pour passer une commande ou 0 pour sortir: ");
        int choix = scanner.nextInt();
        scanner.nextLine();
        if(choix==1)
        {

            //Creation du client
            System.out.print("Votre nom: ");
            String name =scanner.nextLine();
            Client client = new Client(name);
            //Creation de la commande
            Commande commande = new Commande(client.getId());
            //Enregistrement de la commande
            AtomicInteger i= new AtomicInteger(0);
            Stock.stock.forEach(
                   produit -> {
                       System.out.println(i.get()+" : "+produit.getName());
                       i.getAndIncrement();
                   }
            );
            System.out.println("Pour ajouter un produit a votre commande, saisir le chiffre devant le produit ensuite la quantite");
            //boucle
            while (true)
            {

                int numero = 0;
                System.out.print("Produit: ");
                Produit produit = null;
                try
                {
                    numero = scanner.nextInt();
                    scanner.nextLine();
                    if (numero >= Stock.stock.size() || numero <0)
                    {
                        throw new Exception("Vous n'avez pas choisi un numero valide...");

                    }
                    else
                    {
                       produit = Stock.stock.get(numero);
                    }

                }catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }


               if (produit!=null)
               {
                   int quantite = 1;
                   System.out.print("La quantite: ");
                   try
                   {
                       quantite = scanner.nextInt();
                       if(quantite <=0)
                       {
                           throw new Exception("La quantite doit etre superieur a zero");
                       }


                   }catch (Exception e)
                   {
                       System.out.println(e.getMessage());
                   }
                   System.out.println(quantite+" "+produit.getName()+"s");
                   //Creation d'une ligne de commande
                   LigneCommande ligneCommande = new LigneCommande(commande.getId(), produit.getId(), quantite );
                   LigneCommandeService.ajouterUneLigneDeCommande(ligneCommande);
               }

                System.out.print("Voulez ajouter un autre produit ? (0 pour non 1 pour oui): ");
                try{
                    int choixAjout = scanner.nextInt();
                    scanner.nextLine();
                    if(choixAjout != 1 && choixAjout !=0)
                    {
                        throw new Exception("Vous n'avez pas entre 1 ou 0");
                    }
                    if(choixAjout==0)
                    {
                        break;
                    }
                }catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }

            }
            System.out.println("Votre commande: ");
            //Creation d'un mapper
            Function<LigneCommande, Map<String, Integer>> ligneCommandeMapper = (ligneCommande -> {
                String idProduit = ligneCommande.getIdProduit();
                Produit produitTrouve = Stock.stock.stream().filter(produit -> produit.getId().equals(idProduit)).toList().get(0);
                int qte = ligneCommande.getQty();
                Map<String, Integer> ourMap = new HashMap<>();
                ourMap.put(produitTrouve.getName(), qte);
                return ourMap;
            });
            //Application du mapper
            List<Map<String, Integer>> listCommande = LigneCommandeService.tableLigneCommande.stream().map(ligneCommandeMapper).toList();
            for (Map<String, Integer> commandes : listCommande) {
                commandes.keySet().forEach(key -> System.out.println(commandes.get(key)+" "+key+"s"));
            }
            //Set la liste des produits dans commande
            commande.setListeDesProduits(listCommande);
            //Calcul du cout total de la commande
            double coutCommande = LigneCommandeService.tableLigneCommande.stream().map( ligneCommande -> {
                String idProduit = ligneCommande.getIdProduit();
                Produit produitTrouve = Stock.stock.stream().filter(produit -> produit.getId().equals(idProduit)).toList().get(0);
                int qte = ligneCommande.getQty();
                return produitTrouve.getPrice()*qte;
                    }
            ).mapToDouble(Double::doubleValue).sum();
            System.out.println("Prix total: "+coutCommande+" XAF");

        }
        else
        {
            System.out.println("Aurevoir");
        }
    }
}
