package grafo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Grafo {
	// Representamos el grafo por medio de listas de vecinos
	private ArrayList<HashSet<Integer>> _vecinos;

	// El grafo se construye sin aristas. el indice es el vertice, el hashset son sus vecinos
	public Grafo(int verticesIniciales){
		_vecinos = new ArrayList<HashSet<Integer>>();
		
		for(int i=0; i<verticesIniciales; ++i)
			_vecinos.add(new HashSet<Integer>());
	}
	
	// Agregar una arista
	public void agregarArista(int i, int j){
		chequearArista(i, j, "agregar");
		
		_vecinos.get(i).add(j);
		_vecinos.get(j).add(i);
	}
	
	// Como es un método para eliminar una arista?
	public void eliminarArista(int i, int j){
		chequearArista(i, j, "eliminar");
		
		_vecinos.get(i).remove(j);
		_vecinos.get(j).remove(i);
	}
	
	// Responde si existe una arista
	public boolean existeArista(int i, int j){
		chequearArista(i, j, "consultar");
		return _vecinos.get(i).contains(j);
	}

	// El nuevo vértice tiene rótulo n, si antes había n vértices
	public void agregarVertice(){
		_vecinos.add(new HashSet<Integer>());
	}

	// Retorna el grado (cantidad de vecinos) del vértice i
	public int getGrado(int i){
		chequearVertice(i, "el grado");
		return _vecinos.get(i).size();
	}
	
	// Retorna el conjunto de vecinos de un vértice
	public Set<Integer> getVecinos(int i){
		chequearVertice(i, "los vecinos");
		return _vecinos.get(i);
	}

	// Cantidad de vértices del grafo
	public int getVertices(){
		return _vecinos.size();
	}	

	// Verifica que un índice corresponda a un vértice válido
	private void chequearVertice(int i, String consulta){
		if( i < 0 || i >= getVertices())
			throw new IllegalArgumentException("Se intentó consultar " + consulta + " de un vértice inexistente! i = " + i);
	}

	// Verifica que los vértices puedan corresponder a una arista
	private void chequearArista(int i, int j, String accion){
		if( i < 0 || i >= getVertices() )
			throw new IllegalArgumentException("Se intentó " + accion + " una arista con un vértice inexistente! i = " + i);
		
		if( j < 0 || j >= getVertices() )
			throw new IllegalArgumentException("Se intentó " + accion + " una arista con un vértice inexistente! j = " + j);
		
		if( i == j )
			throw new IllegalArgumentException("Se intentó " + accion + " una arista con dos vertices iguales! i, j = " + i);
	}
}
