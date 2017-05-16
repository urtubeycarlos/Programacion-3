package matriz;

import java.util.ArrayList;

public class MatrizCartesiana<E> implements Matriz<Integer, E>{

	private ArrayList<ArrayList<E>> _m;
	
	public MatrizCartesiana(int ancho, int alto){
		_m = initialize(ancho, alto);
	}
	
	@Override
	public E get(Integer row, Integer column) {
		checkCoordinate(row, column);
		return _m.get(row).get(column);
	}

	@Override
	public boolean set(Integer row, Integer column, E e) {
		checkCoordinate(row, column);
		return _m.get(row).set(column, e) != null;
	}

	@Override
	public Integer rowOf(E e) {
		for( int i=0; i<_m.size(); i++ )
			if ( _m.get(i).contains(e) )
				return i;
		return -1;
	}

	@Override
	public Integer columnOf(E e) {
		int row = rowOf(e);
		if ( row != -1 )
			return _m.get(row).indexOf(e);
		return -1;
	}
	
	public Integer width(){
		return _m.size();
	}
	
	public Integer height(){
		return _m.get(0).size();
	}
	
	public Integer size(){
		return width()*height();
	}
	
	public void resize(int width, int height){
		
		ArrayList<ArrayList<E>> nueva_matriz = initialize(width, height);
		
		for(int i=0; i<_m.size(); i++)
		for(int j=0; j<_m.get(i).size(); j++)
			nueva_matriz.get(i).set(j, _m.get(i).get(j));
		
		_m = nueva_matriz;
		
	}
	
	private ArrayList<ArrayList<E>> initialize(int width, int height){
		
		ArrayList<ArrayList<E>> matriz = new ArrayList<ArrayList<E>>();
		for(int i=0; i<width; i++){
			ArrayList<E> aux = new ArrayList<E>();
			for(int j=0; j<height; j++){
				aux.add(null);
			}
			
			matriz.add(aux);
		}
		
		return matriz;
	}
	
	private void checkCoordinate(int row, int column){
		if( row<0 || row>_m.size() )
			throw new IllegalArgumentException("Se intento acceder a una posicion invalida de la matriz. row = " + row);
		if( column<0 || column>_m.get(0).size() )
			throw new IllegalArgumentException("Se intento acceder a una posicion invalida de la matriz = " + column);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Matriz:\n");
		for(ArrayList<E> a:_m)
			sb.append( a.toString() ).append("\n");
		return sb.toString();
	}

	
}
