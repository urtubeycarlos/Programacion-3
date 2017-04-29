package grafo;
import grafo.GrafoPesado;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import grafo.Grafo;

public class GrafoPesadoTest {

	private GrafoPesado _grafoPesado;

	@Before
	public void init(){
		this._grafoPesado = new GrafoPesado(10);
	}

	@Ignore
	public void testAgregarAristaIntInt() {
		this._grafoPesado.agregarArista(2, 3);
	}

	@Test
	public void testAgregarAristaIntIntInt() {
		this._grafoPesado.agregarArista(2, 3, 5);
	}

}
