package mapa;

import java.io.LineNumberInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import grafo.GrafoPesadoUnidireccional;
import matriz.MatrizCartesiana;

public class MapaRutas implements Mapa {

	private GrafoPesadoUnidireccional<Integer> _grafoCiudades;
	private MatrizCartesiana<Boolean> _matrizPeajes;
	ArrayList<Coordenada> _listaCoordenadas;

	public MapaRutas(){
		_grafoCiudades = new GrafoPesadoUnidireccional<Integer>();
		_matrizPeajes = new MatrizCartesiana<Boolean>(1, 1);
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
		
		if( indC1 >= _matrizPeajes.width() || indC2 >= _matrizPeajes.height() )
			_matrizPeajes.resize( _matrizPeajes.width()+1 , _matrizPeajes.height()+1);
		
		_grafoCiudades.agregarArista(indC1, indC2, calcularDistancia(c1, c2));
		_matrizPeajes.set(indC1, indC2, tienePeaje);
	}
	
	private double calcularDistancia(Coordenada c1, Coordenada c2){
		return Math.sqrt( Math.pow(c2.getLatitud() - c1.getLatitud(), 2) + Math.pow(c2.getLongitud() - c1.getLongitud(), 2.0) );
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
	public List<Coordenada> obtenerRutaOptima(Coordenada origen, Coordenada destino, int cantPeajesMax) {

		if( cantPeajesMax > cantPeajes() )
			return obtenerRutaOptima(origen, destino);
		
		if( cantPeajesMax == 0 )
			return obtenerRutaOptimaSinPeajes(origen, destino);
		
		List<Coordenada> ret = new ArrayList<Coordenada>();
		Set<Integer> destinos = new HashSet<Integer>();
		ArrayList<Coordenada> referenciasCoordenadas = new ArrayList<Coordenada>();
		GrafoPesadoUnidireccional<Integer> grafoEnCapas = new GrafoPesadoUnidireccional<>();
	
		referenciasCoordenadas.addAll( _listaCoordenadas ); //Se guarda las referencias a los vertices una vez.
		for( int c=0; c<cantPeajesMax; c++ ){ //Se guardan n referencias a los vertices de nuevo, con n la cantidad de peajes maxima que quiero.
											 //Tambien calcula los destinos.
			referenciasCoordenadas.addAll( _listaCoordenadas );
			destinos.add( _listaCoordenadas.indexOf(destino) + c*_grafoCiudades.cantVertices());
		}

		//Agrega los vertices al grafo en capas
		for( Integer vertice=0; vertice<referenciasCoordenadas.size(); vertice++ )
			grafoEnCapas.agregarVertice(vertice);
		
		
		
		//Setea las aristas del grafo en capas segun la cantidad maxima de peajes.
		for(int c=0; c<cantPeajesMax; c++){
			
			for(Integer vertice:_grafoCiudades.getVertices())
			for(Integer vecino:_grafoCiudades.getVecinos(vertice))
				if(_grafoCiudades.existeArista(vertice, vecino))
					if( !_matrizPeajes.get(vertice, vecino) ) //Si no tiene peaje...
						grafoEnCapas.agregarArista( _grafoCiudades.cantVertices() * c + vertice, _grafoCiudades.cantVertices() * c + vecino, _grafoCiudades.getPeso(vertice, vecino) );
						//Agrega una arista entre el vertice y su vecino en la misma capa.
					else if ( c < cantPeajesMax ) //Si tiene y no estamos en la ultima capa...
						grafoEnCapas.agregarArista( _grafoCiudades.cantVertices() * c + vertice, _grafoCiudades.cantVertices() * ( c+1 ) + vecino, _grafoCiudades.getPeso(vertice, vecino) );
						//Agrega una arista entre el vertice y su vecino en la misma capa.
			
		}
		
		
		//Busca camino minimo con todos los posibles destino. Esta con un try porque puede 
		//ser que no se pueda llegar y devuelva una excepcion.
		List<ArrayList<Integer>> resultados = new ArrayList<ArrayList<Integer>>();
		for( Integer dest:destinos ){
			try {
				resultados.add( (ArrayList<Integer>) grafoEnCapas.obtenerCaminoMinimo( referenciasCoordenadas.indexOf(origen) , dest) );
			} catch (Exception e){
				continue;
			}
		}
			
		//De los mejores caminos con todos los posibles destinos se busca cual es el mejor.
		for( Integer indice:obtenerMejorResultado(resultados, grafoEnCapas) )
			ret.add( referenciasCoordenadas.get(indice) );
		return ret;
		
	}
	
	/**
	 * Devuelve una ruta optima sin tener en cuenta los peajes, 
	 */
	@Override
	public List<Coordenada> obtenerRutaOptima(Coordenada origen, Coordenada destino) {
		
		List<Coordenada> ret = new ArrayList<Coordenada>();
		
		//Obtiene un camino minimo sin tener en cuenta los peajes llamando al metodo obtenerCaminoMinimo del grafo original.
		//El resultado es una lista de Integers que corresponde al numero de cada vertice del grafo.
		List<Integer> res = _grafoCiudades.obtenerCaminoMinimo( _listaCoordenadas.indexOf(origen), _listaCoordenadas.indexOf(destino));
		for(Integer indice:res) //Convierte la lista de Integers a una lista de Coordenadas.
			ret.add( _listaCoordenadas.get(indice) );
		return ret;
		
	}
	
	private List<Coordenada> obtenerRutaOptimaSinPeajes(Coordenada origen, Coordenada destino){
		
		GrafoPesadoUnidireccional<Integer> copiaGrafo = _grafoCiudades.clonar();
		List<Integer> resultado;
		List<Coordenada> ret = new ArrayList<Coordenada>();
		
		HashMap<Integer, Integer> vecinosABorrar = new HashMap<>();
		for( Integer vertice:copiaGrafo.getVertices() ) //posible problema: iterando sobre lista y eliminando objetos de ella
		for( Integer vecino:copiaGrafo.getVecinos(vertice) )
			if( _matrizPeajes.get(vertice, vecino) )
				vecinosABorrar.put(vertice, vecino);
				//copiaGrafo.eliminarArista(vertice, vecino);
		
		for (Entry<Integer, Integer> entry : vecinosABorrar.entrySet())
		{
		    copiaGrafo.eliminarArista(entry.getKey(), entry.getValue());
		}
		
		try {
			resultado = copiaGrafo.obtenerCaminoMinimo( _listaCoordenadas.indexOf(origen), _listaCoordenadas.indexOf(destino) );
		} catch (Exception e) {
			throw new IllegalArgumentException("No se puede llegar a destino sin pasar por ningun peaje. destino = " + destino);
		}
		
		for(Integer indice:resultado)
			ret.add( _listaCoordenadas.get(indice) );
		return ret;
		
	}
	
	private ArrayList<Integer> obtenerMejorResultado(List<ArrayList<Integer>> resultados, GrafoPesadoUnidireccional<Integer> grafo_pesos){
		
		ArrayList<Integer> ret = null;
		Double mejor_peso = null;
		for( ArrayList<Integer> resultado:resultados ){
			double acum = 0.0;
			for( int i=0; i<resultado.size()-1; i++ ){
				acum += grafo_pesos.getPeso( resultado.get(i) , resultado.get(i+1) );
			}
			
			if( mejor_peso == null ){
				mejor_peso = acum;
				ret = resultado;
			} else if( ( acum < mejor_peso ) || ( acum == mejor_peso && resultado.size() < ret.size() ) ){
				mejor_peso = acum;
				ret = resultado;
			}
				
		} return ret;
		
	}
	
}
