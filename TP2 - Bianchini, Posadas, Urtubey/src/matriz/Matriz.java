package matriz;

public interface Matriz<K, V> {

	public V get(K key1, K key2);
	public boolean set(K key1, K key2, V value);
	public K rowOf(V e);
	public K columnOf(V e);
	
}
