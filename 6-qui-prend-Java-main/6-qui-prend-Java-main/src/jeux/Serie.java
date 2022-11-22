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
    private static final int maxCartes=5; // nombre maximum de cartes que peut contenir une s�rie, commun � toutes les series et constant
    private int nbCartes; // noombres de cartes de la s�rie
    private final int num; // num�ro de la s�rie

    /**
     * @biref : constrructeur de la S�rie
     * @param p : paquet dont la serie prend la premi�re carte
     * @param i : num�ro de la s�rie
     */
    public Serie(Paquet p, int i) {
        this.num=i;
        this.nbCartes=0;
        serie=new ArrayList<Cartes>(); // initialisation de la serie 
        Cartes premiere = p.PrendreCarte(); //on prend une carte 
        this.ajoutCarte(premiere);//on l'ajoute dans la serie
    }

    /**
     * @biref : ajoute une carte � une s�rie
     * @param c : la carte � ajouter
     */
    public void ajoutCarte(Cartes c) {
        this.serie.add(c);
        this.nbCartes++;
    }

    /**
     * @biref : fait la difference entre la carte c et la derni�re carte de la serie 
     * @param c : la carte � comparer
     * @return : la difference entre la carte � comparer et la derni�re carte de la serie 
     */
    public int PeutJouer(Cartes c) {
        return c.getValeur()-this.serie.get(nbCartes-1).getValeur();
    }

    /**
     * @brief : recup�re les tetes de boeuf que contient la s�rie puis supprime les cartes
     * @return t�teBoeuf : le nombre de t�te de boeuf que contient une s�rie
     */
    public int P�nalit�() {
        int t�teBoeuf=0;
        while(this.serie.isEmpty()==false) {
            t�teBoeuf+=this.serie.get(0).getBoeuf();
            this.serie.remove(0);
            this.nbCartes--;
        }
        return t�teBoeuf;
    }

    /**
     * @return true si la s�rie contient 5 cartes, sinon false
     */
    public boolean MaxCartesSerie() {
        return this.nbCartes == maxCartes;
    }

    /**
     *@brief : affiche une s�rie
     *@return Serie : string repr�sentant la s�rie
     */
    @Override
    public String toString() {
        String Serie="- s�rie n� "+this.num+ " : ";

        for(int i=0;i<nbCartes-1;i++) {
            Serie+=this.serie.get(i)+", "; 
        }
        Serie+=this.serie.get(nbCartes-1); 
        return Serie;
    }

}