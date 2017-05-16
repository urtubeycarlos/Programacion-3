package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import grafo.GrafoPesadoUnidireccional;

public class GrafoPesadoUnidireccionalTest {

	GrafoPesadoUnidireccional<Integer> _grafo;
	
	@Test
	public void agregarVerticeTest(){
		_grafo = new GrafoPesadoUnidireccional<Integer>();
		_grafo.agregarVertice(1);
		assertEquals(1, _grafo.cantVertices());
	}

	@Test
	public void eliminarVerticeTest(){
		
	}
	
	@Test
	public void agregarAristaTest(){
		_grafo = new GrafoPesadoUnidireccional<Integer>();
		
		_grafo.agregarVertice(0);
		_grafo.agregarVertice(1);
		
		assertTrue(_grafo.agregarArista(0, 1));
	}
	
	@Test
	public void agregarAristaPesadaTest(){
		_grafo = new GrafoPesadoUnidireccional<Integer>();
		
		_grafo.agregarVertice(0);
		_grafo.agregarVertice(1);
		
		assertTrue(_grafo.agregarArista(0, 1, 3.0));
	}
	
	@Test
	public void getPeso(){
		_grafo = new GrafoPesadoUnidireccional<Integer>();
		
		_grafo.agregarVertice(0);
		_grafo.agregarVertice(1);
		
		_grafo.agregarArista(0, 1, 3.0);
		assertEquals(3.0,_grafo.getPeso(0, 1), 0.0001);
	}
	
	@Test
	public void obtenerCaminoMinimoTest(){
		
	}
	
	@Test
	public void distanciaMinimaUnElementoTest(){
		_grafo = new GrafoPesadoUnidireccional<Integer>();
		_grafo.agregarVertice(0);
		_grafo.agregarVertice(1);
		_grafo.agregarVertice(2);
		
		_grafo.agregarArista(0, 1, 3.0);
		_grafo.agregarArista(0, 2, 5.0);	
		_grafo.agregarArista(1, 2, 3.0);
		
	}
	

}
