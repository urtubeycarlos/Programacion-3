package grafo;
import grafo.GrafoPesadoMapa;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

//TODO: Testear y ver que todo funcione ok
public class GrafoPesadoMapaTest {

	private GrafoPesadoMapa _grafoPesado;

	@Before
	public void init(){
		this._grafoPesado = new GrafoPesadoMapa(10);
	}

	@Ignore
	public void testAgregarAristaIntInt() {
		this._grafoPesado.agregarArista(2, 3);
	}

	@Test
	public void testAgregarAristaIntIntInt() {
		this._grafoPesado.agregarArista(2, 3, 5, 0);
	}

}
