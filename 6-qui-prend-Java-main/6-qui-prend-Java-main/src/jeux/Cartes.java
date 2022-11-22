package jeux;

/**
 * 
 * @file Cartes.java
 * Projet 6 qui prend
 * @author Lenouvel Louis et Gabriel Esteves
 * @version 02/03/2022
 *
 */


/**
 * @brief Class Cartes 
 *
 */
public class Cartes implements Comparable <Cartes>  {
	private int valeur; // valeur de la carte
	private int boeuf;  // tête de boeuf de la carte


	/**@Brief : Constructeur de Cartes
	 * @param i : le numéro de la carte
	 */
	public Cartes(int i) throws IllegalArgumentException {
		if(i<0 || i>103)
			throw new IllegalArgumentException();
		this.setValeur(i+1); // intialisation de la valeur de la carte
		int dizaine=this.getValeur()/10;
		int unité = this.getValeur()%10;
		switch (unité) {	//attribution des têtes de boeufs en fonction de la valeur des unités
		case 0:
			this.setBoeuf(3);
			if(unité-dizaine==0&&this.getValeur()>=10) { // si la valeur des unités et dizaines sont identique
				this.setBoeuf(5);
			}
			break;
		case 5:
			this.setBoeuf(2);
			if(unité-dizaine==0&&this.getValeur()>=10) {	// si la valeur des unités et dizaines sont identique
				this.setBoeuf(5);
			}
			break;
		default:	// si la valeur des unités ne finit pas par 0 ou 5 alors 
			this.setBoeuf(1);
			if(unité-dizaine==0&&this.getValeur()>=10) {	// si la valeur des unités et dizaines sont identique
				this.setBoeuf(5);
			}
			break;
		}


	}

	/**
	 * @return la valeur de la tête de boeuf
	 */
	public int getBoeuf() {
		return boeuf;
	}


	/**
	 * @param [in] boeuf : valeur de la tête de boeuf
	 */
	public void setBoeuf(int boeuf) {
		this.boeuf = boeuf;
	}
	/**
	 * @return la valeur de la carte
	 */
	public int getValeur() {
		return valeur;
	}

	/**
	 * @param [in] val : valeur de la carte
	 */
	public void setValeur(int val) {
		this.valeur = val;
	}

	/**
	 * @brief : Permet de comparer 2 cartes
	 * @param c: la carte à comparer
	 * @return différence entre 2 cartes
	 */
	public int compareTo(Cartes c) {
		return (this.getValeur()- c.getValeur());
	}


	/**
	 * @brief : vérifie si 2 cartes sont égaux
	 * @param c : la carte à comparer
	 * @return true si les valeurs des cartes sont égaux
	 */
	public Boolean Equals(Cartes c) {
		return this.valeur == c.getValeur(); 
	}

	@Override
	/**
	 * @brief : représentation d'une carte
	 * @return  Carte : string représentant une carte
	 */
	public String toString() {
		String Carte="";
		if(this.getBoeuf()!=1) // si la valeur de la tête de boeuf est différente de 1 
			Carte+=this.getValeur()+" ("+this.getBoeuf()+")";	// nous l'affichons
		else  // sinon 
			Carte+=this.getValeur(); //nous affichons uniquement la valeur de la carte
		return Carte;
	}


}