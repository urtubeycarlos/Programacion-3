package grafo;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

//FIXME: Para no romper el invariante de representacion habría que tener dos GrafoPesado en Mapa
//FIXME: Uno para los peajes y otro para las distancias.

public class GrafoPesado extends Grafo {
	
	private int[][] _matrizPesos;
	
	public GrafoPesado(int verticesIniciales) {
		super(verticesIniciales);
		//Se amortiza para una futura rendimension
		_matrizPesos = new int[verticesIniciales*4][verticesIniciales*4];
	}
	
	@Override
	public void agregarVertice(){
		super.agregarVertice();
		if( super.getVertices() >= _matrizPesos.length )
			redimensionarMatrices();
	}
	
	@Override
	public void agregarArista(int i, int j){
		this.agregarArista(i, j, 0);
	}
	
	public void agregarArista(int i, int j, int peso){
		super.agregarArista(i, j);
		_matrizPesos[i][j] = peso;
	}

	private void redimensionarMatrices(){
		// Complejidad O(n^n) amortizado
		int dimNueva = _matrizPesos.length*4;
		int[][] nuevaMatrizPesos = new int[dimNueva][dimNueva];
		
		for(int i=0; i<_matrizPesos.length; i++){
			for(int j=0; j<_matrizPesos.length; i++){
				nuevaMatrizPesos[i][j] = _matrizPesos[i][j];
			}
		}	
		
		_matrizPesos = nuevaMatrizPesos;
		
	}
	
	public int getPeso(int i, int j){
		chequearArista(i, j, "consultar el peso");
		return _matrizPesos[i][j];
	}
	
	//TODO: Implementar Dijsktra
	public List<Integer> obtenerCaminoMinimo(int iOrigen, int jDestino){
		List<Integer> ret = new ArrayList<Integer>();
		PriorityQueue<Integer> cola = new PriorityQueue<Integer>();
//		boolean[] visitados = new boolean[ getVertices() ];

		int nodo_actual = iOrigen;
		while( !ret.contains(jDestino) ){
			
			
			
		}
		
		return ret;
	}
	
}