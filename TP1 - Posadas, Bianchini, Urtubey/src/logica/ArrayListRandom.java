package logica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * @author alumnos
 * @param <E> Tipo de elemento del cual sera el ArrayList
 * 
 * Un ArrayList tipico con la posibilidad de extraer un elemento aleatorio de la lista.
 */

public class ArrayListRandom<E> extends ArrayList<E> {

	private static final long serialVersionUID = 4053419480482339418L;
	private Random _random;
	
	public ArrayListRandom() {
		super();
		initializeRandom();
	}

	public ArrayListRandom(int arg0) {
		super(arg0);
		initializeRandom();
	}

	public ArrayListRandom(Collection<? extends E> arg0) {
		super(arg0);
		initializeRandom();
	}

	private void initializeRandom(){
		_random = new Random();
	}

	/**
	 * 
	 * @return Elemento de tipo <E> de forma aleatoria.
	 * 
	 */
	public E removeRandom(){
		int randomIndex = _random.nextInt( size() );
		return remove(randomIndex);
	}
	
}
