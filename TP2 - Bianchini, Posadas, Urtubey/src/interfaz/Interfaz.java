package interfaz;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;

import mapa.Coordenada;
import mapa.MapaRutas;

public class Interfaz {
	
	boolean peaje = false;
	
	// Trabajamos con coordinates para que sea mas agil la imple del jmap.
	MapaRutas mapa = new MapaRutas();
	Coordinate puntoInicio;
	Coordinate puntoDestino;
	
	//Metodos
		boolean obtenerCaminoMinimo(JMapViewer map, JTextField campoPeajes, JCheckBox chckbxPeaje) throws Exception{
			try {
				pasarDatosAlMapa(map);
				ArrayList<Coordenada> caminoMinimo = obtenerCamino(campoPeajes);
				dibujarCaminoMinimo(caminoMinimo, chckbxPeaje, map);
				return true;
			} catch (Exception e) {
				throw new Exception(e);
			}
		}
		
		private void pasarDatosAlMapa(JMapViewer map) {
			for (MapPolygon polygon: map.getMapPolygonList()){
				Coordenada coord1 = new Coordenada(null, polygon.getPoints().get(0).getLat(), polygon.getPoints().get(0).getLon());
				Coordenada coord2 = new Coordenada(null, polygon.getPoints().get(1).getLat(), polygon.getPoints().get(1).getLon());
				
				boolean conPeaje = checkPolygonName(polygon);
				
				mapa.agregarCoordenada(coord1);
				mapa.agregarCoordenada(coord2);
				
				mapa.agregarRuta(coord1, coord2, conPeaje);
				mapa.agregarRuta(coord2, coord1, conPeaje);
			}
		}
		
		private ArrayList<Coordenada> obtenerCamino(JTextField campoPeajes) throws Exception {
			if (!(this.puntoInicio!=null && this.puntoDestino!=null)){
				throw new Exception("Debe seleccionar puntos de origen y destino.");
			}
			Coordenada origen = new Coordenada("Inicio", this.puntoInicio.getLat(), this.puntoInicio.getLon()); 
			Coordenada destino = new Coordenada("Destino", this.puntoDestino.getLat(), this.puntoDestino.getLon()); 
			
			int cantPeajes = 0;
			if (!campoPeajes.getText().equals(""))
				cantPeajes = Integer.parseInt(campoPeajes.getText());
		
			ArrayList<Coordenada> caminoMinimo = (ArrayList<Coordenada>) mapa.obtenerRutaOptima(origen, destino, cantPeajes);
			return caminoMinimo;
		}

		private void dibujarCaminoMinimo(ArrayList<Coordenada> caminoMinimo, JCheckBox chckbxPeaje, JMapViewer map) {
			if (caminoMinimo!=null){
				for (int i=0;i<caminoMinimo.size()-1;i++){
					Coordinate inicio = new Coordinate(caminoMinimo.get(i).getLatitud(), caminoMinimo.get(i).getLongitud());
					Coordinate destino = new Coordinate(caminoMinimo.get(i+1).getLatitud(), caminoMinimo.get(i+1).getLongitud());
					VentanaPrincipal.dibujarLineaEntrePuntos(inicio, destino, Color.RED, chckbxPeaje, map);
				}
			}
		}

		private boolean checkPolygonName(MapPolygon polygon) {
			if (polygon.getName()!=null)
				return true;
			return false;
		}
		
		void limpiarPuntos(JMapViewer map, JTextField campoInicio, JTextField campoDestino){
			ArrayList<MapMarker> listaDePuntosAQuitar = new ArrayList<>();
			ArrayList<MapMarker> listaDePuntosAReemplazar = new ArrayList<>();
			for (MapMarker punto: map.getMapMarkerList()){
				if (punto.getName()!=null){
					listaDePuntosAQuitar.add(punto);

					MapMarkerDot puntoDeReemplazo = new MapMarkerDot(punto.getCoordinate());
					puntoDeReemplazo.setBackColor(Color.BLUE);
					listaDePuntosAReemplazar.add(puntoDeReemplazo);
				}
			}
			for (MapMarker punto: listaDePuntosAReemplazar){
				map.addMapMarker(punto);
			}
			borraPuntos(listaDePuntosAQuitar, map, campoInicio, campoDestino);
			map.removeAllMapPolygons();
			this.mapa = new MapaRutas();
		}
		
