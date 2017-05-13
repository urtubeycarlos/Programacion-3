package mapa;

import java.io.Serializable;

public class Coordenada implements Serializable {

	private static final long serialVersionUID = 1L;
	private String _nombre;
	private double _latitud;
	private double _longitud;
	
	public Coordenada(){
		_nombre = "";
		_latitud = 0.0;
		_longitud = 0.0;
	}
	
	public Coordenada(String n, double lat, double lon){
		_nombre = n;
		_latitud = lat;
		_longitud = lon;
	}
	
	public String getNombre(){
		return _nombre;
	}
	
	public double getLatitud(){
		return _latitud;
	}
	
	public double getLongitud(){
		return _longitud;
	}
	
	public double getLatitudEnRadianes(){
		return Math.toRadians(_latitud);
	}
	
	public double getLongitudEnRadianes(){
		return Math.toRadians(_longitud);
	}
	

	public void setData(double latitud, double longitud){
		setData("", latitud, longitud);
	}
	
	public void setData(String nombre_nuevo, double latitud, double longitud){
		_nombre = ( nombre_nuevo.equals("") )? _nombre:nombre_nuevo ;
		_latitud = latitud;
		_longitud = longitud;
	}
	
	public Coordenada clonar(){
		return new Coordenada(_nombre, _latitud, _longitud);
	}
	
	@Override
	public String toString(){
		return "Coord: { Nombre: " + _nombre +"; Latitud: " + _latitud + "; Longitud: " + _longitud + " }";
	}
	
	@Override
	public boolean equals(Object o){
		if ( !(o instanceof Coordenada) )
			throw new IllegalArgumentException("El parametro ingresado no es una Coordenada");
		Coordenada coord2 = (Coordenada) o;
		return _latitud == coord2.getLatitud() && _longitud == coord2.getLongitud();
	}
	
}
