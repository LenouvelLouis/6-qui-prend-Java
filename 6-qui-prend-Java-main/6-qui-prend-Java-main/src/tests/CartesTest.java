package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jeux.Cartes;

class CartesTest {

	@Test
	void test() {
		Cartes c=new Cartes(39);
		Cartes a=new Cartes(40);
		assertFalse(a.Equals(c));
		assertFalse(a.getBoeuf()==c.getBoeuf());
		assertTrue(a.compareTo(c)>0);
		
	}

}
