package mapa;

import java.util.ArrayList;
import java.util.List;

import grafo.GrafoPesado;

public class Mapa {

	private GrafoPesado _grafoDistanciasMapa;
	private GrafoPesado _grafoPeajesMapa;
	private ArrayList<Coordenada> _coordenadas;
	
	public Mapa(List<Coordenada> listaCoordenadas){

		_grafoDistanciasMapa = new GrafoPesado(listaCoordenadas.size());
		_grafoPeajesMapa = new GrafoPesado(listaCoordenadas.size());
		for(int i=0; i<listaCoordenadas.size(); i++){
			_coordenadas.add(listaCoordenadas.get(i));
		}
	
	}
	
	public Iterable<Coordenada> getCoordenadas(){
		return _coordenadas;
	}
	
	public void agregarRuta(Coordenada c1, Coordenada c2){
		agregarRuta(c1, c2, 0);
	}
	
	public void agregarRuta(Coordenada c1, Coordenada c2, int cantPeajes){
		chequearCoordenada(c1, "agregar una ruta");
		chequearCoordenada(c2, "agregar una ruta");
		int iCoord1 = _coordenadas.indexOf(c1);
		int iCoord2 = _coordenadas.indexOf(c2);
		_grafoDistanciasMapa.agregarArista(iCoord1, iCoord2, calcularDistancia(c1, c2));
		_grafoPeajesMapa.agregarArista(iCoord1, iCoord2, cantPeajes);
	}


	//TODO: Calcular distancia en plano R2
	public static double calcularDistancia(Coordenada c1, Coordenada c2){
//		c1.set_latitud(Math.toRadians(c1.getLatitud()));
//		c1.set_longitud(Math.toRadians(c1.getLongitud()));
//		
//		c2.set_latitud(Math.toRadians(c2.getLatitud()));
//		c2.set_longitud(Math.toRadians(c2.getLongitud()));
		
		double radio = 6378.137;
		double distLong = c2.getLongitudEnRadianes() - c1.getLongitudEnRadianes();
		double distanciaCoord = Math.acos(Math.sin(c1.getLatitudEnRadianes() * Math.sin(c2.getLatitudEnRadianes() + Math.cos(c1.getLatitudEnRadianes()) * Math.cos(c2.getLatitudEnRadianes() * Math.cos(distLong))))) * radio;
		
		//Sin adaptacion

//			lat1 = Math.toRadians(lat1);
//			long1 = Math.toRadians(long1);
//			lat2 = Math.toRadians(lat2);
//			long2 = Math.toRadians(long2);
//			
//			double radio = 6378.137;
//			double distLong = long2 - long1;
//			double distanciaCoord = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(distLong)) * radio;
//			
//			//Metros. distanciaCoord * 0.621371192
		
		return distanciaCoord;
	}

	private void chequearCoordenada(Coordenada c, String accion){
		if( !_coordenadas.contains(c) )
			throw new IllegalArgumentException("Se intento " + accion + " con una coordenada inexistente");
	}
	
}
