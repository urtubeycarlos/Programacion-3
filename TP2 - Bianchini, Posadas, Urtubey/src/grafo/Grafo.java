package grafo;

import java.util.Set;

public interface Grafo<E> {

	public boolean agregarVertice(E e);
	public Set<E> getVertices();
	public int cantVertices();
	public boolean agregarArista(E e1, E e2);
	public boolean eliminarArista(E e1, E e2);
	public boolean existeArista(E e1, E e2);
	public Set<E> getVecinos(E e);
	public int getGrado(E e);
	
}
