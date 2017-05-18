package mapa;

import java.util.ArrayList;
import java.util.List;

import grafo.GrafoPesadoUnidireccional;
import matriz.MatrizCartesiana;

public class MapaRutas implements Mapa {

	private GrafoPesadoUnidireccional<Integer> _grafoCiudades;
	private MatrizCartesiana<Boolean> _matrizPeajes;
	ArrayList<Coordenada> _listaCoordenadas;

	public MapaRutas(){
		_grafoCiudades = new GrafoPesadoUnidireccional<Integer>();
		_matrizPeajes = new MatrizCartesiana<Boolean>(16, 16);
		_listaCoordenadas = new ArrayList<Coordenada>();
	}
	
	@Override
	public void agregarCoordenada(Coordenada c) {
		if( !_listaCoordenadas.contains(c) )
			_listaCoordenadas.add(c);
		_grafoCiudades.agregarVertice( _listaCoordenadas.indexOf(c) );
	}

	@Override
	public void agregarCoordenadas(Iterable<Coordenada> coordenadas) {
		for( Coordenada c:coordenadas )
			agregarCoordenada(c);
	}

	@Override
	public void agregarRuta(Coordenada c1, Coordenada c2, boolean tienePeaje) {
		Integer indC1 = _listaCoordenadas.indexOf(c1);
		Integer indC2 = _listaCoordenadas.indexOf(c2);
		
		if( indC1 > _matrizPeajes.width() || indC2 > _matrizPeajes.height() )
			_matrizPeajes.resize( _matrizPeajes.width()*2 , _matrizPeajes.height()*2);
		
		_grafoCiudades.agregarArista(indC1, indC2, calcularDistancia(c1, c2));
		_matrizPeajes.set(indC1, indC2, tienePeaje);
	}
	
	private double calcularDistancia(Coordenada c1, Coordenada c2){
		
//		double lat1 = c1.getLatitudEnRadianes();
//		double long1 = c1.getLongitudEnRadianes();
//		double lat2 = c2.getLatitudEnRadianes();
//		double long2 = c2.getLongitudEnRadianes();
//		
//		double radio = 6378.137;
//		double distLong = long2 - long1;
//		double distanciaCoord = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(distLong)) * radio;
//		
//		return (distanciaCoord * 0.621371192);
		return (Math.hypot(c2.getLatitud() - c2.getLatitud(), c2.getLongitud() - c2.getLongitud()) * 1e-5);
		
	}
	
	@Override
	public boolean existeRuta(Coordenada c1, Coordenada c2) {
		return _grafoCiudades.existeArista( _listaCoordenadas.indexOf(c1) , _listaCoordenadas.indexOf(c2) );
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Coordenada> getCoordenadas() {
		return ( List<Coordenada> ) _listaCoordenadas.clone();
	}

	@Override
	public Integer cantPeajes() {
		Integer cont = 0;
		for( Integer vertice:_grafoCiudades.getVertices() )
		for( Integer vecino:_grafoCiudades.getVecinos(vertice) )
			if( _matrizPeajes.get(vertice, vecino) )
				cont++;
		return cont;

	}

	@Override
	public List<Coordenada> obtenerRutaOptima(Coordenada origen, Coordenada destino) {
		List<Coordenada> ret = new ArrayList<Coordenada>();
		Integer indOrigen = _listaCoordenadas.indexOf(origen);
		Integer indDestino = _listaCoordenadas.indexOf(destino);
		List<Integer> res = _grafoCiudades.obtenerCaminoMinimo(indOrigen, indDestino);
		for(Integer indice:res)
			ret.add( _listaCoordenadas.get(indice) );
		return ret;
		
	}

	@Override
	public List<Coordenada> obtenerRutaOptima(Coordenada origen, Coordenada destino, int cantPeajesMax) {

		List<Coordenada> ret = new ArrayList<Coordenada>();
		ArrayList<Coordenada> referenciasCoordenadas = new ArrayList<Coordenada>();
		GrafoPesadoUnidireccional<Integer> grafoEnCapas = new GrafoPesadoUnidireccional<>();
		
		for( int i=0; i<=cantPeajesMax; i++ )
			referenciasCoordenadas.addAll( _listaCoordenadas );

		for( Integer vertice=0; vertice<referenciasCoordenadas.size(); vertice++ )
			grafoEnCapas.agregarVertice(vertice);
		
		
		for( int i=0; i<cantPeajesMax; i++ )
		for( int vertice:_grafoCiudades.getVertices() )
		for( int vecino:_grafoCiudades.getVecinos(vertice) )
			grafoEnCapas.agregarArista(vertice + i*_grafoCiudades.cantVertices(), vecino + i*_grafoCiudades.cantVertices(), _grafoCiudades.getPeso(vertice, vecino));

		
		for( int i=0; i<cantPeajesMax; i++ )
		for( int vertice:grafoEnCapas.getVertices() )
		for( int vecino:grafoEnCapas.getVecinos(vertice) ) 
				if( _matrizPeajes.get(vertice - i*_grafoCiudades.cantVertices(), vecino - i*_grafoCiudades.cantVertices()) ){
					grafoEnCapas.eliminarArista(vertice - i*_grafoCiudades.cantVertices(), vecino - i*_grafoCiudades.cantVertices());
					grafoEnCapas.agregarArista( vertice - i*_grafoCiudades.cantVertices() , vecino);
				}

		List<Integer> res = grafoEnCapas.obtenerCaminoMinimo( referenciasCoordenadas.indexOf(origen) , referenciasCoordenadas.indexOf(destino) + referenciasCoordenadas.indexOf(destino)*cantPeajesMax );
		
		for(Integer indice:res)
			ret.add( referenciasCoordenadas.get(indice) );
		return ret;
	}
	
}
