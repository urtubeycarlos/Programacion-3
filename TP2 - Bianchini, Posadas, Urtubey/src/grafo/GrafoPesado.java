package grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

//FIXME: Para no romper el invariante de representacion habría que tener dos GrafoPesado en Mapa
//FIXME: Uno para los peajes y otro para las distancias.

public class GrafoPesado extends GrafoDirigido {
	
	private double[][] _matrizPesos;
	
	public GrafoPesado(int verticesIniciales) {
		super(verticesIniciales);
		//Se amortiza para una futura rendimension
		_matrizPesos = new double[verticesIniciales*4][verticesIniciales*4];
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
	
	public void agregarArista(int i, int j, double peso){
		super.agregarArista(i, j);
		_matrizPesos[i][j] = peso;
	}

	private void redimensionarMatrices(){
		// Complejidad O(n^n) amortizado
		int dimNueva = _matrizPesos.length*4;
		double[][] nuevaMatrizPesos = new double[dimNueva][dimNueva];
		
		for(int i=0; i<_matrizPesos.length; i++){
			for(int j=0; j<_matrizPesos.length; i++){
				nuevaMatrizPesos[i][j] = _matrizPesos[i][j];
			}
		}	
		
		_matrizPesos = nuevaMatrizPesos;
		
	}
	
	public double getPeso(int i, int j){
		chequearArista(i, j, "consultar el peso");
		return _matrizPesos[i][j];
	}
	
	//FIXME: Esta mal :/
	public Iterable<Integer> obtenerCaminoMinimo(int iOrigen, int jDestino){
		
		boolean[] visitados = new boolean[ getVertices() ];
		Camino ret = new Camino();
		Camino caminoAux = new Camino();
		PriorityQueue<Double> pesosActuales = new PriorityQueue<Double>();

		int nodoActual = iOrigen;
		HashMap<Double, Integer> aux = new HashMap<Double, Integer>();
		// HashMap< ...pesos correspondiente a... ,  este vertice con el actual >
		
		
		while( !visitados[jDestino] ){
			
			for(int vecino:getVecinos(nodoActual)){
				if( !visitados[vecino] ){
					pesosActuales.add( getPeso(nodoActual, vecino) );
					aux.put( getPeso(nodoActual, vecino), vecino );
					visitados[vecino] = true;
				}
			}
				
			double menorPeso = pesosActuales.peek();
			caminoAux.agregarNodo(aux.get(menorPeso), menorPeso);;
			
			
		}

		return ret.getIterable();
	}
	
	private class Camino {
		
		private ArrayList<Integer> _nodos;
		private double _pesoAcum;

		public Camino(){
			_nodos = new ArrayList<Integer>();
			_pesoAcum = 0.0;
		}
		
		private void agregarNodo(Integer nodo){
			_nodos.add(nodo);
		}
		
		public void agregarNodo(Integer nodo, double peso){
			agregarNodo(nodo);
			modificarPesoAcumulado(peso);
		}
		
		private void modificarPesoAcumulado(double valor){
			_pesoAcum += valor;
		}
		
		public double getPesoAcumulado(){
			return _pesoAcum;
		}
		
		public Iterable<Integer> getIterable(){
			return _nodos;
		}
		
	}
	
	public static void main(String[] args){
		GrafoPesado g = new GrafoPesado(5);
		g.obtenerCaminoMinimo(2, 3);
	}
	
}