package tests;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
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
	
	@Test
	public void obtenerCaminoMinimoTest2(){
		
		GrafoPesadoUnidireccional<Integer> g = new GrafoPesadoUnidireccional<>();

		List<Integer> resultado_esperado = new ArrayList<Integer>();
		Integer[] vertices_esperados = new Integer[]{1, 2, 5, 4};
		resultado_esperado.addAll( Arrays.asList(vertices_esperados) );
		
		g.agregarVertice(1);
		g.agregarVertice(2);
		g.agregarVertice(3);
		g.agregarVertice(4);
		g.agregarVertice(5);
		g.agregarVertice(6);
		// 1234 : 4 + 3 + 4 = 11
		// 1254 : 4 + 1 + 4 = 9 
		// 134: 8 + 4 = 12
		g.agregarArista(1, 2, 4.0);
		g.agregarArista(1, 6, 3.0);
		g.agregarArista(1, 3, 8.0);
		
		g.agregarArista(2, 5, 1.0);
		g.agregarArista(2, 3, 3.0);
		
		g.agregarArista(3, 4, 4.0);
		g.agregarArista(3, 5, 1.0);
		
		g.agregarArista(5, 4, 4.0);
		
		g.agregarArista(6, 5, 3.0);
		
		assertEquals(resultado_esperado, g.obtenerCaminoMinimo(1, 4));
	}
	
	@Test
	public void testPrimerCaminoMasLargo(){
		
		GrafoPesadoUnidireccional<Integer> g = new GrafoPesadoUnidireccional<>();

		List<Integer> resultado_esperado = new ArrayList<Integer>();
		Integer[] vertices_esperados = new Integer[]{1, 5};
		resultado_esperado.addAll( Arrays.asList(vertices_esperados) );
		
		g.agregarVertice(1);
		g.agregarVertice(2);
		g.agregarVertice(3);
		g.agregarVertice(4);
		g.agregarVertice(5);
		
		g.agregarArista(1, 2, 2.0);
		g.agregarArista(1, 5, 4.0);
		g.agregarArista(2, 3, 2.0);
		g.agregarArista(3, 4, 2.0);
		g.agregarArista(4, 5, 2.0);
		
		assertEquals(resultado_esperado, g.obtenerCaminoMinimo(1, 5));
	}

	@Test(expected=RuntimeException.class)
	public void caminoImposibleTest(){
		_grafo.agregarArista(0, 1);
		_grafo.obtenerCaminoMinimo(0, 2);
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
