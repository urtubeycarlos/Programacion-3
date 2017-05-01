package mapa;

import java.util.ArrayList;
import java.util.List;

import grafo.GrafoPesadoMapa;

public class Mapa {

	private GrafoPesadoMapa _grafoMapa;
	private ArrayList<Coordenada> _coordenadas;
	
	public Mapa(List<Coordenada> listaCoordenadas){

		_grafoMapa = new GrafoPesadoMapa(listaCoordenadas.size());
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
		_grafoMapa.agregarArista(_coordenadas.indexOf(c1), _coordenadas.indexOf(c2), obtenerDistancia(c1, c2), cantPeajes);
	}


	//TODO: Calcular distancia en plano 2D
	private int obtenerDistancia(Coordenada c1, Coordenada c2){
		return 0;
	}

	private void chequearCoordenada(Coordenada c, String accion){
		if( !_coordenadas.contains(c) )
			throw new IllegalArgumentException("Se intento " + accion + " con una coordenada inexistente");
	}
	
}
