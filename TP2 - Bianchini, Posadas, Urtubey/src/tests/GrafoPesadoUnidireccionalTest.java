package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import grafo.GrafoPesadoUnidireccional;

public class GrafoPesadoUnidireccionalTest {

	GrafoPesadoUnidireccional<Integer> g;
	
	@Test
	public void inicializar(){
		g = new GrafoPesadoUnidireccional<Integer>();
		g.agregarVertice(1);
		g.agregarVertice(2);
		g.agregarVertice(3);
		
		g.agregarArista(1, 2, 6.0);
		g.agregarArista(2, 3, 5.0);
		g.agregarArista(1, 3, 7.0);
		
		System.out.println(g.obtenerCaminoMinimo(1, 3) );
	}
	
	@Test
	public void distanciaMinimaUnElementoTest(){
		g = new GrafoPesadoUnidireccional<Integer>();
		g.agregarVertice(1);
	}
	

}
