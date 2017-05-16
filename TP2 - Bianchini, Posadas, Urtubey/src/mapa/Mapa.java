package mapa;

import java.util.List;

public interface Mapa {
	
	public void agregarCoordenada(Coordenada c);
	public void agregarCoordenadas(Iterable<Coordenada> coordenadas);
	public void agregarRuta(Coordenada c1, Coordenada c2, boolean tienePeaje); //Unidireccional
	public boolean existeRuta(Coordenada c1, Coordenada c2);
	public List<Coordenada> getCoordenadas();
	public Integer cantPeajes();
	public List<Coordenada> obtenerRutaOptima(Coordenada origen, Coordenada destino); //sin tener en cuenta los peajes.
	public List<Coordenada> obtenerRutaOptima(Coordenada origen, Coordenada destino, int cantPeajesMax);

}