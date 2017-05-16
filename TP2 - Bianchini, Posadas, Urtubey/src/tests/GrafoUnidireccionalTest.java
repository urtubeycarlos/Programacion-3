package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import grafo.GrafoUnidireccional;

public class GrafoUnidireccionalTest {

	private GrafoUnidireccional<String> _grafo;
	
	@Before
	public void inicializar(){
		_grafo = new GrafoUnidireccional<String>();
		
		_grafo.agregarVertice("Carlos");
		_grafo.agregarVertice("Sebas");
		_grafo.agregarVertice("Juan");

		_grafo.agregarArista("Sebas", "Juan");
	}

	@Test
	public void agregarVerticeTest() {
		assertEquals(3, _grafo.getVertices().size() );
	}

	@Test
	public void eliminarVerticeTest(){
		assertTrue(_grafo.eliminarVertice("Carlos"));
	}
	
	@Test
	public void agregarAristaTest(){
		assertTrue( _grafo.existeArista("Sebas", "Juan") );
	}
	
	@Test
	public void eliminarAristaTest(){
		_grafo.eliminarArista("Sebas", "Juan");
		assertFalse(_grafo.existeArista("Sebas", "Juan"));
	}
	
	@Test
	public void getGradoTest(){
		assertEquals(1, _grafo.getGrado("Sebas"));
	}
	
	@Test
	public void getVecinosTest(){
		assertTrue( _grafo.getVecinos("Sebas").contains("Juan") );
	}
	
}
