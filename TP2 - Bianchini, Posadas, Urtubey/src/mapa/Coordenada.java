package mapa;

public class Coordenada {

	private String _nombre;
	private double _latitud;
	private double _longitud;
	
	public Coordenada(){
		_latitud = 0;
		_longitud = 0;
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
	
	public void setLatitudLongitud(double lat, double lon){
		_latitud = lat;
		_longitud = lon;
	}
	
	@Override
	public String toString(){
		return "Coord: { Nombre: " + _nombre +"; Latitud: " + _latitud + "; Longitud: " + _longitud + " }";
	}
	
	@Override
	public boolean equals(Object o){
		if ( !(o instanceof Coordenada) )
			throw new IllegalArgumentException("El parametro ingresado no es una Coordenada");
		return o.toString().equals(this.toString());
	}
	
}
