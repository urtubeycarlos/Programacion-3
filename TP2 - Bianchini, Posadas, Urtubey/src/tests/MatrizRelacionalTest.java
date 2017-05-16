package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import matriz.MatrizRelacional;

public class MatrizRelacionalTest {

	MatrizRelacional<String, Integer> m;
	
	@Before
	public void inicializar(){
		m = new MatrizRelacional<String, Integer>();
		
		m.set("Carlos", "Sebas", 5);
		m.set("Juan", "Pedro", 7);
		m.set("Carlos", "Pepe", 3);
		
	}
	
	@Test
	public void getAndSetTest() {
		assertEquals(new Integer(5), m.get("Carlos", "Sebas"));
	}
	
	@Test
	public void reSetTest(){
		m.set("Carlos", "Sebas", 8);
		assertEquals(new Integer(8), m.get("Carlos", "Sebas"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void removeTest(){
		m.remove("Carlos", "Sebas");
		m.get("Carlos", "Sebas");
	}

	@Test
	public void rowOfTest(){
		assertEquals("Carlos", m.rowOf(3));
	}

	@Test
	public void columnOfTest(){
		assertEquals("Pedro", m.columnOf(7));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void keyInvalidaTest(){
		m.get("Carlos", "Ezequiel");
	}
	
}
