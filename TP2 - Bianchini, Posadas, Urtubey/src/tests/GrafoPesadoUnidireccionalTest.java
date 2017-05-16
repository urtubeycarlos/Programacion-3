package tests;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	@Test(expected=RuntimeException.class)
	public void caminoImposibleTest(){
		_grafo.agregarArista(0, 1);
		_grafo.obtenerCaminoMinimo(0, 2);
	}
	
	@Test
	public void obtenerMenorTest() throws Exception {

		Method obtenerMenor = _grafo.getClass().getDeclaredMethod("obtenerMenor", HashMap.class, Set.class);
		obtenerMenor.setAccessible(true);
		
		HashMap<Integer, Double> hashMapPrueba = new HashMap<>();
		hashMapPrueba.put(1, 5.0);
		hashMapPrueba.put(2, 7.0);
		hashMapPrueba.put(3, 9.0);
		
		Set<Integer> setPrueba = new HashSet<Integer>();
		setPrueba.add(1);
		
		assertEquals(new Integer(2), obtenerMenor.invoke(_grafo, hashMapPrueba, setPrueba));
		
	}

	@Test
	public void sePuedeLlegarTest() throws Exception {

		Method obtenerMenor = _grafo.getClass().getDeclaredMethod("sePuedeLlegar", Object.class, Object.class);
		obtenerMenor.setAccessible(true);
		
		_grafo.agregarArista(0, 1);
		_grafo.agregarArista(1, 2);
		_grafo.agregarArista(0, 2);
		
		assertTrue( (Boolean) obtenerMenor.invoke(_grafo, 0, 2));
		
	}
	
	@Test
	public void noSePuedeLlegarTest() throws Exception {

		Method obtenerMenor = _grafo.getClass().getDeclaredMethod("sePuedeLlegar", Object.class, Object.class);
		obtenerMenor.setAccessible(true);
		assertFalse( (Boolean) obtenerMenor.invoke(_grafo, 0, 2));
		
	}
	
}
