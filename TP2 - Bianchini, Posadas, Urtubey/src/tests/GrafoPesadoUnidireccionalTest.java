package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import grafo.GrafoPesadoUnidireccional;

public class GrafoPesadoUnidireccionalTest {

	GrafoPesadoUnidireccional<Integer> _grafo;
	
	@Before
	public void inicializar(){
		_grafo = new GrafoPesadoUnidireccional<Integer>();

		_grafo.agregarVertice(0);
		_grafo.agregarVertice(1);
		_grafo.agregarVertice(2);
	}
	
	@Test
	public void agregarVerticeTest(){
		
		_grafo.agregarVertice(3);
		assertEquals(4, _grafo.cantVertices());
	}

	@Test
	public void eliminarVerticeTest(){
		
		_grafo.eliminarVertice(1);
		assertEquals(2, _grafo.cantVertices());
	}
	
	@Test
	public void agregarAristaTest(){
		
		assertTrue(_grafo.agregarArista(0, 1));
	}
	
	@Test
	public void eliminarAristaTest(){
		
		_grafo.agregarArista(0, 1);
		assertTrue(_grafo.eliminarArista(0, 1));
	}
	
	@Test
	public void agregarAristaPesadaTest(){
		
		assertTrue(_grafo.agregarArista(0, 1, 3.0));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void eliminarAristaPesadaTest(){
		
		_grafo.agregarArista(0, 1, 3.0);
		_grafo.eliminarArista(0, 1);
		
		_grafo.getPeso(0, 1);
	}
	
	@Test
	public void getPeso(){
		
		_grafo.agregarArista(0, 1, 3.0);
		assertEquals(3.0,_grafo.getPeso(0, 1), 0.0001);
	}
	
	@Test
	public void obtenerCaminoMinimoTest(){
		
		List<Integer> lista = new ArrayList<Integer>();
		lista.add(0);
		lista.add(2);
		_grafo.agregarArista(0, 1, 3.0);
		_grafo.agregarArista(1, 2, 5.0);
		_grafo.agregarArista(0, 2, 7.0);
		
		assertEquals(lista, _grafo.obtenerCaminoMinimo(0, 2));
	}
}
