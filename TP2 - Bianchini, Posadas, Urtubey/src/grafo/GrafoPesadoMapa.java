package grafo;

public class GrafoPesadoMapa extends Grafo {
	
	private int[][] _matrizPesos;
	private int[][] _matrizPeajes;
	
	public GrafoPesadoMapa(int verticesIniciales) {
		super(verticesIniciales);
		//Se amortiza para una futura rendimension
		_matrizPesos = new int[verticesIniciales*4][verticesIniciales*4];
		_matrizPeajes = new int[verticesIniciales*4][verticesIniciales*4];
	}
	
	@Override
	public void agregarVertice(){
		super.agregarVertice();
		if( super.getVertices() >= _matrizPesos.length )
			redimensionarMatrices();
	}
	
	@Override
	public void agregarArista(int i, int j){
		this.agregarArista(i, j, 0, 0);
	}
	
	public void agregarArista(int i, int j, int peso, int cantPeajes){
		super.agregarArista(i, j);
		_matrizPesos[i][j] = peso;
		_matrizPeajes[i][j] = cantPeajes;
	}

	private void redimensionarMatrices(){
		// Complejidad O(n^n) amortizado
		int dimNueva = _matrizPesos.length*4;
		int[][] nuevaMatrizPesos = new int[dimNueva][dimNueva];
		int[][] nuevaMatrizPeajes = new int[dimNueva][dimNueva];
		
		for(int i=0; i<_matrizPesos.length; i++){
			for(int j=0; j<_matrizPesos.length; i++){
				nuevaMatrizPesos[i][j] = _matrizPesos[i][j];
				nuevaMatrizPeajes[i][j] = _matrizPeajes[i][j];
			}
		}	
		
		_matrizPesos = nuevaMatrizPesos;
		_matrizPeajes = nuevaMatrizPeajes;
		
	}

	public boolean tienePeaje(int i, int j){
		chequearArista(i, j, "consultar el peaje");
		return _matrizPeajes[i][j] != 0;
	}
	
	public int cantPeajes(int i, int j){
		chequearArista(i, j, "consultar la cantidad de peajes");
		return _matrizPeajes[i][j];
	}
	
	public int getPesoArista(int i, int j){
		chequearArista(i, j, "consultar el peso");
		return _matrizPesos[i][j];
	}
	
}