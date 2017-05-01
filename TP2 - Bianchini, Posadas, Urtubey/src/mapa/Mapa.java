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
	private int calcularDistancia(Coordenada c1, Coordenada c2){
		return 0;
	}

	private void chequearCoordenada(Coordenada c, String accion){
		if( !_coordenadas.contains(c) )
			throw new IllegalArgumentException("Se intento " + accion + " con una coordenada inexistente");
	}
	
}
