package tests;


import org.junit.jupiter.api.Test;

import jeux.Paquet;

class PaquetTest {

	@Test
	void test() {
		Paquet p = new Paquet();
		for(int i=0;i<104;i++) {
		p.PrendreCarte();
	}
}
}
