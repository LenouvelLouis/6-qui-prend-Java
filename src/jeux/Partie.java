package jeux;

/**
 * 
 * @file Partie.java
 * Projet 6 qui prend
 * @author Lenouvel Louis et Gabriel Esteves
 * @version 02/03/2022
 *
 */


/**
 * @brief Class Partie 
 *
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;



public class Partie {
	private int nbJoueurs;		//nombre de joueur de la partie
	private static final int nbList=4;		//nombre constant de la série d'une partie 
	private int numseriejoué;		//numéro de la série où l'on veut insérer la carte 
	private Paquet p;		//paquet de la partie
	private ArrayList<Joueur> team;		//liste de joueurs de la partie
	private ArrayList<Serie> listSerie;		//liste de séries de la partie
	private ArrayList<Cartes> listManche;		//liste de cartes choisis par les joueurs pour une manche

	/**
	 * @brief Constructeur de la partie
	 * @param : nom du fichier contenant les noms des joueurs
	 *
	 */

	public Partie(String nom){
		this.nbJoueurs=0;
		this.numseriejoué=0;
		this.p =new Paquet();
		this.team=new ArrayList<Joueur>();		//Initialisation de la liste
		this.listManche=new ArrayList<Cartes>();		//Initialisation de la liste
		listSerie=new ArrayList<Serie>();		//Initialisation de la liste
		for(int i=0;i<nbList;i++) {
			Serie s= new Serie(this.p,i+1);		//Initialisation d'une série avec son numéro et son paquet
			this.listSerie.add(s);		//ajout d'une série à la liste de série
		}

		try {
			Scanner in = new Scanner(new FileInputStream(nom));		//Initialisation du scanner
			while (in.hasNextLine()) {
				Joueur j1= new Joueur(this.p);		//Initialisation d'un joueur
				j1.Setprenom(in.next());		//Initialisation de son prénom
				this.team.add(j1);		//ajout du joueur à la liste
				this.nbJoueurs++;
			}
			if(nbJoueurs<2 || nbJoueurs>10) {//Vérification d'un nombre de joueur valide
				throw new RuntimeException("Le nombre de joueurs doit être compris entre 2 et 10.");
			}
					//fermeture du scanner

		}
		catch (FileNotFoundException e) {		//Vérification que le fichier existe
			System.err.println("Le fichier est introuvable");
			System.exit(0);
		}

	}


	/**
	 * @brief Lancement du jeu 
	 */
	public void sixQuiPrend() {		
		this.afficherjoueur();
		for (int f=0;f<10;f++) {
			this.jouerManche();
			Collections.sort(listManche);
			this.afficherTour();
			this.PlacerCartes();
			this.afficherPointManche();
		}
		this.triFinale();
		this.finPartie();
	}

	/**
	 * @brief : Affichage des joueurs de la partie
	 */
	private void afficherjoueur() {
		System.out.print("Les "+this.nbJoueurs+" joueurs sont ");
		for(int i=0;i <this.nbJoueurs-1;i++) {		//Pour chaque joueur nous affichons son prénom
			if(i==this.nbJoueurs-2)
				System.out.print(this.team.get(i).afficherprenom());
			else
				System.out.print(this.team.get(i).afficherprenom()+", ");
		}
		System.out.println(" et " + this.team.get(nbJoueurs-1).afficherprenom()+ ". Merci de jouer à 6 qui prend !");
	}


	/**
	 * @brief Joue une manche
	 */
	private void jouerManche() {
		for (int i=0;i<this.nbJoueurs;i++) {		//Pour chaque joueur
			this.team.get(i).PointManche(0);		//on initialise ses points de manche à 0
			System.out.println("A "+this.team.get(i).afficherprenom()+" de jouer.");		//Affichage du nom du joueur qui doit jouer
			Console.pause();
			this.afficherSeries();		//Affichage des séries
			System.out.println(this.team.get(i));		//Affichage des cartes du joueur 
			System.out.print("Saisissez votre choix : ");
			this.team.get(i).demandeCarte();		//Nous demandons la carte que le joueur veut jouer
			Console.clearScreen();
			this.listManche.add(this.team.get(i).getCartejoue());		//On ajoute la carte que le joueur à choisi à la liste des cartes jouées pendant la manche
			this.team.get(i).suppCarteMain(this.team.get(i).getCartejoue());		//On retire la carte que le joueur à jouer de sa main
		}

	}

	/**
	 * @brief : Place les cartes choisis par les joueurs en fonction des règles du jeux
	 */
	private void PlacerCartes() {
		for (int i=0;i<this.nbJoueurs;i++) {		//Pour chaque joueur 
			int difference= this.Pluspetit(this.listManche.get(0));		//nous récupérons la plus petite différence entre sa carte joué et la dernière cartes de la série
			if(difference>0 && difference!=104) {		//Vérif règle 2 : Si la différence est positive  	
				if(this.listSerie.get(this.numseriejoué).MaxCartesSerie()==true) 	//Vérif règle 3 : Si la série comporte déjà 5 cartes  	
					this.maxCartesAtteint(i);		//Alors on vide la série et il ramasse les têtes de boeuf 
				this.listSerie.get(this.numseriejoué).ajoutCarte(this.listManche.get(0));		//Alors on place la cartes dans la série jouée
			}
			else {		//Règle 4 : Carte trop faible
				this.demandeSerie();		//Nous demandons au joueur quelle série il veut ramasser
				this.afficherTour();		//Affichage des cartes jouées
				this.listSerie.get(this.numseriejoué).ajoutCarte(this.listManche.get(0));		//On ajoute la carte joué à la série ramassée
			}
			this.listManche.remove(0);		//La carte est placé alors on la supprime des cartes jouées
		}
		Boolean Peutjouer=true;
		this.cartesposees(Peutjouer);		//Nous affchons un message différent en fonction des cartes jouées
	}

	/**
	 * @brief : Choisis la série où la plus petite différence est trouvée
	 * @param c : Cartes qui doit être posée
	 * @return différence : la différence entre la carte qui doit être posée et la dernière cartes de la série 
	 */
	private int Pluspetit(Cartes c) {
		int difference=104;
		for(int i=0;i<nbList;i++) {		//Pour chaque série
			if(difference>this.listSerie.get(i).PeutJouer(c) && this.listSerie.get(i).PeutJouer(c)>0) { //Si une plus petite différence est trouvée 
				difference=this.listSerie.get(i).PeutJouer(c);		//Alors la différence est remplacé
				this.numseriejoué=i;		//On récupère le numéro de la série où la plus petite différence à été trouvé
			}
		}
		return difference;
	}


	/**
	 * @brief : Application de la règle 3 : Si la série comporte 5 cartes et que l'on veut ajouter une carte
	 * @param i : Index du joueur
	 */
	private void maxCartesAtteint(int i) {
		int pénalité=this.listSerie.get(this.numseriejoué).Pénalité();		//On récupère les têtes de boeuf de la série
		this.team.get(i).Point(pénalité);
		this.team.get(i).PointManche(pénalité);
	}


	/**
	 * @brief : Demande quelle série il veut ramasser
	 */
	private void demandeSerie() {
		int IdJoueur = this.recupIdJoueur(this.listManche.get(0));		//Nous récupérons l'index du joueur qui doit ramasser une série
		Boolean peutPoser=false;
		this.cartesposees(peutPoser);		//Nous affchons un message différent en fonction des cartes jouées
		System.out.println("Pour poser la carte "+ this.team.get(IdJoueur).getCartejoue().getValeur() +", " + this.team.get(IdJoueur).afficherprenom() +" doit choisir la série qu'il va ramasser.");
		this.afficherSeries();		//Nous affichons les séries
		System.out.print("Saisissez votre choix : ");
		this.ramasserSerie();		//Le joueur ramasse la série qu'il a jouer
	}


	/**
	 * @brief : Récupération de l'index du joueur en fonction d'une carte
	 * @param c : Carte jouée
	 * @return i : Index du joueur ou -1 si nous ne l'avons pas trouvé
	 */
	private int recupIdJoueur(Cartes c) {
		for(int i=0;i<this.nbJoueurs;i++) {		//Pour chaque joueur
			if(this.team.get(i).getCartejoue().getValeur()==c.getValeur()) {		//Nous vérifions si la carte cherchés correspond à la carte jouée du joueur
				return i;
			}
		}
		return -1;
	}

	/**
	 * @Brief : Ramasse la série que le joueur à demandé
	 */
	private void ramasserSerie () {
		int IdJoueur = this.recupIdJoueur(this.listManche.get(0));		//Nous récupérons l'index du joueur qui doit ramasser une série
		int serie=0;
		try {
			do {
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(System.in);	//Initialisation du scanner
				serie= sc.nextInt();		//On récupère le numéro de la série choisi
				if (serie>0 && serie<=nbList) {		//Si le numéro est valide
					this.numseriejoué = serie-1;		
					int têteBoeuf=this.listSerie.get(this.numseriejoué).Pénalité();		//Nous récupérons les têtes de boeuf de la série choisi 
					this.team.get(IdJoueur).Point(têteBoeuf);
					this.team.get(IdJoueur).PointManche(têteBoeuf);
				}
				else 
					System.out.print("Ce n'est pas une série valide, saisissez votre choix : ");

			}while(serie<0 || serie>nbList);	//Tant que nous avons pas un numéro de série valide nous continuons 
		}
		catch(InputMismatchException e) {

			System.err.print("Ce n'est pas une série valide, saisissez votre choix : ");
			this.ramasserSerie();
		}
		catch(NoSuchElementException e) {
			System.err.print("Ce n'est pas une série valide, saisissez votre choix : ");
			this.ramasserSerie();
		}
		catch(IllegalStateException e) {
			System.err.print("Ce n'est pas une série valide, saisissez votre choix : ");
			this.ramasserSerie();
		}
	}

	/**
	 * @brief : On renvoi le le nombre de joueur de la partie
	 */
	public int getnbJoueurs() {
		return this.nbJoueurs;
	}


	/**
	 * @brief : Affichage des cartes jouées pendant une manche
	 */
	private void afficherTour() {
		int Id;
		System.out.print("Les cartes ");
		for(int i=0; i<this.listManche.size();i++) {		//Pour chaque carte nous affichons sa valeur et  le joueur associé
			Id=this.recupIdJoueur(this.listManche.get(i));		//Nous récupérons l'index du joueur
			if(i<this.listManche.size()-2)
				System.out.print(this.listManche.get(i).getValeur() +" ("+this.team.get(Id).afficherprenom()+"), ");
			else if (i==this.listManche.size()-2)
				System.out.print(this.listManche.get(i).getValeur() +" ("+this.team.get(Id).afficherprenom()+") ");
			else if (i==this.listManche.size()-1)
				System.out.print("et " +this.listManche.get(i).getValeur() +" ("+this.team.get(Id).afficherprenom()+") ");
		}

	} 

	/**
	 * @brief : Affichage des séries de la partie
	 */
	private void afficherSeries() {
		for(int j=0;j<nbList;j++) {		//Pour chaque série nous affichons son numéro et ses cartes associées
			System.out.println(this.listSerie.get(j));
		} 
	}

	/**
	 * @brief : Affiche d'un message en fonction des cartes qui sont jouées
	 * @param peutPoser : etat qui défini si les cartes peuvent être posée
	 */
	private void cartesposees(Boolean peutPoser) {
		if(peutPoser==true)
			System.out.println("ont été posées.");
		else 
			System.out.println("vont être posées.");
	}


	/**
	 * @brief : Affichage des points accordés pendant une manche
	 */
	private void afficherPointManche() {
		this.afficherSeries();		//Nous affichons les séries de la partie
		Boolean aucunJoueurPerdu=true;
		for (int i=0;i<this.nbJoueurs;i++) {		//Pour chaque joueur nous vérifions s'il a ramassé des têtes de boeuf pendant la manche
			if(this.team.get(i).getpointManche()!=0) {		//Si c'est le cas nous affichon son nom et le nombre de tête de boeuf qu'il a ramasé
				aucunJoueurPerdu=false;
				System.out.println(this.team.get(i).afficherprenom() + " a ramassé " + this.team.get(i).getpointManche() + " têtes de boeufs");
			}
		}
		if (aucunJoueurPerdu==true)		//Si aucun joueur n'a ramassé de têtes de boeuf alors nous affichons un message différent
			System.out.println("Aucun joueur ne ramasse de tête de boeufs.");

	}

	/**
	 * @brief :  affichage du résultat de la partie
	 */
	private void finPartie() {
		System.out.println("** Score final");
		for (int i=0;i<this.nbJoueurs;i++) {		//Pour chaque joueur nous affichons son som et ses points
			if(this.team.get(i).getPoint()!=0)
				System.out.println(this.team.get(i).afficherprenom() + " a ramassé " + this.team.get(i).getPoint() + " têtes de boeufs");
			else
				System.out.println(this.team.get(i).afficherprenom() + " a ramassé " + this.team.get(i).getPoint() + " tête de boeufs");
		}
	}

	/**
	 * @brief : Tri de la liste de joueur par le score
	 */
	private void TriScore() {
		for (int i=0;i<this.nbJoueurs;i++) {		//Pour chaque joueur nous vérifions si son score est plus grand que le joueur suivant
			for(int j=i;j<this.nbJoueurs;j++) {
				if(this.team.get(i).getPoint()>this.team.get(j).getPoint()) {		//Si ce n'es pas le cas alors nous les échangeons de place
					this.echangerplace(this.team.get(i), this.team.get(j));
				}
			}
		}
	}

	/**
	 * @brief : Récupérations de la longueur du plus petit nom
	 * @param a : nom que l'on veut comparer
	 * @param b : nom que l'on veut comparer
	 * @return : longueur du plus petit nom
	 */
	private int min(String a,String b) {
		if(a.length()>b.length()) {
			return b.length();
		}
		return a.length();
	}

	/**
	 * @brief : Échange de place entre deux joueurs 
	 * @param j1 : Joueur que l'on veut échanger
	 * @param j2 : Joueur que l'on veut échanger
	 */
	private void echangerplace(Joueur j1,Joueur j2) {
		int pointTmp=0;		//Utilisations de variables temporaires pour le changement 
		String prenomTmp=""; //Utilisations de variables temporaires pour le changement 
		pointTmp=j2.getPoint();		//Nous récupérons les points de j2 	 
		prenomTmp=j2.afficherprenom();		//Nous récupérons le nom de j2
		j2.ajoutPoint(j1.getPoint());		//Nous lui attribuons les points de j1
		j2.Setprenom(j1.afficherprenom());		//Nous lui attribuons le nom de j1
		j1.ajoutPoint(pointTmp); 		//Nous lui attribuons les points de j2
		j1.Setprenom(prenomTmp);		//Nous lui attribuons le nom de j2
	}


	/**
	 * @brief : Tri de la liste de joueur par ordre alphabétique de la liste de joueur
	 */
	private void trialpha() {
		int a,b;	//Utilisations de variables temporaires pour la comparaison
		for (int i=0;i<this.nbJoueurs;i++) { //Pour chaque joueur nous vérifions si nom est bien devant dans l'alpahabet 
			for(int j=i;j<this.nbJoueurs;j++) {
				int lng=min(this.team.get(i).afficherprenom(),this.team.get(j).afficherprenom());	//Nous récupérons la longueur du plus petit nom
				for(int h=0;h<lng;h++) {		//Nous comparons lettre par lettre les deux noms
					a=(int)this.team.get(i).afficherprenom().charAt(h);
					b=(int)this.team.get(j).afficherprenom().charAt(h);
					if(a>b) {		//si une lettre de j1 est plus grande que celle de j2 alors nous échangeons leurs places
						this.echangerplace(this.team.get(i), this.team.get(j));
					}
				}
			}
		}

	}

	/**
	 * @brief : Tri finale de la liste de joueurs
	 */
	private void triFinale() {
		this.trialpha();		//Nous trions d'abord par ordre alphabétique
		this.TriScore();		//Puis nous trions par ordre de score
	}
}


