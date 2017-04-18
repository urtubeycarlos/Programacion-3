package datos;


import java.sql.*;
import java.util.ArrayList;
import logica.Tupla;

//FIXME: Arreglar los metodos usando la API nueva de Juan
public class GestorPuntajes{

	private DataBaseInterface _db;
	
	public GestorPuntajes() throws Exception{
		_db = new DataBaseInterface("org.sqlite.JDBC", "src/Datos/db/puntos.db");
		_db._openConnection();
	}
	
	public ArrayList<Tupla<String, Integer>> obtenerPuntajes() throws SQLException{
		ArrayList<Tupla<String, Integer>> ret = new ArrayList<Tupla<String, Integer>>();
		ResultSet rs = _db.selectQuery("SELECT * FROM puntajes ORDER BY pos;");
		while(rs.next())
			ret.add(new Tupla<String, Integer>(rs.getString("nombre"), rs.getInt("pts")));
		return ret;
	}

	public void insertarPuntaje(String nombre, Integer puntos) throws SQLException{
		ResultSet rs = _db.selectQuery("SELECT pos FROM puntajes WHERE " + puntos + ">=pts;");
		int pos_obtenida;
		if(rs.isClosed()){
			ResultSet conteo_filas = _db.selectQuery("SELECT COUNT(*) AS rowcount FROM puntajes");
			pos_obtenida = conteo_filas.getInt("rowcount") + 1;
		} else {
			pos_obtenida = rs.getInt("pos");
		}
		_db.updateQuery("UPDATE puntajes SET pos = pos + 1 WHERE pos>=" + pos_obtenida + ";");
		_db.insertQuery("INSERT INTO puntajes(pos, nombre, pts) VALUES(" + pos_obtenida + ", '" + nombre + "', " + puntos + ");");
		_db.deleteQuery("DELETE FROM puntajes WHERE pos>10;");
	}
	
	public void finalizarGestorPuntajes() throws SQLException{
		_db.closeConnection();
	}
}
