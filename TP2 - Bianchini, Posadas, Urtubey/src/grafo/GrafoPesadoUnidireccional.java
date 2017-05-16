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
		for( E vecino:getVecinos(vertice) )
			_matrizPesos.remove(vertice, vecino);
		return super.eliminarVertice(vertice);
	}
	
	@Override
	public boolean agregarArista(E vertice1, E vertice2){
		return this.agregarArista(vertice1, vertice2, 0.0);
	}
	
	public boolean agregarArista(E vertice1, E vertice2, Double peso){
		_matrizPesos.set(vertice1, vertice2, peso);
		return super.agregarArista(vertice1, vertice2);
	}
	
	public Double getPeso(E vertice1, E vertice2){
		super.checkearArista(vertice1, vertice2, "obtener el peso");
		return _matrizPesos.get(vertice1, vertice2);
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

			for( E nodo_j:super.getVecinos(nodo_actual) ) if( !visitados.contains(nodo_j) && nodo_j != null){
				Double calc_distancia = distancias.get(nodo_actual) + getPeso(nodo_actual, nodo_j);
				if ( calc_distancia < distancias.get(nodo_j) ){
					distancias.put(nodo_j, calc_distancia);
				}
			}
		}

		if( !camino_actual.contains(destino) )
			return null;

		for( int i=1; i<camino_actual.size()-1; i++ ){
			
			E anterior = camino_actual.get(i-1);
			E siguiente = camino_actual.get(i+1);
			E actual = camino_actual.get(i);
			
			if( existeArista(anterior, siguiente) ){
				if( !existeArista(actual, siguiente) ){
					camino_actual.remove(actual);
				} else {
					double peso_ant_sig = getPeso(anterior, siguiente);
					double peso_ant_act_sig = peso_ant_sig + getPeso(actual, siguiente);
				
					if( peso_ant_sig <= peso_ant_act_sig )
						camino_actual.remove(actual);
				}
			}
			
		}
		
		return camino_actual;
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
