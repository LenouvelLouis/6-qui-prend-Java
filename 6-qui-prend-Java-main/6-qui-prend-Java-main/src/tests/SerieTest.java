package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jeux.Paquet;
import jeux.Serie;

class SerieTest {

	@Test
	void test() {
		Paquet p= new Paquet();
		Serie s = new Serie(p,1);
		for(int i=0;i<4;i++) {
			s.ajoutCarte(p.PrendreCarte());
		}
		System.out.println(s);
		assertTrue(s.MaxCartesSerie());
		s.Pénalité();
		assertFalse(s.MaxCartesSerie());
	}

}
