package jeux;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * 
 * @file Joueur.java
 * Projet 6 qui prend
 * @author Lenouvel Louis et Gabriel Esteves
 * @version 02/03/2022
 *
 */


/**
 * @brief Class Joueur
 *
 */
/**
 * @author mrest
 *
 */
public class Joueur  {
	private int point; // point du joueur
	private int pointManche; // point par manche du joueur
	private ArrayList<Cartes> main; // liste de cartes du joueur
	private String prenom; // prenom du joueur
	private int nbCartes; // nombre de cartes initiale du joueur
	private Cartes Cartejoue; // carte joué du joueur à chaque manche

	public Joueur(Paquet p) {
		this.nbCartes=10;
		this.point=0; 
		this.pointManche=0;
		main=new ArrayList<Cartes>(); // initialisation de la main du joueur
		for(int i =0;i<this.nbCartes;i++) { 
			Cartes c = p.PrendreCarte() ; // carte prise du paquet p
			this.main.add(c); // carte ajouté à la main du joueur
		}
		Collections.sort(this.main); // tri des cartes du joueur du plus petit au plus grand
	}


	/**
	 * @brief : setter du prenom du joueur et qui le normalise
	 * @param next : le prenom
	 */
	public void Setprenom(String next) {
		this.prenom=next;
		this.normaliser();

	}

	/**
	 * @brief : affiche le prenom du joueur
	 * @return String : le prenom du joueur
	 */
	public String afficherprenom() {
		return this.prenom;
	}

	/**
	 * @brief : demande au joueur de choisir une carte
	 */
	public void demandeCarte() {

		try {

			do {
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(System.in); //initialisation du scanner
				int carte= sc.nextInt();  // on stock la valeur rentré par l'utilisateur
				this.Cartejoue=new Cartes(carte-1);  // on stock la carte joué avec la carte rentré
				if (this.chercherCarte(this.Cartejoue)==false) // si la carte n'est pas dans la main du joueur
					System.out.print("Vous n'avez pas cette carte, saisissez votre choix : "); // on lui redemande de saisir une carte
			}while(this.chercherCarte(this.Cartejoue)==false); // on repète tant que la carte n'est pas contenu dans sa liste de cartes

		}
		catch(InputMismatchException e) { 
			System.err.print("Vous n'avez pas cette carte, saisissez votre choix : "); 
			this.demandeCarte();
		}
		catch(NoSuchElementException e) {
			System.err.print("Vous n'avez pas cette carte, saisissez votre choix : ");
			this.demandeCarte();
		}
		catch(IllegalStateException e) {
			System.err.print("Vous n'avez pas cette carte, saisissez votre choix : ");
			this.demandeCarte();
		}

	}

	/**
	 * @brief : supprime la carte de la main du joueur
	 * @param c : la carte que l'on veut supprimer
	 */
	public void suppCarteMain (Cartes c) {
		for(int i=0;i<this.nbCartes;i++) {
			if(this.main.get(i).getValeur()==c.getValeur()) { // si la valeur rechercher est contenu dans la main
				this.main.remove(i); // alors on la supprime de la main
				this.nbCartes--; 
			}

		}
	}


	/**
	 * @brief : recherche d'une carte dans la liste de cartes du joueur
	 * @param c : la carte à chercher
	 * @return true si la carte est contenu dans la main du joueur, false sinon
	 */
	public boolean chercherCarte(Cartes c) {
		for(int i=0;i<this.nbCartes;i++) {
			if(this.main.get(i).getValeur()==c.getValeur()) { // si la valeur rechercher est contenu dans la main
				return true;
			}
		}
		return false;
	}



	/**
	 * @brief : getter de la carte joué du joueur
	 * @return Cartejoue : la carte que le joueur à joué
	 */
	public Cartes getCartejoue() {
		return Cartejoue;
	}



	/**
	 * @brief : getter des points du joueur de la partie
	 * @return point : les points du joueur de la partie
	 */
	public int getPoint() {
		return this.point;
	}

	/**
	 * @brief : getter des points du joueur de la manche
	 * @return pointManche : les points du joueur de la manche
	 */
	public int getpointManche() {
		return this.pointManche;
	}


	/**
	 * @brief : setter des points par manche du joueur
	 * @param jPointManche  : point de la manche du joueur
	 */
	public void PointManche(int jPointManche) {
		this.pointManche=jPointManche;

	}

	/**
	 * @brief : setter des points de la partie pour le joueur
	 * @param têteBoeuf : point à ajouter à la partie en cours pour le joueur
	 */
	public void Point(int têteBoeuf) {
		this.point+=têteBoeuf;

	}


	/**
	 * @brief : réinitialise les points du joueur afin de faire le tri
	 * @param têteBoeuf : point du joueur
	 */
	public void ajoutPoint(int têteBoeuf) {
		this.point=têteBoeuf;
	}
	/**
	 * @brief : normalise le prenom du joueur
	 */
	public void normaliser() {
		String firstpre;
		this.prenom=this.prenom.trim(); // on supprime les espaces du prenom
		firstpre=this.prenom.substring(1); // on supprime la première lettre du prenom
		this.prenom=this.prenom.toUpperCase(); // on met le prenom en majuscule
		firstpre=firstpre.toLowerCase(); // on met les caractères en miniscule 
		this.prenom=this.prenom.charAt(0)+firstpre; // on garde le premier caractère du prenom en majuscule et on ajoute le reste qui est en miniscule
	}

	/**
	 *@brief : affiche pour un joueur sa liste de carte joué
	 */
	@Override
	public String toString() {
		String mainJoueur="- Vos cartes : ";
		for(int i=0;i<this.nbCartes-1;i++) {
			mainJoueur+= this.main.get(i)+", ";

		}
		mainJoueur+=this.main.get(nbCartes-1);
		return mainJoueur;
	}
}



