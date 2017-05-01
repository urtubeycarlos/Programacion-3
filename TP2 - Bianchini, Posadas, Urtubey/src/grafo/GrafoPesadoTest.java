package grafo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

//TODO: Testear todo
public class GrafoPesadoTest {

	private GrafoPesado _grafoPesado;
	
	@Before
	public void initialize(){
		_grafoPesado = new GrafoPesado(10);
	}
	
	@Test
	public void test() {
		assertEquals(10, _grafoPesado.getVertices());
	}

	@Test
	public void agregarVerticeTest(){
		_grafoPesado.agregarVertice();
		assertEquals(11, _grafoPesado.getVertices());
	}
	
}
