package datos;

import java.util.HashMap;
import logica.Tupla;

public class GestorPuntajes{

	private DataBaseInterface _db;
	
	/**
	 * Clase que permite obtener de la base de datos la lista de jugadores con su posicion y puntaje correspondiente.
	 */
	public GestorPuntajes(){
		_db = new DataBaseInterface("src/datos/db/basePuntos.sqlite");
		_db.createTable("puntajes", new String[]{"nombre", "pts"});
	}
	

	/**
	 * 
	 * @return Devuelve un HashMap que contiene como "llave" la posicion del jugador y 
	 * como valor una tupla con la secuencia ( 'nombre de jugador', 'puntaje' )
	 * 
	 */
	public HashMap<Integer, Tupla<String, Integer>> obtenerPuntuaciones(){
		DataBaseObject res = _db.select("puntajes", "pts", false);
		HashMap<Integer, Tupla<String, Integer>> ret = new HashMap<Integer, Tupla<String, Integer>>();
		
		Integer i = 1;
		for(HashMap<String, String> fila:res.getObject()){
			String nombre = fila.get("nombre");
			Integer puntos = new Integer(fila.get("pts"));
			ret.put(i, new Tupla<String, Integer>(nombre, puntos));
			i++;
		} return ret;
	}

	/**
	 * 
	 * @param nombre Nombre del jugador a insertar en la base de datos
 	 * @param puntos Puntaje obtenido para insertar en la base de datos
 	 * 
	 */
	public void insertarPuntaje(String nombre, Integer puntos){
		String[] data = new String[]{nombre, puntos.toString()};
		_db.insert("puntajes", data);
	}

}