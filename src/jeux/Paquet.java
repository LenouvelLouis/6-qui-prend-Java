package jeux;
import java.util.*;
/**
 * 
 * @file Paquet.java
 * Projet 6 qui prend
 * @author Lenouvel Louis et Gabriel Esteves
 * @version 02/03/2022
 *
 */


/**
 * @brief Class Paquet
 *
 */

public class Paquet {
    private ArrayList<Cartes> paquet; // liste de cartes
    private int nbCartes;	//nombres de cartes du paquet

    /**
     * brief : constructeur du paquet
     */
    public Paquet() {
    	this.nbCartes=104;
        paquet=new ArrayList<Cartes>();
        for(int i =0;i<this.nbCartes;i++) {
            Cartes a=new Cartes(i); // initialistation d'une carte avec son numero
            this.paquet.add(a); // ajout de la carte dans le paquet
        }
        Collections.shuffle(this.paquet); //mélange du paquet
    }

    /**
     * @brief : prend une carte du paquet
     * @return Carte : première carte du paquet
     */
    public Cartes PrendreCarte() throws IndexOutOfBoundsException {
    	if(this.paquet.isEmpty()==true)
    		throw new IndexOutOfBoundsException();
        Cartes carte=this.paquet.get(0);
        this.paquet.remove(0);
        this.nbCartes--; // on décrémente le nombre de cartes du paquet
        return carte;
    }



}