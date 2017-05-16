public interface Mapa {
	
	public boolean agregarCoordenada(Coordenada c);
	public boolean agregarCoordenadas(Iterable<Coordenada> coordenadas);
	public boolean agregarRuta(Coordenada c1, Coordenada c2, boolean tienePeaje); //Unidireccional
	public List<Coordenada> getCoordenadas();
	public Integer cantPeajes();
	public List<Coordenada> obtenerRutaOptima(Coordenada origen, Coordenada destino); //sin tener en cuenta los peajes.
	public List<Coordenada> obtenerRutaOptima(Coordenada origen, Coordenada destino, int cantPeajesMax);

}