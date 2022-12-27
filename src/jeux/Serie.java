package jeux;

import java.util.ArrayList;
/**
 * 
 * @file Serie.java
 * Projet 6 qui prend
 * @author Lenouvel Louis et Gabriel Esteves
 * @version 02/03/2022
 *
 */


/**
 * @brief Class Serie 
 *
 */
public class Serie {
    private ArrayList<Cartes> serie; // liste de series 
    private static final int maxCartes=5; // nombre maximum de cartes que peut contenir une série, commun à toutes les series et constant
    private int nbCartes; // noombres de cartes de la série
    private final int num; // numéro de la série

    /**
     * @biref : constrructeur de la Série
     * @param p : paquet dont la serie prend la première carte
     * @param i : numéro de la série
     */
    public Serie(Paquet p, int i) {
        this.num=i;
        this.nbCartes=0;
        serie=new ArrayList<Cartes>(); // initialisation de la serie 
        Cartes premiere = p.PrendreCarte(); //on prend une carte 
        this.ajoutCarte(premiere);//on l'ajoute dans la serie
    }

    /**
     * @biref : ajoute une carte à une série
     * @param c : la carte à ajouter
     */
    public void ajoutCarte(Cartes c) {
        this.serie.add(c);
        this.nbCartes++;
    }

    /**
     * @biref : fait la difference entre la carte c et la dernière carte de la serie 
     * @param c : la carte à comparer
     * @return : la difference entre la carte à comparer et la dernière carte de la serie 
     */
    public int PeutJouer(Cartes c) {
        return c.getValeur()-this.serie.get(nbCartes-1).getValeur();
    }

    /**
     * @brief : recupère les tetes de boeuf que contient la série puis supprime les cartes
     * @return têteBoeuf : le nombre de tête de boeuf que contient une série
     */
    public int Pénalité() {
        int têteBoeuf=0;
        while(this.serie.isEmpty()==false) {
            têteBoeuf+=this.serie.get(0).getBoeuf();
            this.serie.remove(0);
            this.nbCartes--;
        }
        return têteBoeuf;
    }

    /**
     * @return true si la série contient 5 cartes, sinon false
     */
    public boolean MaxCartesSerie() {
        return this.nbCartes == maxCartes;
    }

    /**
     *@brief : affiche une série
     *@return Serie : string représentant la série
     */
    @Override
    public String toString() {
        String Serie="- série n° "+this.num+ " : ";

        for(int i=0;i<nbCartes-1;i++) {
            Serie+=this.serie.get(i)+", "; 
        }
        Serie+=this.serie.get(nbCartes-1); 
        return Serie;
    }

}