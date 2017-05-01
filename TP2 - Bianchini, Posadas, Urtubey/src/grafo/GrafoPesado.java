package grafo;
import java.util.ArrayList;

public class GrafoPesado extends Grafo {
	
	private static ArrayList<ArrayList<Integer>> _matrizDePesos;

	public GrafoPesado(int verticesIniciales) {
		super(verticesIniciales);
		
		_matrizDePesos = new ArrayList<ArrayList<Integer>>();
		for ( ArrayList<Integer> arrayList : _matrizDePesos){
			arrayList = new ArrayList<Integer>();
		}
	}
	
	@Override
	public void agregarArista(int i, int j){
		this.agregarArista(i, j, 0);
	}
	
	//no podemos agregar nada, esta vacia la matriz
	public void agregarArista(int i, int j, int peso){
		super.agregarArista(i, j);
		System.out.println(_matrizDePesos.toString());
		for (int lista=0; lista<this._matrizDePesos.size();i++){
			
		}
		//this._matrizDePesos.get(i).add(peso);
			
	}

}
