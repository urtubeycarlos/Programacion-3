
package datos;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class DataBaseInterface {
	
	String dataBaseName;
	Connection connection;
	Statement stmt;
	
	private final static Logger _logger = Logger.getLogger(DataBaseInterface.class.getName());
	private static FileHandler fileHandler = null;
		
	/**
	 * Constructor de la interfaz con la base de datos
	 * @param dataBaseDriver String que contiene el nombre del driver de SQLite que hace funcionar la conexion. Suele ser
	 * siempre "org.sqlite.JDBC" pero puede depender del driver.
	 * @param dataBaseName String que contiene la ruta completa hacia la base. "/nombreDeBase.sqlite" creara la base en el directorio
	 * raiz de la unidad desde donde se ejecuta la aplicacion. A partir de esa ruta, puede especificarse una ruta diferente, pero los
	 * directorios deben existir.
	 *@Ejemplo
	 *new DataBaseInterface("/database/test.sqlite") 
	 *
	 */
	public DataBaseInterface(String dataBaseName) {
		initLogger();
		
		this.dataBaseName = dataBaseName;

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			_logger.log(Level.INFO, e.getMessage());
		}
		
	}
	
	/**
	 * Este metodo inicializa un logger en el cual se escribiran los mensajes de error provenientes de excepciones que devuelve
	 * el driver de la base.
	 */
	public static void initLogger(){
		try {
			fileHandler = new FileHandler("log.log", true);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}

    	fileHandler.setFormatter(new SimpleFormatter());
    	_logger.addHandler(fileHandler);
		_logger.setLevel(Level.INFO);
    }

	private void _openConnection() throws SQLException {
		this.connection = DriverManager.getConnection("jdbc:sqlite:"+ this.dataBaseName);
		this.stmt = this.connection.createStatement();
	}

	private void _closeConnection() throws SQLException {
		this.stmt.close();
		this.connection.close();
	}

	private ResultSet _query(String sql) throws SQLException{
		this._openConnection();
		ResultSet query = this.stmt.executeQuery(sql);
		return query;
	}
	
	private void _update(String sql) throws SQLException{
		this._openConnection();
		this.stmt.executeUpdate(sql);
		this._closeConnection();
	}
	
	/**
	 * Este metodo auxiliar privado construye un dataBaseObject a partir de un query
	 * @param sql String con sentencia sql
	 */
	private DataBaseObject _buildSearch(String sql) throws SQLException {
		ResultSet query = this._query(sql);
		
		DataBaseObject search = new DataBaseObject();
		
		while (query.next()){
			HashMap<String, String> row = new HashMap<>();
			for (int column=1; column<query.getMetaData().getColumnCount()+1; column++){
				row.put(query.getMetaData().getColumnName(column), query.getString(query.getMetaData().getColumnName(column)));
			}
			search.addData(row);
		}
		query.close();
		return search;
	}
	
	
	/**
	 * Este metodo devuelve un ArrayList con los nombres de las tablas de la base. (Se usa en test para borrar todas las tablas)
	 */
	public ArrayList<String> getTables() {
		try {
			this._openConnection();
			ResultSet tables = this.connection.getMetaData().getTables(null, null, "%", null);
			
			ArrayList<String> table_names = new ArrayList<>();
			while (tables.next())
				table_names.add(tables.getString(3));
			
			tables.close();
			
			return table_names;
			
		} catch (SQLException e) {
			_logger.log(Level.INFO, e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * Este metodo crea una tabla en la base con el nombre y las columnas pasadas como parametro
	 * @param tableName String con el nombre de la tabla
	 * @param columns String[] con los nombres de las columnas (no puede estar vacio)
	 * @Ejemplo
	 * createTable("COMPANIA", new String[]{"nombre", "edad", "posicion"});
	 */
	public void createTable(String tableName, String[] columns){
		try{
			StringBuilder columnsString = new StringBuilder();
			for (String column : columns){
				columnsString.append(column+" text, ");
			}
			
			String columnsSubString = columnsString.substring(0, columnsString.length()-2);
			String sql = "CREATE TABLE IF NOT EXISTS "+tableName+" ("+columnsSubString+");";
			
			this._update(sql);
		}
		catch( Exception e ){
			_logger.log(Level.INFO, e.getMessage());
		}
				
	}
	
	/**
	 * Este metodo borra una tabla de la base
	 * @param tableName String con el nombre de la tabla
	 */
	public void dropTable(String tableName){
		try{
			String sql = "drop table "+ tableName;

			this._update(sql);
		}
		catch (Exception e) {
			_logger.log(Level.INFO, e.getMessage());
		}
	}
	
	/**
	 * Este metodo inserta un registro en la tabla especificada. La tabla debe existir y se debe conocer el nombre y orden de las columnas
	 * @param tableName String con el nombre de la tabla
	 * @param data String[] con los valores de cada columna en orden.
	 * @Ejemplo 
	 * para la tabla COMPANIA con columnas nombre|edad|posicion
	 * insert("COMPANIA", new String[] {"juan", "25", "empleado"});
	 * 
	 * Si se desea colocar un dato en blanco se escribe ""
	 */
	public void insert(String tableName, String[] data){
		try{
			StringBuilder columnNames = new StringBuilder();
			StringBuilder columnValues = new StringBuilder();
			
			String sql = "pragma table_info("+tableName+");";
			ResultSet tableStructure = this._query(sql);
			
			while(tableStructure.next()){
				System.out.println(tableStructure.getString(2));
				columnNames.append(tableStructure.getString(2)+", ");
			}
			tableStructure.close();
			
			for (String column: data){
				columnValues.append("'"+column+"', ");
			}
			
			String columnNamesSubstring = columnNames.substring(0, columnNames.length()-2);
			String columnValuesSubstring = columnValues.substring(0, columnValues.length()-2);
			
			sql = "insert into "+tableName+" ("+columnNamesSubstring+") VALUES ("+columnValuesSubstring+");";
			
			this._update(sql);
		}
		catch (Exception e){
			_logger.log(Level.INFO, e.getMessage());
		}
	}
		
	/**
	 * Este metodo borra los registros que cumplan con la condicion establecida
	 * @param tableName String con Nombre de la tabla
	 * @param domain String[][] con los criterios de busqueda
	 * @Ejemplo
	 * delete("SCORE", new String[][] {{"name","=","juan"}})
	 */
	public void delete(String tableName, String[][] domain){
		try {
			StringBuilder domainString = new StringBuilder();
			for (String[] condition : domain){
				domainString.append(condition[0]+condition[1]+"'"+condition[2]+"' AND ");				
			}
			String domainSubString = domainString.substring(0, domainString.length()-5);

			String sql = "delete from "+tableName+" where "+domainSubString+";";
			
			this._update(sql);
		} 
		catch( Exception e ){
			_logger.log(Level.INFO, e.getMessage());
		}
		
	}
	
	/**
	 * Este metodo devuelve un dataBaseObject con toda la informacion de la tabla
	 * @param tableName String con el nombre de la tabla
	 */
	public DataBaseObject select(String tableName){
		try {
			String sql = "SELECT * FROM "+  tableName + ";";
			return _buildSearch(sql);
		}
		catch( Exception e ){
			_logger.log(Level.INFO, e.getMessage());
		}
		return null;
		
	}
	
	public DataBaseObject select(String tableName, String orderByColum, boolean descendent){
		try {
			String sql = "SELECT * FROM "+  tableName + " ORDER BY " + orderByColum + ((descendent)?" DESC":"") + ";";
			return _buildSearch(sql);
		}
		catch( Exception e ){
			_logger.log(Level.INFO, e.getMessage());
		}
		return null;
		
	}
	
	/**
	 * Este metodo devuelve un dataBaseObject con aquellos registros de la tabla que coinciden con el criterio de busqueda
	 * @param tableName String con el nombre de la tabla
	 * @param domain String[][] (array de arrays) con tuplas "campo","operador","valor". No puede estar vacio.
	 * @Ejemplo
	 * search("SCORE", new String[][]{{"name","=","juan"},{"score",">","100"}});
	 */
	public DataBaseObject select(String tableName, String[][] domain){
		try{
			StringBuilder domainString = new StringBuilder();
			for (String[] condition : domain){
				domainString.append(condition[0]+condition[1]+"'"+condition[2]+"' AND ");				
			}
			String domainSubString = domainString.substring(0, domainString.length()-5);

			String sql = "SELECT * FROM "+ tableName + " WHERE "+ domainSubString + ";";
			
			return _buildSearch(sql);
		}
		catch( Exception e ){
			_logger.log(Level.INFO, e.getMessage());
		}
		return null;
	}
	
	public Integer getRowCount() throws SQLException{
		ResultSet query = _query("SELECT COUNT(*) AS rowcount FROM puntajes");
		return query.getInt("rowcount");
	}
}
