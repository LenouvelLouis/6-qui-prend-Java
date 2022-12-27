package appli;
/**
 * 
 * @file Application.java
 * Projet 6 qui prend
 * @author Lenouvel Louis et Gabriel Esteves
 * @version 02/03/2022
 *
 */

import jeux.Partie;

/**
 * @brief Class Application
 *
 */

	public class Application {

		public static void main(String args[]) {
			Partie p1=new Partie("config"); // initialisation de la partie
			p1.sixQuiPrend(); // lancement du jeu


		} 
	}

