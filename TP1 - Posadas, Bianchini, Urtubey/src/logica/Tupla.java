package logica;

/**
 * @author Bianchini, Posadas, Urtubey
 * @param <T1> Tipo 1 para el primer elemento de la tupla
 * @param <T2> Tipo 2 para el primer elemento de la tupla
 * 
 * Representa una tupla, es decir una secuencia de dos elementos del tipo predifinido previamente.
 */
public class Tupla<T1, T2> {
	public final T1 elem1;
	public final T2 elem2;
	
	/**
	 * 
	 * @param elem1 Primer elemento de la tupla
	 * @param elem2 Segundo elemento de la tupla
	 * 
	 */
	public Tupla(T1 elem1, T2 elem2){
		this.elem1 = elem1;
		this.elem2 = elem2;
	}
	
	public String toString(){
		return "(" + elem1.toString() + ", " + elem2.toString()+")";
	}
}
