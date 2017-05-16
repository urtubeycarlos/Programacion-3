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
	public void agregarCoordenadas() {
	}

	@Test
	public void agregarRuta() {
	}

	@Test
	public void getCoordenadas() {
	}

	@Test
	public void cantPeajes() {
	}

	@Test
	public void obtenerRutaOptimaTest() {
	}

}
