package grafo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GrafoUnidireccional<E> implements Grafo<E> {
	
	private HashMap<E, HashSet<E>> _vecinos;

	public GrafoUnidireccional(){
		_vecinos = new HashMap<E, HashSet<E>>();
	}
	
	@Override
	public boolean agregarVertice(E e) {
		return _vecinos.putIfAbsent(e, new HashSet<E>()) != null;
	}

	@Override
	public Set<E> getVertices() {
		return _vecinos.keySet();
	}
	
	@Override
	public int cantVertices() {
		return getVertices().size();
	}

	@Override
	public boolean agregarArista(E e1, E e2) {
		checkearArista(e1, e2, "agregar una arista");
		return _vecinos.get(e1).add(e2);
	}

	@Override
	public boolean eliminarArista(E e1, E e2) {
		checkearArista(e1, e2, "eliminar una arista");
		return _vecinos.get(e1).remove(e2);
	}

	@Override
	public boolean existeArista(E e1, E e2) {
		checkearArista(e1, e2, "ver si existe una arista");
		return _vecinos.get(e1).contains(e2);
	}

	@Override
	public Set<E> getVecinos(E e) {  
		checkearVertice(e, "obtener los vecinos");
		return clonarSet( _vecinos.get(e) );
	}

	@Override
	public int getGrado(E e) {
		checkearVertice(e, "obtener el grado");
		return getVecinos(e).size();
	}

	private Set<E> clonarSet(Set<E> in){
		return new HashSet<E>(in);
	}
	
	protected void checkearVertice(E e, String accion){
		if( !_vecinos.containsKey(e) )
			throw new IllegalArgumentException("Se intentó " + accion + " con un vértice inexistente! e = " + e.toString() );
	}
	
	protected void checkearArista(E e1, E e2, String accion){
		if( !_vecinos.containsKey(e1) )
			throw new IllegalArgumentException("Se intentó " + accion + " una arista con un vértice inexistente! e1 = " + e1.toString() );
		
		if( !_vecinos.containsKey(e2) )
			throw new IllegalArgumentException("Se intentó " + accion + " una arista con un vértice inexistente! e2 = " + e2.toString() );
		
		if ( e1.equals(e2) )
			throw new IllegalArgumentException("Se intentó " + accion + " una arista con dos vértices iguales!");
	}
	
	@Override
	public String toString(){
		String ret = new String("Grafo Unidireccional: {");
		for( E vertice:getVertices() ){
			ret += vertice.toString() + ": ";
			ret += getVecinos(vertice).toString() + "; ";
		} return ret = ret.substring(0, ret.length()-2) + "}";
	}
	
}
