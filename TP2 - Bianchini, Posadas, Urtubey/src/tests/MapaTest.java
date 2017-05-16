package tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import mapa.Coordenada;
import mapa.MapaRutas;

public class MapaTest {

	MapaRutas m;
	
	@Before
	public void inicializar() {
		m = new MapaRutas();
	}

	@Test
	public void agregarCoordenadaTest() {
		Coordenada c = new Coordenada("Springfield", 200.5, 200.5);
		m.agregarCoordenada(c);
		assertEquals(1, m.getCoordenadas().size());
	}
	
	@Test
	public void agregarCoordenadasTest() {
		Coordenada[] coordenadas = new Coordenada[]{
				new Coordenada("Springfield", 510.0, 213.0), 
				new Coordenada("Shelbyville", -510, -213), 
				new Coordenada("Ciudad Gritos", 666, 123)
		};
		m.agregarCoordenadas( Arrays.asList(coordenadas) );
		assertEquals(3, m.getCoordenadas().size() );
	}

	

	@Test
	public void agregarRuta() {
		
		Coordenada c1 = new Coordenada("Springfield", 510.0, 213.0); 
		Coordenada c2 = new Coordenada("Shelbyville", -510, -213);
		m.agregarCoordenada(c1);
		m.agregarCoordenada(c2);
		m.agregarRuta(c1, c2, true);
		assertTrue( m.existeRuta(c1, c2) );
		assertFalse( m.existeRuta(c2, c1) );
		
	}

	@Test
	public void getCoordenadas() {
		
	}

	@Test
	public void cantPeajes() {
	}

	@Test
	public void obtenerRutaOptimaTest() {
		Coordenada[] coordenadas = new Coordenada[]{
				new Coordenada("Springfield", 510.0, 213.0), 
				new Coordenada("Shelbyville", -510, -213), 
				new Coordenada("Ciudad Gritos", 666, 123)
		};
		m.agregarCoordenadas( Arrays.asList(coordenadas) );
	}

}
