package datos;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import logica.Tupla;

//FIXME: Arreglar los metodos usando la API nueva de Juan
public class GestorPuntajes{

	private DataBaseInterface _db;
	
	public GestorPuntajes(){
		_db = new DataBaseInterface("src/datos/db/basePuntos.sqlite");
	}
	
	public HashMap<Integer, Tupla<String, Integer>> obtenerPuntuaciones(){
		DataBaseObject res = _db.select("puntajes", "pts", true);
		HashMap<Integer, Tupla<String, Integer>> ret = new HashMap<Integer, Tupla<String, Integer>>();
		
		Integer i = 1;
		for(HashMap<String, String> fila:res.getObject()){
			String nombre = fila.get("nombre");
			Integer puntos = new Integer(fila.get("pts"));
			ret.put(i, new Tupla<String, Integer>(nombre, puntos));
			i++;
		} return ret;
	}

	public void insertarPuntaje(String nombre, Integer puntos){
		String[] data = new String[]{nombre, puntos.toString()};
		_db.insert("puntajes", data);
	}
	
	
	public static void main(String[] args){
		GestorPuntajes gp = new GestorPuntajes();
		gp.insertarPuntaje("Pedro", 25);
//		String s1 = "2";
//		String s2 = "2";
//		System.out.println(s1.compareTo(s2));
	}
	
}