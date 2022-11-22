package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jeux.Joueur;
import jeux.Paquet;

class JoueurTest {

	@Test
	void test() {
		Paquet p=new Paquet();
		Joueur j1= new Joueur(p);
		assertTrue(j1.getPoint()==0);
		int point = 500;
		j1.Point(point);
		assertTrue(j1.getPoint()==point);
		System.out.println(j1);
	}

}
