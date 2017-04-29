import java.util.ArrayList;

public class GrafoPesado extends Grafo {
	
	private static ArrayList<ArrayList<Integer>> _matrizDePesos;

	public GrafoPesado(int verticesIniciales) {
		super(verticesIniciales);
		
		_matrizDePesos = new ArrayList<ArrayList<Integer>>();
	}

}
