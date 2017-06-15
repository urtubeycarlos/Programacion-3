package grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import matriz.MatrizRelacional;

public class GrafoPesadoUnidireccional<E> extends GrafoUnidireccional<E> {

	MatrizRelacional<E, Double> _matrizPesos;
	
	public GrafoPesadoUnidireccional(){
		_matrizPesos = new MatrizRelacional<E, Double>();
	}
	
	@Override
	public boolean agregarVertice(E vertice){
		return super.agregarVertice(vertice);
	}
	
	@Override
	public boolean eliminarVertice(E vertice){
		eliminarPesos(vertice);
		return super.eliminarVertice(vertice);
	}

	private void eliminarPesos(E vertice) {
		for( E vecino:getVecinos(vertice) )
			_matrizPesos.remove(vertice, vecino);
	}
	
	@Override
	public boolean agregarArista(E vertice1, E vertice2){
		return this.agregarArista(vertice1, vertice2, 0.0);
	}
	
	public boolean agregarArista(E vertice1, E vertice2, Double peso){
		_matrizPesos.set(vertice1, vertice2, peso);
		return super.agregarArista(vertice1, vertice2);
	}
	
	@Override
	public boolean eliminarArista(E e1, E e2){
		_matrizPesos.remove(e1, e2);
		return super.eliminarArista(e1, e2);
	}
	
	public Double getPeso(E vertice1, E vertice2){
		super.checkearArista(vertice1, vertice2, "obtener el peso");
		return _matrizPesos.get(vertice1, vertice2);
	}
	
	public GrafoPesadoUnidireccional<E> clonar(){
		GrafoPesadoUnidireccional<E> ret = new GrafoPesadoUnidireccional<>();
		
		for(E vertice:getVertices())
			ret.agregarVertice(vertice);
		
		for(E vertice:getVertices())
		for(E vecino:getVecinos(vertice))
			ret.agregarArista(vertice, vecino, getPeso(vertice, vecino));
		return ret;
	}

	public List<E> obtenerCaminoMinimo(E origen, E destino){

		super.checkearVertice(origen, "obtener un camino minimo");
		super.checkearVertice(destino, "obtener un camino minimo");
		
		if( !sePuedeLlegar(origen, destino) )
			throw new RuntimeException( "No se puede llegar a destino = " + destino );
		
		E nodo_actual;

		HashMap<E, Double> distancias = new HashMap<E, Double>();
		for( E vertice:super.getVertices() )
			distancias.put(vertice, Double.POSITIVE_INFINITY);
		distancias.put(origen, 0.0);
		
		List<E> camino_actual = new ArrayList<E>();
		Set<E> visitados = new HashSet<E>();
		
		while( !visitados.contains(destino) ){
			
			nodo_actual = obtenerMenor(distancias, visitados); 
			visitados.add(nodo_actual);
			camino_actual.add(nodo_actual);

			for( E nodo_j:super.getVecinos(nodo_actual) ) if( nodo_j != null && !visitados.contains(nodo_j) ){
				Double calc_distancia = distancias.get(nodo_actual) + getPeso(nodo_actual, nodo_j);
				if ( calc_distancia < distancias.get(nodo_j) ){
					distancias.put(nodo_j, calc_distancia);
				}
			}
		}

		E nodo_sobrante;
		while( (nodo_sobrante = getNodoSobrante(camino_actual, distancias) ) != null )
			camino_actual.remove(nodo_sobrante);
		
		return camino_actual;
	}
	
	private E getNodoSobrante(List<E> camino, HashMap<E, Double> distancias){
		for(int i=1; i<camino.size()-1; i++){
			
			E anterior = camino.get(i-1);
			E actual = camino.get(i);
			E siguiente = camino.get(i+1);
			
			if( existeArista(anterior, siguiente) ){
				if( !existeArista(actual, siguiente) ){
					return actual;
				} else {
					List<E> con_actual = new ArrayList<E>(camino);
					List<E> sin_actual = new ArrayList<E>(camino);
					sin_actual.remove(actual);

					double peso_acum_con_actual = calcularPeso(con_actual);
					double peso_acum_sin_actual = calcularPeso(sin_actual);
					if( peso_acum_sin_actual < peso_acum_con_actual )
						return actual;
				}
			}
		} return null;
	}
	
	private double calcularPeso(List<E> camino){
		double ret = 0.0;
		for(int i=0; i<camino.size()-1; i++)
			ret += this.getPeso(camino.get(i), camino.get(i+1));
		return ret;
		
	}
	
	private boolean sePuedeLlegar(E origen, E destino){
		E nodo_actual = destino;
		boolean cambio_nodo;
		while( !nodo_actual.equals(origen) ){
			cambio_nodo = false;
			for( E vertice:getVertices() ) if( !cambio_nodo )
				if( getVecinos(nodo_actual) != null && getVecinos(vertice).contains(nodo_actual) ){
					nodo_actual = vertice;
					cambio_nodo = true;
				}
			if( cambio_nodo )
				continue;
			return false;
		} return true;
	}
	
	private E obtenerMenor(HashMap<E, Double> dist_tentativas, Set<E> visitados){
		
		E ret = null;
		Double menor_actual = Double.POSITIVE_INFINITY;

		for( Entry<E, Double> entry:dist_tentativas.entrySet() ){
			if( entry.getValue() < menor_actual && !visitados.contains( entry.getKey() ) ){
				menor_actual = entry.getValue();
				ret = entry.getKey();
			}
		} return ret;
	}
	
	
	
	@Override
	public String toString(){
		String ret = new String("Grafo Pesado Unidireccional: {");
		for( E vertice:getVertices() )
		for( E vecino:getVecinos(vertice) )
			ret += "[" + vertice +":" + vecino + "]: " + getPeso(vertice, vecino) + "; ";
		return ret = ret.substring(0, ret.length()-2) + "}";
	}
	
}
