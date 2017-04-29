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
	
	// Como es un m�todo para eliminar una arista?
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

	// El nuevo v�rtice tiene r�tulo n, si antes hab�a n v�rtices
	public void agregarVertice(){
		_vecinos.add(new HashSet<Integer>());
	}

	// Retorna el grado (cantidad de vecinos) del v�rtice i
	public int getGrado(int i){
		chequearVertice(i, "el grado");
		return _vecinos.get(i).size();
	}
	
	// Retorna el conjunto de vecinos de un v�rtice
	public Set<Integer> getVecinos(int i){
		chequearVertice(i, "los vecinos");
		return _vecinos.get(i);
	}

	// Cantidad de v�rtices del grafo
	public int getVertices(){
		return _vecinos.size();
	}	

	// Verifica que un �ndice corresponda a un v�rtice v�lido
	private void chequearVertice(int i, String consulta){
		if( i < 0 || i >= getVertices())
			throw new IllegalArgumentException("Se intent� consultar " + consulta + " de un v�rtice inexistente! i = " + i);
	}

	// Verifica que los v�rtices puedan corresponder a una arista
	private void chequearArista(int i, int j, String accion){
		if( i < 0 || i >= getVertices() )
			throw new IllegalArgumentException("Se intent� " + accion + " una arista con un v�rtice inexistente! i = " + i);
		
		if( j < 0 || j >= getVertices() )
			throw new IllegalArgumentException("Se intent� " + accion + " una arista con un v�rtice inexistente! j = " + j);
		
		if( i == j )
			throw new IllegalArgumentException("Se intent� " + accion + " una arista con dos vertices iguales! i, j = " + i);
	}
}
