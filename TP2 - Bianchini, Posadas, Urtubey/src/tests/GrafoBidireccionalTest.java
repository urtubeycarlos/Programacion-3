package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import grafo.GrafoBidireccional;

public class GrafoBidireccionalTest {

private GrafoBidireccional<String> _grafo;
	
	@Before
	public void inicializar(){
		_grafo = new GrafoBidireccional<String>();
		
		_grafo.agregarVertice("Carlos");
		_grafo.agregarVertice("Sebas");
		_grafo.agregarVertice("Juan");

		_grafo.agregarArista("Sebas", "Juan");
	}
	
	@Test
	public void existeAristaBidireccionalTest(){
		assertTrue( _grafo.existeArista("Juan", "Sebas") );
	}
	
	@Test
	public void eliminarAristaBidireccionalTest(){
		_grafo.eliminarArista("Sebas", "Juan");
		assertFalse( _grafo.existeArista("Juan", "Sebas") );
	}
	
}
