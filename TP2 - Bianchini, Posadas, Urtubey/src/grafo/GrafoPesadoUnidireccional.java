package grafo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
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
			
		LinkedList<E> camino = new LinkedList<E>();
		
		if ( origen.equals(destino) ){
			camino.add(origen);
			return camino;
		}
			

		Nodo nodo_inicial = new Nodo(origen, 0.0);
		Nodo nodo_final = new Nodo(destino, Double.POSITIVE_INFINITY);
		
		PriorityQueue<Nodo> nodos_abiertos = new PriorityQueue<Nodo>( (n1, n2) -> (int) (n1.costo - n2.costo) );
		nodos_abiertos.add(nodo_inicial);
		nodos_abiertos.add(nodo_final);
		Set<Nodo> nodos_cerrados = new HashSet<Nodo>();
		
		Nodo nodo_actual = null;
		
		while( !nodos_abiertos.isEmpty() ){
			
			nodo_actual = nodos_abiertos.poll();
			if( nodo_actual.equals(nodo_final) )
				break;
			
			nodos_cerrados.add(nodo_actual);
			
			for( E vecino:getVecinos(nodo_actual.val) ){
				if( vecino != null ){
					Nodo nodo_vecino = new Nodo(vecino);
					if( !nodos_cerrados.contains(nodo_vecino) ){

						if( nodos_abiertos.contains(nodo_vecino) ){
							
							for( Nodo n:nodos_abiertos )
								if( n.equals(nodo_vecino) )
									nodo_vecino = n;
							
							if( nodo_actual.costo + getPeso(nodo_actual.val, nodo_vecino.val) <= nodo_vecino.costo ){
								nodo_vecino.costo = nodo_actual.costo + getPeso(nodo_actual.val, nodo_vecino.val);
								nodo_vecino.padre = nodo_actual;
							}
							
						} else {
							nodo_vecino.costo = nodo_actual.costo + getPeso(nodo_actual.val, nodo_vecino.val);
							nodo_vecino.padre = nodo_actual;
							nodos_abiertos.add( nodo_vecino );
						}
						
						
					}
				}
			}
			
		}
		
		Nodo n = nodo_actual;
		do {
			camino.addFirst( n.val );
			n = n.padre;
		} while ( n != null );
		
		return camino;
		
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
	
	@Override
	public String toString(){
		String ret = new String("Grafo Pesado Unidireccional: {");
		for( E vertice:getVertices() )
		for( E vecino:getVecinos(vertice) )
			ret += "[" + vertice +":" + vecino + "]: " + getPeso(vertice, vecino) + "; ";
		return ret = ret.substring(0, ret.length()-2) + "}";
	}

	
	private class Nodo {
		
		Nodo padre = null;
		E val = null;
		double costo = 0.0;
		
		public Nodo(E val){
			this.val = val;
		}
		
		public Nodo(E val, double costo){
			this.val = val;
			this.costo = costo;
		}
		
		@Override
		public boolean equals(Object o){
			@SuppressWarnings("unchecked")
			Nodo in = (Nodo) o;
			return val.equals(in.val);
		}
		
	}
	
}
