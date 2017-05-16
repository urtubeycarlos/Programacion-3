package tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import mapa.Coordenada;
import mapa.MapaRutas;

public class MapaTest {

	private MapaRutas m;
	private Coordenada[] coordenadas;
	
	@Before
	public void inicializar() {
		
		m = new MapaRutas();
		
		coordenadas = new Coordenada[]{
				new Coordenada("Springfield", 510.0, 213.0), 
				new Coordenada("Shelbyville", 310.0, 220.0), 
				new Coordenada("Ciudad Gritos", 666.0, 123.0)
		};
		
		m.agregarCoordenadas( Arrays.asList(coordenadas) );
		
		m.agregarRuta(coordenadas[0], coordenadas[1], false);
		m.agregarRuta(coordenadas[1], coordenadas[2], false);
		m.agregarRuta(coordenadas[0], coordenadas[2], true);
	
	}

	@Test
	public void agregarCoordenadaTest() {
		assertEquals(3, m.getCoordenadas().size());
	}
	
	@Test
	public void agregarCoordenadasTest() {
		assertEquals(3, m.getCoordenadas().size() );
	}

	@Test
	public void agregarRuta() {
		assertTrue( m.existeRuta(coordenadas[0], coordenadas[1]) );
		assertFalse( m.existeRuta(coordenadas[1], coordenadas[0]) );
	}

	
	@Test
	public void getCoordenadas() {
		assertEquals( Arrays.asList(coordenadas), m.getCoordenadas() );
	}

	@Test
	public void cantPeajes() {
		assertEquals(new Integer(1), m.cantPeajes());
	}

	@Test
	public void obtenerRutaOptimaTest() {
		System.out.println( m.obtenerRutaOptima(coordenadas[0], coordenadas[2], 0) );
	}
	

}
