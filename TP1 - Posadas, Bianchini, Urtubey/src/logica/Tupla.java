package logica;

public class Tupla<T1, T2> {
	public final T1 elem1;
	public final T2 elem2;
	
	public Tupla(T1 elem1, T2 elem2){
		this.elem1 = elem1;
		this.elem2 = elem2;
	}
	
	public String toString(){
		return "(" + elem1.toString() + ", " + elem2.toString()+")";
	}
}
