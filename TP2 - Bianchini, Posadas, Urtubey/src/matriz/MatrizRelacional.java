package matriz;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MatrizRelacional<K, V> implements Matriz<K, V>{

	HashMap<K, HashMap<K, V>> _m;
	
	public MatrizRelacional(){
		_m = new HashMap<K, HashMap<K, V>>();
	}

	@Override
	public V get(K key1, K key2) {
		checkKeys(key1, key2, "acceder a un valor");
		return _m.get(key1).get(key2);
	}

	@Override
	public boolean set(K key1, K key2, V value) {
		_m.putIfAbsent(key1, new HashMap<K, V>());
		return _m.get(key1).put(key2, value) != null;
	}
	
	public boolean remove(K key1, K key2){
		checkKeys(key1, key2, "eliminar un valor");
		if ( _m.get(key1).size() == 1 )
			return _m.remove(key1) != null;
		return _m.get(key1).remove(key2) != null;
	}

	@Override
	public K rowOf(V e) {
		for( K key:_m.keySet() )
			if( _m.get(key).containsValue(e) )
				return key;
		return null;
	}

	@Override
	public K columnOf(V e) {
		K row = rowOf(e);
		 if ( row != null ) for( K key:_m.get(row).keySet() )
			if( _m.get(row).get(key).equals(e) )
				return key;
		return null;
	}
	
	public Set<V> values(){
		Set<V> ret = new HashSet<V>();
		for(K key:_m.keySet())
			ret.addAll( _m.get(key).values() );
		return ret;
	}
	
	private void checkKeys(K key1, K key2, String accion){
		if ( !_m.keySet().contains(key1) )
			throw new IllegalArgumentException("Se intento " + accion + " con una key invalida. key1 = " + key1);
		if( !_m.get(key1).keySet().contains(key2) )
			throw new IllegalArgumentException("Se intento acceder a un valor con una key invalida. key2 = " + key2);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for( K key1:_m.keySet() ){
			sb.append(key1.toString() + ": { ");
			for( K key2:_m.get(key1).keySet() ){
				sb.append(key2).append(": ").append( _m.get(key1).get(key2).toString() ).append("; ");
			}	sb.append("}\n");
		}
		
		return sb.toString();
	}
	
}
