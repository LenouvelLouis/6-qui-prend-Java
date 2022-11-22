package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jeux.Partie;

class PartieTest {

	@Test
	void test() {
		Partie p = new Partie("dep");
		assertTrue(p.getnbJoueurs()>=2 && p.getnbJoueurs()<=10);
		
	}

}
