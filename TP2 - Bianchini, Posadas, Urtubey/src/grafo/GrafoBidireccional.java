package grafo;

public class GrafoBidireccional<E> extends GrafoUnidireccional<E>{

	@Override
	public boolean agregarArista(E e1, E e2){
		return super.agregarArista(e1, e2) && super.agregarArista(e2, e1);
	}
	
	@Override
	public boolean eliminarArista(E e1, E e2){
		return super.eliminarArista(e1, e2) && super.eliminarArista(e2, e1);
	}
	
}
