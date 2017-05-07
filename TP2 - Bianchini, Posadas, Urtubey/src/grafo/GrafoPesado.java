package grafo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	
	//TODO: Documentar
	public List<Integer> obtenerCaminoMinimo(int origen, int destino){

		double[] distancias = new double[ getVertices() ];
		Arrays.fill(distancias, 1, distancias.length, Double.POSITIVE_INFINITY);
		
		boolean[] visitados = new boolean[ getVertices() ];
		
		ArrayList<Integer> ret = new ArrayList<Integer>();
	
		int nodo_actual_i;
		
		while ( !visitados[destino] ){
			
			nodo_actual_i = obtenerMenor(distancias, visitados);
			visitados[nodo_actual_i] = true;
			ret.add(nodo_actual_i);
			
			for( int nodo_j:getVecinos(nodo_actual_i)) if ( !visitados[nodo_j] ){
				
				double calculo_distancia = distancias[nodo_actual_i] + getPeso(nodo_actual_i, nodo_j);
				if( calculo_distancia < distancias[nodo_j] ){
					distancias[nodo_j] = calculo_distancia;
				}
				
			}
			
			
		}
		
		return ret;
		
	}
	
	public int obtenerMenor(double[] distancias, boolean[] visitados){
		
		int res = -1;
		double menor_actual = Double.POSITIVE_INFINITY;

		for( int i=0; i<distancias.length; i++ ) if ( !visitados[i] ){
			if( distancias[i] < menor_actual ){
				menor_actual = distancias[i];
				res = i;
			}
		}

		return res;
	}
	
	
	public static void main(String[] args){
		
//		double[] distancias = new double[]{1.2, 5.0, 3.0, 7.0, 2.0};
//		boolean[] visitados = new boolean[]{false, false, false, false, false};
		
		
		GrafoPesado g = new GrafoPesado(6);
		
//		System.out.println( g.obtenerMenor(distancias, visitados) );
		
		g.agregarArista(0, 1, 10.0);
		g.agregarArista(0, 2, 10.0);
		g.agregarArista(0, 5, 1.0);
		
		g.agregarArista(1, 2, 10.0);
		g.agregarArista(1, 4, 10.0);
		
		g.agregarArista(2, 3, 10.0);
		g.agregarArista(2, 4, 10.0);
		
		g.agregarArista(4, 3, 1.0);
		
		g.agregarArista(5, 4, 1.0);
		
		System.out.println( g.obtenerCaminoMinimo(0, 3) );
	}
	
}