		/**
		 * Se añade al mapa un punto nuevo
		 * @param map mapa
		 * @param campoCoord1 Coordenada actual
		 * @param posicionActualMapa
		 */
		void agregarPunto(JMapViewer map, JTextField campoCoord1, Coordinate posicionActualMapa){
			MapMarkerDot nuevoPunto = new MapMarkerDot(posicionActualMapa);
			nuevoPunto.setBackColor(Color.BLUE);
			map.addMapMarker(nuevoPunto);
			campoCoord1.setText(round(posicionActualMapa.getLat(), 4) +"; "+ round(posicionActualMapa.getLon(), 4));
		}
		
		/**
		 * Si el boton quitar puntos esta seleccionado, cada vez que se hace click en un punto
		 * este se borra junto con las aristas que salen de el.
		 * @param map 
		 * @param campoInicio 
		 * @param campoDestino 
		 */
		void eliminarPunto(JMapViewer map, JTextField campoInicio, JTextField campoDestino){
			ArrayList<MapMarker> listaDePuntosAQuitar = new ArrayList<>();
			ArrayList<MapPolygon> listaDePoligonosAQuitar = new ArrayList<>();
			for (MapMarker punto: map.getMapMarkerList()){
				if (VentanaPrincipal.fueClickeado(punto)){
					listaDePuntosAQuitar.add(punto);
					for (MapPolygon polygon: map.getMapPolygonList())
						for (ICoordinate coordinate : polygon.getPoints())
							if (punto.getCoordinate().getLat() == coordinate.getLat() && punto.getCoordinate().getLon() == coordinate.getLon())
								listaDePoligonosAQuitar.add(polygon);
				}
			}
			borrarPoligonos(listaDePoligonosAQuitar, map);
			borraPuntos(listaDePuntosAQuitar, map, campoInicio, campoDestino);
		}

		private void borrarPoligonos(ArrayList<MapPolygon> listaDePoligonosAQuitar, JMapViewer map) {
			for (MapPolygon polygon: listaDePoligonosAQuitar){
				map.removeMapPolygon(polygon);
			}
		}

		private void borraPuntos(ArrayList<MapMarker> listaDePuntosAQuitar, JMapViewer map, JTextField campoInicio, JTextField campoDestino) {
			for (MapMarker punto: listaDePuntosAQuitar){
				map.removeMapMarker(punto);
				if (punto.getName() != null && punto.getName().equals("Inicio"))
					{this.puntoInicio = null; campoInicio.setText("");}
				if (punto.getName() != null && punto.getName().equals("Destino"))
					{this.puntoDestino = null; campoDestino.setText("");}
			}
		}
		
		void agregarPuntoDeInicio(JMapViewer map, JTextField campoInicio, Coordinate posicionActualMapa){
			for (MapMarker punto: map.getMapMarkerList()){

				if (VentanaPrincipal.fueClickeado(punto) && this.puntoInicio == null){
					this.puntoInicio = new Coordinate(punto.getLat(), punto.getLon());
					map.removeMapMarker(punto);

					MapMarkerDot puntoInicialMapa = new MapMarkerDot("Inicio", puntoInicio);
					puntoInicialMapa.setBackColor(Color.RED);

					map.addMapMarker(puntoInicialMapa);
					campoInicio.setText(round(posicionActualMapa.getLat(), 4) +"; "+ round(posicionActualMapa.getLon(), 4));
					break;
				}
			}
		}
		
		void agregarPuntoDestino(JMapViewer map, JTextField campoDestino, Coordinate posicionActualMapa){
			for (MapMarker punto: map.getMapMarkerList()){
				if (VentanaPrincipal.fueClickeado(punto) && this.puntoDestino == null){
					this.puntoDestino = new Coordinate(punto.getLat(), punto.getLon());
					map.removeMapMarker(punto);

					MapMarkerDot puntoDestinoMapa = new MapMarkerDot("Destino", puntoDestino);
					puntoDestinoMapa.setBackColor(Color.YELLOW);

					map.addMapMarker(puntoDestinoMapa);
					campoDestino.setText(round(posicionActualMapa.getLat(), 4) +"; "+ round(posicionActualMapa.getLon(), 4));
					break;
				}
			}
		}
		
		public static double round(double value, int places) {
		    if (places < 0) throw new IllegalArgumentException();

		    long factor = (long) Math.pow(10, places);
		    value = value * factor;
		    long tmp = Math.round(value);
		    return (double) tmp / factor;
		}
}
