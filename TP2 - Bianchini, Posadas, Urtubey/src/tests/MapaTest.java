package tests;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		m.agregarRuta(coordenadas[1], coordenadas[2], true);
		m.agregarRuta(coordenadas[0], coordenadas[2], false);
	
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
	public void calculaDistanciaTest() {
		Method calcDistancia;
		try {
			calcDistancia = m.getClass().getDeclaredMethod("calcularDistancia", Coordenada.class, Coordenada.class);
			calcDistancia.setAccessible(true);
			assertTrue( (double) calcDistancia.invoke(m, coordenadas[0], coordenadas[2]) == 180.0999722376436);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void obtenerRutaOptimaTest() {
		Coordenada[] coordenas_esperadas = new Coordenada[]{
				coordenadas[0],
				coordenadas[2]
		};
		
		List<Coordenada> resultado_esperado = new ArrayList<Coordenada>( Arrays.asList(coordenas_esperadas) );

		assertEquals(resultado_esperado, m.obtenerRutaOptima(coordenadas[0], coordenadas[2], 1));

	}
	
	@Test
	public void obtenerRutaConPeajesSobrantesTest() {
		Coordenada[] coordenas_esperadas = new Coordenada[]{
				coordenadas[0],
				coordenadas[2]
		};
		
		List<Coordenada> resultado_esperado = new ArrayList<Coordenada>( Arrays.asList(coordenas_esperadas) );

		assertEquals(resultado_esperado, m.obtenerRutaOptima(coordenadas[0], coordenadas[2], 5));
	}
	
	@Test()
	public void sePuedeLlegarSinPeajesTest() {
		Coordenada[] coordenas_esperadas = new Coordenada[]{
				coordenadas[0],
				coordenadas[2]
		};
		
		List<Coordenada> resultado_esperado = new ArrayList<Coordenada>( Arrays.asList(coordenas_esperadas) );
		assertEquals(resultado_esperado, m.obtenerRutaOptima(coordenadas[0], coordenadas[2], 0));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void noSePuedeLlegarSinPeajesTest() {
		MapaRutas m2 = new MapaRutas();
		
		coordenadas = new Coordenada[]{
				new Coordenada("Springfield", 510.0, 213.0), 
				new Coordenada("Shelbyville", 310.0, 220.0), 
				new Coordenada("Ciudad Gritos", 666.0, 123.0)
		};
		
		m2.agregarCoordenadas( Arrays.asList(coordenadas) );
		m2.agregarRuta(coordenadas[0], coordenadas[1], false);
		m2.agregarRuta(coordenadas[1], coordenadas[2], true);
		
		m2.obtenerRutaOptima(coordenadas[0], coordenadas[2], 0);
	}

}
