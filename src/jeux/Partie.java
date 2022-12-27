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
	private static final int nbList=4;		//nombre constant de la s�rie d'une partie 
	private int numseriejou�;		//num�ro de la s�rie o� l'on veut ins�rer la carte 
	private Paquet p;		//paquet de la partie
	private ArrayList<Joueur> team;		//liste de joueurs de la partie
	private ArrayList<Serie> listSerie;		//liste de s�ries de la partie
	private ArrayList<Cartes> listManche;		//liste de cartes choisis par les joueurs pour une manche

	/**
	 * @brief Constructeur de la partie
	 * @param : nom du fichier contenant les noms des joueurs
	 *
	 */

	public Partie(String nom){
		this.nbJoueurs=0;
		this.numseriejou�=0;
		this.p =new Paquet();
		this.team=new ArrayList<Joueur>();		//Initialisation de la liste
		this.listManche=new ArrayList<Cartes>();		//Initialisation de la liste
		listSerie=new ArrayList<Serie>();		//Initialisation de la liste
		for(int i=0;i<nbList;i++) {
			Serie s= new Serie(this.p,i+1);		//Initialisation d'une s�rie avec son num�ro et son paquet
			this.listSerie.add(s);		//ajout d'une s�rie � la liste de s�rie
		}

		try {
			Scanner in = new Scanner(new FileInputStream(nom));		//Initialisation du scanner
			while (in.hasNextLine()) {
				Joueur j1= new Joueur(this.p);		//Initialisation d'un joueur
				j1.Setprenom(in.next());		//Initialisation de son pr�nom
				this.team.add(j1);		//ajout du joueur � la liste
				this.nbJoueurs++;
			}
			if(nbJoueurs<2 || nbJoueurs>10) {//V�rification d'un nombre de joueur valide
				throw new RuntimeException("Le nombre de joueurs doit �tre compris entre 2 et 10.");
			}
					//fermeture du scanner

		}
		catch (FileNotFoundException e) {		//V�rification que le fichier existe
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
		for(int i=0;i <this.nbJoueurs-1;i++) {		//Pour chaque joueur nous affichons son pr�nom
			if(i==this.nbJoueurs-2)
				System.out.print(this.team.get(i).afficherprenom());
			else
				System.out.print(this.team.get(i).afficherprenom()+", ");
		}
		System.out.println(" et " + this.team.get(nbJoueurs-1).afficherprenom()+ ". Merci de jouer � 6 qui prend !");
	}


	/**
	 * @brief Joue une manche
	 */
	private void jouerManche() {
		for (int i=0;i<this.nbJoueurs;i++) {		//Pour chaque joueur
			this.team.get(i).PointManche(0);		//on initialise ses points de manche � 0
			System.out.println("A "+this.team.get(i).afficherprenom()+" de jouer.");		//Affichage du nom du joueur qui doit jouer
			Console.pause();
			this.afficherSeries();		//Affichage des s�ries
			System.out.println(this.team.get(i));		//Affichage des cartes du joueur 
			System.out.print("Saisissez votre choix : ");
			this.team.get(i).demandeCarte();		//Nous demandons la carte que le joueur veut jouer
			Console.clearScreen();
			this.listManche.add(this.team.get(i).getCartejoue());		//On ajoute la carte que le joueur � choisi � la liste des cartes jou�es pendant la manche
			this.team.get(i).suppCarteMain(this.team.get(i).getCartejoue());		//On retire la carte que le joueur � jouer de sa main
		}

	}

	/**
	 * @brief : Place les cartes choisis par les joueurs en fonction des r�gles du jeux
	 */
	private void PlacerCartes() {
		for (int i=0;i<this.nbJoueurs;i++) {		//Pour chaque joueur 
			int difference= this.Pluspetit(this.listManche.get(0));		//nous r�cup�rons la plus petite diff�rence entre sa carte jou� et la derni�re cartes de la s�rie
			if(difference>0 && difference!=104) {		//V�rif r�gle 2 : Si la diff�rence est positive  	
				if(this.listSerie.get(this.numseriejou�).MaxCartesSerie()==true) 	//V�rif r�gle 3 : Si la s�rie comporte d�j� 5 cartes  	
					this.maxCartesAtteint(i);		//Alors on vide la s�rie et il ramasse les t�tes de boeuf 
				this.listSerie.get(this.numseriejou�).ajoutCarte(this.listManche.get(0));		//Alors on place la cartes dans la s�rie jou�e
			}
			else {		//R�gle 4 : Carte trop faible
				this.demandeSerie();		//Nous demandons au joueur quelle s�rie il veut ramasser
				this.afficherTour();		//Affichage des cartes jou�es
				this.listSerie.get(this.numseriejou�).ajoutCarte(this.listManche.get(0));		//On ajoute la carte jou� � la s�rie ramass�e
			}
			this.listManche.remove(0);		//La carte est plac� alors on la supprime des cartes jou�es
		}
		Boolean Peutjouer=true;
		this.cartesposees(Peutjouer);		//Nous affchons un message diff�rent en fonction des cartes jou�es
	}

	/**
	 * @brief : Choisis la s�rie o� la plus petite diff�rence est trouv�e
	 * @param c : Cartes qui doit �tre pos�e
	 * @return diff�rence : la diff�rence entre la carte qui doit �tre pos�e et la derni�re cartes de la s�rie 
	 */
	private int Pluspetit(Cartes c) {
		int difference=104;
		for(int i=0;i<nbList;i++) {		//Pour chaque s�rie
			if(difference>this.listSerie.get(i).PeutJouer(c) && this.listSerie.get(i).PeutJouer(c)>0) { //Si une plus petite diff�rence est trouv�e 
				difference=this.listSerie.get(i).PeutJouer(c);		//Alors la diff�rence est remplac�
				this.numseriejou�=i;		//On r�cup�re le num�ro de la s�rie o� la plus petite diff�rence � �t� trouv�
			}
		}
		return difference;
	}


	/**
	 * @brief : Application de la r�gle 3 : Si la s�rie comporte 5 cartes et que l'on veut ajouter une carte
	 * @param i : Index du joueur
	 */
	private void maxCartesAtteint(int i) {
		int p�nalit�=this.listSerie.get(this.numseriejou�).P�nalit�();		//On r�cup�re les t�tes de boeuf de la s�rie
		this.team.get(i).Point(p�nalit�);
		this.team.get(i).PointManche(p�nalit�);
	}


	/**
	 * @brief : Demande quelle s�rie il veut ramasser
	 */
	private void demandeSerie() {
		int IdJoueur = this.recupIdJoueur(this.listManche.get(0));		//Nous r�cup�rons l'index du joueur qui doit ramasser une s�rie
		Boolean peutPoser=false;
		this.cartesposees(peutPoser);		//Nous affchons un message diff�rent en fonction des cartes jou�es
		System.out.println("Pour poser la carte "+ this.team.get(IdJoueur).getCartejoue().getValeur() +", " + this.team.get(IdJoueur).afficherprenom() +" doit choisir la s�rie qu'il va ramasser.");
		this.afficherSeries();		//Nous affichons les s�ries
		System.out.print("Saisissez votre choix : ");
		this.ramasserSerie();		//Le joueur ramasse la s�rie qu'il a jouer
	}


	/**
	 * @brief : R�cup�ration de l'index du joueur en fonction d'une carte
	 * @param c : Carte jou�e
	 * @return i : Index du joueur ou -1 si nous ne l'avons pas trouv�
	 */
	private int recupIdJoueur(Cartes c) {
		for(int i=0;i<this.nbJoueurs;i++) {		//Pour chaque joueur
			if(this.team.get(i).getCartejoue().getValeur()==c.getValeur()) {		//Nous v�rifions si la carte cherch�s correspond � la carte jou�e du joueur
				return i;
			}
		}
		return -1;
	}

	/**
	 * @Brief : Ramasse la s�rie que le joueur � demand�
	 */
	private void ramasserSerie () {
		int IdJoueur = this.recupIdJoueur(this.listManche.get(0));		//Nous r�cup�rons l'index du joueur qui doit ramasser une s�rie
		int serie=0;
		try {
			do {
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(System.in);	//Initialisation du scanner
				serie= sc.nextInt();		//On r�cup�re le num�ro de la s�rie choisi
				if (serie>0 && serie<=nbList) {		//Si le num�ro est valide
					this.numseriejou� = serie-1;		
					int t�teBoeuf=this.listSerie.get(this.numseriejou�).P�nalit�();		//Nous r�cup�rons les t�tes de boeuf de la s�rie choisi 
					this.team.get(IdJoueur).Point(t�teBoeuf);
					this.team.get(IdJoueur).PointManche(t�teBoeuf);
				}
				else 
					System.out.print("Ce n'est pas une s�rie valide, saisissez votre choix : ");

			}while(serie<0 || serie>nbList);	//Tant que nous avons pas un num�ro de s�rie valide nous continuons 
		}
		catch(InputMismatchException e) {

			System.err.print("Ce n'est pas une s�rie valide, saisissez votre choix : ");
			this.ramasserSerie();
		}
		catch(NoSuchElementException e) {
			System.err.print("Ce n'est pas une s�rie valide, saisissez votre choix : ");
			this.ramasserSerie();
		}
		catch(IllegalStateException e) {
			System.err.print("Ce n'est pas une s�rie valide, saisissez votre choix : ");
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
	 * @brief : Affichage des cartes jou�es pendant une manche
	 */
	private void afficherTour() {
		int Id;
		System.out.print("Les cartes ");
		for(int i=0; i<this.listManche.size();i++) {		//Pour chaque carte nous affichons sa valeur et  le joueur associ�
			Id=this.recupIdJoueur(this.listManche.get(i));		//Nous r�cup�rons l'index du joueur
			if(i<this.listManche.size()-2)
				System.out.print(this.listManche.get(i).getValeur() +" ("+this.team.get(Id).afficherprenom()+"), ");
			else if (i==this.listManche.size()-2)
				System.out.print(this.listManche.get(i).getValeur() +" ("+this.team.get(Id).afficherprenom()+") ");
			else if (i==this.listManche.size()-1)
				System.out.print("et " +this.listManche.get(i).getValeur() +" ("+this.team.get(Id).afficherprenom()+") ");
		}

	} 

	/**
	 * @brief : Affichage des s�ries de la partie
	 */
	private void afficherSeries() {
		for(int j=0;j<nbList;j++) {		//Pour chaque s�rie nous affichons son num�ro et ses cartes associ�es
			System.out.println(this.listSerie.get(j));
		} 
	}

	/**
	 * @brief : Affiche d'un message en fonction des cartes qui sont jou�es
	 * @param peutPoser : etat qui d�fini si les cartes peuvent �tre pos�e
	 */
	private void cartesposees(Boolean peutPoser) {
		if(peutPoser==true)
			System.out.println("ont �t� pos�es.");
		else 
			System.out.println("vont �tre pos�es.");
	}


	/**
	 * @brief : Affichage des points accord�s pendant une manche
	 */
	private void afficherPointManche() {
		this.afficherSeries();		//Nous affichons les s�ries de la partie
		Boolean aucunJoueurPerdu=true;
		for (int i=0;i<this.nbJoueurs;i++) {		//Pour chaque joueur nous v�rifions s'il a ramass� des t�tes de boeuf pendant la manche
			if(this.team.get(i).getpointManche()!=0) {		//Si c'est le cas nous affichon son nom et le nombre de t�te de boeuf qu'il a ramas�
				aucunJoueurPerdu=false;
				System.out.println(this.team.get(i).afficherprenom() + " a ramass� " + this.team.get(i).getpointManche() + " t�tes de boeufs");
			}
		}
		if (aucunJoueurPerdu==true)		//Si aucun joueur n'a ramass� de t�tes de boeuf alors nous affichons un message diff�rent
			System.out.println("Aucun joueur ne ramasse de t�te de boeufs.");

	}

	/**
	 * @brief :  affichage du r�sultat de la partie
	 */
	private void finPartie() {
		System.out.println("** Score final");
		for (int i=0;i<this.nbJoueurs;i++) {		//Pour chaque joueur nous affichons son som et ses points
			if(this.team.get(i).getPoint()!=0)
				System.out.println(this.team.get(i).afficherprenom() + " a ramass� " + this.team.get(i).getPoint() + " t�tes de boeufs");
			else
				System.out.println(this.team.get(i).afficherprenom() + " a ramass� " + this.team.get(i).getPoint() + " t�te de boeufs");
		}
	}

	/**
	 * @brief : Tri de la liste de joueur par le score
	 */
	private void TriScore() {
		for (int i=0;i<this.nbJoueurs;i++) {		//Pour chaque joueur nous v�rifions si son score est plus grand que le joueur suivant
			for(int j=i;j<this.nbJoueurs;j++) {
				if(this.team.get(i).getPoint()>this.team.get(j).getPoint()) {		//Si ce n'es pas le cas alors nous les �changeons de place
					this.echangerplace(this.team.get(i), this.team.get(j));
				}
			}
		}
	}

	/**
	 * @brief : R�cup�rations de la longueur du plus petit nom
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
	 * @brief : �change de place entre deux joueurs 
	 * @param j1 : Joueur que l'on veut �changer
	 * @param j2 : Joueur que l'on veut �changer
	 */
	private void echangerplace(Joueur j1,Joueur j2) {
		int pointTmp=0;		//Utilisations de variables temporaires pour le changement 
		String prenomTmp=""; //Utilisations de variables temporaires pour le changement 
		pointTmp=j2.getPoint();		//Nous r�cup�rons les points de j2 	 
		prenomTmp=j2.afficherprenom();		//Nous r�cup�rons le nom de j2
		j2.ajoutPoint(j1.getPoint());		//Nous lui attribuons les points de j1
		j2.Setprenom(j1.afficherprenom());		//Nous lui attribuons le nom de j1
		j1.ajoutPoint(pointTmp); 		//Nous lui attribuons les points de j2
		j1.Setprenom(prenomTmp);		//Nous lui attribuons le nom de j2
	}


	/**
	 * @brief : Tri de la liste de joueur par ordre alphab�tique de la liste de joueur
	 */
	private void trialpha() {
		int a,b;	//Utilisations de variables temporaires pour la comparaison
		for (int i=0;i<this.nbJoueurs;i++) { //Pour chaque joueur nous v�rifions si nom est bien devant dans l'alpahabet 
			for(int j=i;j<this.nbJoueurs;j++) {
				int lng=min(this.team.get(i).afficherprenom(),this.team.get(j).afficherprenom());	//Nous r�cup�rons la longueur du plus petit nom
				for(int h=0;h<lng;h++) {		//Nous comparons lettre par lettre les deux noms
					a=(int)this.team.get(i).afficherprenom().charAt(h);
					b=(int)this.team.get(j).afficherprenom().charAt(h);
					if(a>b) {		//si une lettre de j1 est plus grande que celle de j2 alors nous �changeons leurs places
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
		this.trialpha();		//Nous trions d'abord par ordre alphab�tique
		this.TriScore();		//Puis nous trions par ordre de score
	}
}


