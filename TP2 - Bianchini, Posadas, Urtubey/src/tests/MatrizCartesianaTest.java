package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import matriz.MatrizCartesiana;

public class MatrizCartesianaTest {

	MatrizCartesiana<String> m;
	
	@Before
	public void inicializar(){
		m = new MatrizCartesiana<String>(3, 4);
		m.set(2, 1, "Carlos");
		m.set(1, 2, "Sebas");
		m.set(2, 2, "Juan");
	}

	@Test
	public void alturaTest(){
		assertEquals(new Integer(4), m.height());
	}
	
	@Test
	public void anchuraTest(){
		assertEquals(new Integer(3), m.width());
	}
	
	@Test
	public void dimensionTest(){
		assertEquals(new Integer(12), m.size());
	}
	
	@Test
	public void getAndSetTest() {
		assertEquals("Sebas", m.get(1, 2));
	}
	
	@Test
	public void columnOfTest(){
		assertEquals(new Integer(1), m.columnOf("Carlos"));
	}
	
	@Test
	public void rowOfTest(){
		assertEquals(new Integer(2), m.rowOf("Carlos"));
	}
	
	@Test 
	public void redimensionarTest(){
		m.resize(7, 4);
		assertEquals(new Integer(7*4), m.size());
	}

	@Test(expected=IllegalArgumentException.class)
	public void coordenadaInvalidaTest(){
		m.get(10, 15);
	}
	
	
}
