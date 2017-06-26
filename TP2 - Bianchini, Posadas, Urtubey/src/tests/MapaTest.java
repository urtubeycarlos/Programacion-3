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
				new Coordenada("Springfield", 37.579412513438385, -46.0546875), 
				new Coordenada("Shelbyville", 60.500525410511315, -27.7734375), 
				new Coordenada("Ciudad Gritos", 52.696361078274485, -18.6328125),
				new Coordenada("New York", 59.5343180010956, -0.87890625), 
				new Coordenada("Ciudad Capital", 44.465151013519616, 39.0234375),
		};
		
		m.agregarCoordenadas( Arrays.asList(coordenadas) );
		
		m.agregarRuta(coordenadas[0], coordenadas[1], false);
		m.agregarRuta(coordenadas[1], coordenadas[2], false);
		m.agregarRuta(coordenadas[2], coordenadas[3], false);
		m.agregarRuta(coordenadas[3], coordenadas[4], false);
		m.agregarRuta(coordenadas[0], coordenadas[4], false);
	
	}
	
	@Test
	public void agregarCoordenadasTest() {
		assertEquals(5, m.getCoordenadas().size() );
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
		assertEquals(new Integer(0), m.cantPeajes());
	}
	
	@Test
	public void calculaDistanciaTest() {
		Method calcDistancia;
		try {
			calcDistancia = m.getClass().getDeclaredMethod("calcularDistancia", Coordenada.class, Coordenada.class);
			calcDistancia.setAccessible(true);
			double dist1 = (double) calcDistancia.invoke(m, coordenadas[0], coordenadas[1]) + (double) calcDistancia.invoke(m, coordenadas[1], coordenadas[2]) + (double) calcDistancia.invoke(m, coordenadas[2], coordenadas[3]) + (double) calcDistancia.invoke(m, coordenadas[3], coordenadas[4]); 
			double dist2 = (double) calcDistancia.invoke(m, coordenadas[0], coordenadas[4]);
			System.out.println("dist01234 = " + dist1 );
			System.out.println("dist04 = " + dist2 );
			assertTrue( (double) calcDistancia.invoke(m, coordenadas[0], coordenadas[1]) == 29.31862067019632);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void obtenerRutaOptimaTest() {
		Coordenada[] coordenas_esperadas = new Coordenada[]{
				coordenadas[0],
				coordenadas[4]
		};
		List<Coordenada> camino = m.obtenerRutaOptima(coordenadas[0], coordenadas[4], 5);
		System.out.println(camino);
		List<Coordenada> resultado_esperado = new ArrayList<Coordenada>( Arrays.asList(coordenas_esperadas) );

		assertEquals(resultado_esperado, camino);

	}
	
	@Test
	public void obtenerRutaConPeajesSobrantesTest() {
		Coordenada[] coordenas_esperadas = new Coordenada[]{
				coordenadas[0],
				coordenadas[1],
				coordenadas[2]
		};
		
		List<Coordenada> resultado_esperado = new ArrayList<Coordenada>( Arrays.asList(coordenas_esperadas) );

		assertEquals(resultado_esperado, m.obtenerRutaOptima(coordenadas[0], coordenadas[2], 5));
	}
	
	@Test()
	public void sePuedeLlegarSinPeajesTest() {
		Coordenada[] coordenas_esperadas = new Coordenada[]{
				coordenadas[0],
				coordenadas[4]
		};
		
		List<Coordenada> resultado_esperado = new ArrayList<Coordenada>( Arrays.asList(coordenas_esperadas) );
		assertEquals(resultado_esperado, m.obtenerRutaOptima(coordenadas[0], coordenadas[4], 0));
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

	@Test
	public void testRutaM(){
		
		MapaRutas m3 = new MapaRutas();
		
		coordenadas = new Coordenada[]{
				new Coordenada("A", 37.579412513438385, -46.0546875),
				new Coordenada("B", 60.500525410511315, -27.7734375),
				new Coordenada("C", 52.696361078274485, -18.6328125),
				new Coordenada("D", 59.5343180010956, -0.87890625),
				new Coordenada("E", 44.465151013519616, 39.0234375),
		};
		
		m3.agregarCoordenadas( Arrays.asList(coordenadas) );
			
		m3.agregarRuta(coordenadas[0], coordenadas[1], false);
		m3.agregarRuta(coordenadas[1], coordenadas[2], false);
		m3.agregarRuta(coordenadas[2], coordenadas[3], false);
		m3.agregarRuta(coordenadas[3], coordenadas[4], false);
		m3.agregarRuta(coordenadas[0], coordenadas[4], true);
		
		
		
		Coordenada[] coordenas_esperadas = new Coordenada[]{
				coordenadas[0],
				coordenadas[4]
		};
		
		List<Coordenada> resultado_esperado = new ArrayList<Coordenada>( Arrays.asList(coordenas_esperadas) );
		
		assertEquals(resultado_esperado, m3.obtenerRutaOptima(coordenadas[0], coordenadas[4]));
		assertEquals(resultado_esperado, m3.obtenerRutaOptima(coordenadas[0], coordenadas[4], 2));
		assertEquals(resultado_esperado, m3.obtenerRutaOptima(coordenadas[0], coordenadas[4], 1)); 
		
		Coordenada[] coordenadas_esperadas = new Coordenada[]{
				coordenadas[0],
				coordenadas[1],
				coordenadas[2],
				coordenadas[3],
				coordenadas[4]
		};
		
		resultado_esperado = new ArrayList<Coordenada>( Arrays.asList( coordenadas_esperadas ) );
		
		assertEquals( resultado_esperado, m3.obtenerRutaOptima(coordenadas[0], coordenadas[4], 0) );
		
	}
	
}
