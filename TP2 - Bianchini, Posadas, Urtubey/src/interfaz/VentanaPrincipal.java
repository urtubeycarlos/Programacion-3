package interfaz;
import mapa.Coordenada;
import mapa.MapaRutas;

import java.awt.EventQueue;
import java.util.List;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JFrame;

import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JCheckBox;

public class VentanaPrincipal {
	
	private JFrame frame;
	private JMapViewerTree mapViewerTree;
	private final ButtonGroup botonesDelPanel = new ButtonGroup();
	
	Point posicionActualFrame;
	Coordinate posicionActualMapa;
	ArrayList<MapMarker> puntosSeleccionados;
	List<MapMarker> listaDePuntosDelMapa;
	List<MapPolygon> listaDePoligonosDelMapa;
	boolean peaje = false;
	
	// Trabajamos con coordinates para que sea mas agil la imple del jmap.
	MapaRutas mapa = new MapaRutas();
	ArrayList<Coordinate> listaDeCoordenadas;
	Coordinate puntoInicio;
	Coordinate puntoDestino;
	private JTextField campoInicio;
	private JTextField campoCoord1;
	private JTextField campoDestino;
	private JTextField campoCoord2;
	private JTextField campoPeajes;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		posicionActualFrame = new Point();
		posicionActualMapa = new Coordinate(0, 0);
		puntosSeleccionados = new ArrayList<>();
		
		listaDeCoordenadas = new ArrayList<>();
				
		frame = new JFrame();
		frame.setBounds(100, 100, 970, 592);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnAgregarPunto = new JButton("Agregar Puntos");
		botonesDelPanel.add(btnAgregarPunto);
		btnAgregarPunto.setBounds(24, 420, 150, 23);
		frame.getContentPane().add(btnAgregarPunto);
		
		JButton btnQuitarPunto = new JButton("Quitar Puntos");
		botonesDelPanel.add(btnQuitarPunto);
		btnQuitarPunto.setBounds(24, 454, 150, 23);
		frame.getContentPane().add(btnQuitarPunto);
		
		JButton btnAgregarRuta = new JButton("Conectar Puntos");
		botonesDelPanel.add(btnAgregarRuta);
		btnAgregarRuta.setBounds(24, 488, 150, 23);
		frame.getContentPane().add(btnAgregarRuta);
		
		JButton btnInicio = new JButton("Marcar inicio");
		botonesDelPanel.add(btnInicio);
		btnInicio.setBounds(284, 420, 150, 23);
		frame.getContentPane().add(btnInicio);
		
		JButton btnDestino = new JButton("Marcar destino");
		botonesDelPanel.add(btnDestino);
		btnDestino.setBounds(697, 420, 150, 23);
		frame.getContentPane().add(btnDestino);
		
		campoInicio = new JTextField();
		campoInicio.setFont(new Font("Tahoma", Font.BOLD, 11));
		campoInicio.setBounds(284, 455, 230, 20);
		frame.getContentPane().add(campoInicio);
		campoInicio.setColumns(10);
		
		campoCoord1 = new JTextField();
		campoCoord1.setBounds(284, 489, 230, 20);
		frame.getContentPane().add(campoCoord1);
		campoCoord1.setColumns(10);
		
		campoDestino = new JTextField();
		campoDestino.setFont(new Font("Tahoma", Font.BOLD, 11));
		campoDestino.setBounds(617, 455, 230, 20);
		frame.getContentPane().add(campoDestino);
		campoDestino.setColumns(10);
		
		campoCoord2 = new JTextField();
		campoCoord2.setBounds(617, 489, 230, 20);
		frame.getContentPane().add(campoCoord2);
		campoCoord2.setColumns(10);
		
		JLabel lblInicio = new JLabel("Inicio");
		lblInicio.setBounds(224, 458, 50, 14);
		frame.getContentPane().add(lblInicio);
		
		JLabel lblDestino = new JLabel("Destino");
		lblDestino.setBounds(557, 458, 50, 14);
		frame.getContentPane().add(lblDestino);
		
		JLabel lblNewLabel = new JLabel("Ultima");
		lblNewLabel.setBounds(224, 492, 50, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblV = new JLabel("Actual");
		lblV.setBounds(557, 492, 50, 14);
		frame.getContentPane().add(lblV);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(364, 520, 150, 23);
		frame.getContentPane().add(btnLimpiar);
		
		JButton btnBorrarTodo = new JButton("Borrar Todo");
		btnBorrarTodo.setBounds(617, 520, 150, 23);
		frame.getContentPane().add(btnBorrarTodo);
		
		JButton btnObtenerCamino = new JButton("Obtener Camino Minimo");
		btnObtenerCamino.setBounds(444, 420, 150, 23);
		frame.getContentPane().add(btnObtenerCamino);
		
		JCheckBox chckbxPeaje = new JCheckBox("Peaje");
		chckbxPeaje.setBounds(24, 518, 97, 23);
		frame.getContentPane().add(chckbxPeaje);
		
		campoPeajes = new JTextField();
		campoPeajes.setText("0");
		campoPeajes.setBounds(661, 421, 26, 20);
		frame.getContentPane().add(campoPeajes);
		campoPeajes.setColumns(10);
		
		JLabel lblPeajes = new JLabel("Peajes");
		lblPeajes.setBounds(605, 424, 56, 14);
		frame.getContentPane().add(lblPeajes);
		
		//Se inicializa el Jmap
		mapViewerTree = new JMapViewerTree("Map");
		mapViewerTree.setBounds(10, 10, 930, 400);
		frame.getContentPane().add(mapViewerTree);
		
		// Action listeners
		chckbxPeaje.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!peaje)
					peaje = true;
				else
					peaje = false;
			}
		});
		btnObtenerCamino.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				obtenerCaminoMinimo();
			}
		});
		btnBorrarTodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpiarPuntos();
				map().removeAllMapMarkers();
				map().removeAllMapPolygons();
			}
		});
		btnLimpiar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarPuntos();
			}
		});
		btnAgregarPunto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myActionPerformed(e, btnAgregarPunto);
			}
		});
		btnQuitarPunto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myActionPerformed(e, btnQuitarPunto);
			}
		});
		btnAgregarRuta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				myActionPerformed(e, btnAgregarRuta);
			}
		});
		btnInicio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myActionPerformed(e, btnInicio);
			}
		});
		btnDestino.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myActionPerformed(e, btnDestino);			}
		});
		
		// Mouse listeners
		map().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                posicionActualFrame = e.getPoint();
                posicionActualMapa = map().getPosition(posicionActualFrame);
                map().setToolTipText(map().getPosition(e.getPoint()).toString());
                campoCoord2.setText(round(posicionActualMapa.getLat(), 4) +"; "+ round(posicionActualMapa.getLon(), 4));
            }
        });
		
		map().addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                	if (btnAgregarPunto.isSelected()){
                		agregarPunto();
                	}
                	if (btnQuitarPunto.isSelected()){
                		eliminarPunto();
                	}
                	escucharSeleccionDePuntos(btnAgregarRuta, btnInicio, btnDestino);
                }
            }
        });
	}
	
	//Metodos
	private void obtenerCaminoMinimo(){
		actualizaPuntosYPoligonos();
		
		pasarDatosAlMapa();
		ArrayList<Coordenada> caminoMinimo = obtenerCamino();
		dibujarCaminoMinimo(caminoMinimo);
	}

	private void pasarDatosAlMapa() {
		for (MapPolygon polygon: listaDePoligonosDelMapa){
			System.out.println(listaDePoligonosDelMapa);
			Coordenada coord1 = new Coordenada(null, polygon.getPoints().get(0).getLat(), polygon.getPoints().get(0).getLon());
			Coordenada coord2 = new Coordenada(null, polygon.getPoints().get(1).getLat(), polygon.getPoints().get(1).getLon());
			
			boolean conPeaje = checkPolygonName(polygon);
			
			System.out.println(coord1);
			
			mapa.agregarCoordenada(coord1);
			mapa.agregarCoordenada(coord2);
			
			
			mapa.agregarRuta(coord1, coord2, conPeaje);
			mapa.agregarRuta(coord2, coord1, conPeaje);
			
			
		}
	}
	
	private ArrayList<Coordenada> obtenerCamino() {
		Coordenada origen = new Coordenada("Inicio", this.puntoInicio.getLat(), this.puntoInicio.getLon()); 
		Coordenada destino = new Coordenada("Destino", this.puntoDestino.getLat(), this.puntoDestino.getLon()); 
		int cantPeajes = Integer.parseInt(campoPeajes.getText());
		try {
			ArrayList<Coordenada> caminoMinimo = (ArrayList<Coordenada>) mapa.obtenerRutaOptima(origen, destino, cantPeajes);
			return caminoMinimo;
		}
		catch (Exception e){
			mostrarError(e.getMessage());
		}
		return null;
	}

	private void dibujarCaminoMinimo(ArrayList<Coordenada> caminoMinimo) {
		if (caminoMinimo!=null){
			System.out.println("camino :"+caminoMinimo.toString());
			for (int i=0;i<caminoMinimo.size()-1;i++){
				Coordinate inicio = new Coordinate(caminoMinimo.get(i).getLatitud(), caminoMinimo.get(i).getLongitud());
				Coordinate destino = new Coordinate(caminoMinimo.get(i+1).getLatitud(), caminoMinimo.get(i+1).getLongitud());
				dibujarLineaEntrePuntos(inicio, destino, Color.RED);
			}
		}
	}

	private boolean checkPolygonName(MapPolygon polygon) {
		if (polygon.getName()!=null)
			return true;
		return false;
	}
	
	/**
	 * Generaliza el actionPerformed de los botones
	 */
	public void myActionPerformed(ActionEvent action, JButton button) {
		
		if (button.isSelected()){
			button.setSelected(false);
			button.setForeground(Color.BLACK);
			deseleccionarPuntos();
		}
		else{
			deseleccionarBotones();
			button.setSelected(true);
			button.setForeground(Color.BLUE);
		}
	}
	
	/**
	 *  Jmap getter. devuelve el visor del mapa con todos sus metodos
	 */
	private JMapViewer map() {
        return mapViewerTree.getViewer();
    }

	private void limpiarPuntos(){
		actualizaPuntosYPoligonos();
		ArrayList<MapMarker> listaDePuntosAQuitar = new ArrayList<>();
		ArrayList<MapMarker> listaDePuntosAReemplazar = new ArrayList<>();
		for (MapMarker punto: listaDePuntosDelMapa){
			if (punto.getName()!=null){
				listaDePuntosAQuitar.add(punto);

				MapMarkerDot puntoDeReemplazo = new MapMarkerDot(punto.getCoordinate());
				puntoDeReemplazo.setBackColor(Color.BLUE);
				listaDePuntosAReemplazar.add(puntoDeReemplazo);
			}
		}
		for (MapMarker punto: listaDePuntosAReemplazar){
			listaDeCoordenadas.add(punto.getCoordinate());
			map().addMapMarker(punto);
		}
		borraPuntos(listaDePuntosAQuitar);
		map().removeAllMapPolygons();
		this.listaDePoligonosDelMapa.clear();
		
		this.mapa = new MapaRutas();
	}
	
	/**
	 * Se añade al mapa un punto nuevo
	 */
	private void agregarPunto(){
		MapMarkerDot nuevoPunto = new MapMarkerDot(posicionActualMapa);
		nuevoPunto.setBackColor(Color.BLUE);
		map().addMapMarker(nuevoPunto);
		this.listaDeCoordenadas.add(nuevoPunto.getCoordinate());
		campoCoord1.setText(round(posicionActualMapa.getLat(), 4) +"; "+ round(posicionActualMapa.getLon(), 4));
	}
	
	/**
	 * Si el boton quitar puntos esta seleccionado, cada vez que se hace click en un punto
	 * este se borra junto con las aristas que salen de el.
	 */
	private void eliminarPunto(){
		actualizaPuntosYPoligonos();
		ArrayList<MapMarker> listaDePuntosAQuitar = new ArrayList<>();
		ArrayList<MapPolygon> listaDePoligonosAQuitar = new ArrayList<>();
		for (MapMarker punto: listaDePuntosDelMapa){
			if (fueClickeado(punto)){
				listaDePuntosAQuitar.add(punto);
				for (MapPolygon polygon: listaDePoligonosDelMapa)
					for (ICoordinate coordinate : polygon.getPoints())
						if (punto.getCoordinate().equals(coordinate))
							listaDePoligonosAQuitar.add(polygon);
			}
		}
		borrarPoligonos(listaDePoligonosAQuitar);
		borraPuntos(listaDePuntosAQuitar);
	}

	private void borrarPoligonos(ArrayList<MapPolygon> listaDePoligonosAQuitar) {
		for (MapPolygon polygon: listaDePoligonosAQuitar){
			map().removeMapPolygon(polygon);
		}
	}

	private void borraPuntos(ArrayList<MapMarker> listaDePuntosAQuitar) {
		for (MapMarker punto: listaDePuntosAQuitar){
			map().removeMapMarker(punto);
			this.listaDeCoordenadas.remove(punto.getCoordinate());
			if (punto.getName() != null && punto.getName().equals("Inicio"))
				{this.puntoInicio = null; campoInicio.setText("");}
			if (punto.getName() != null && punto.getName().equals("Destino"))
				{this.puntoDestino = null; campoDestino.setText("");}
		}
	}
	
	private void deseleccionarBotones() {
		Enumeration<AbstractButton> botones = botonesDelPanel.getElements();
		while (botones.hasMoreElements()){
			JButton botonActual = (JButton) botones.nextElement();
			botonActual.setSelected(false);
			botonActual.setForeground(Color.BLACK);
		}
	}
	
	private void escucharSeleccionDePuntos(JButton agregarRuta, JButton agregarInicio, JButton agregarDestino){
		if (agregarRuta.isSelected()){
			seleccionandoRuta();
		}
		else if (agregarInicio.isSelected()){
			agregarPuntoDeInicio();
		}
		else if (agregarDestino.isSelected()){
			agregarPuntoDestino();
		}
		else{
			deseleccionarPuntos();
		}
	}

	/**
	 * Este metodo agrega puntos a la seleccion de ruta. Cuando esta se completa,
	 * dibuja una linea entre ellos.
	 */
	private void seleccionandoRuta() {
		actualizaPuntosYPoligonos();
		for (MapMarker punto: listaDePuntosDelMapa){

			if (fueClickeado(punto) && !puntosSeleccionados.contains(punto)){
				this.puntosSeleccionados.add(punto);
				campoCoord1.setText(round(posicionActualMapa.getLat(), 4) +"; "+ round(posicionActualMapa.getLon(), 4));
			}
		}
		if (this.puntosSeleccionados.size()>=2){
			dibujarLineaEntrePuntosSeleccionados();
			deseleccionarPuntos();
		}
	}

	private void dibujarLineaEntrePuntosSeleccionados() {
		Coordinate inicio = this.puntosSeleccionados.get(0).getCoordinate();
		Coordinate destino = this.puntosSeleccionados.get(1).getCoordinate();
		dibujarLineaEntrePuntos(inicio, destino, null);
	}

	private void dibujarLineaEntrePuntos(Coordinate inicio, Coordinate destino, Color color) {
		List<Coordinate> ruta = new ArrayList<Coordinate>(Arrays.asList(inicio, destino, destino));
		MapPolygonImpl rutaMap = new MapPolygonImpl(ruta);
		// bug
		if (peaje && color==null){
			rutaMap.setName("Peaje");
			rutaMap.setColor(Color.MAGENTA);
		}
		else if (color!=null){
			rutaMap.setColor(color);
		}
		else
			rutaMap.setColor(Color.GREEN);
		map().addMapPolygon(rutaMap);
	}
	
	private void deseleccionarPuntos() {
		if (this.puntosSeleccionados != null)
			this.puntosSeleccionados.clear();
	}
	
	private void agregarPuntoDeInicio(){
		actualizaPuntosYPoligonos();
		for (MapMarker punto: listaDePuntosDelMapa){

			if (fueClickeado(punto) && this.puntoInicio == null){
				this.puntoInicio = new Coordinate(punto.getLat(), punto.getLon());
				map().removeMapMarker(punto);

				MapMarkerDot puntoInicialMapa = new MapMarkerDot("Inicio", puntoInicio);
				puntoInicialMapa.setBackColor(Color.RED);

				map().addMapMarker(puntoInicialMapa);
				campoInicio.setText(round(posicionActualMapa.getLat(), 4) +"; "+ round(posicionActualMapa.getLon(), 4));
				break;
			}
		}
	}
	
	private void agregarPuntoDestino(){
		actualizaPuntosYPoligonos();
		for (MapMarker punto: listaDePuntosDelMapa){
			if (fueClickeado(punto) && this.puntoDestino == null){
				this.puntoDestino = new Coordinate(punto.getLat(), punto.getLon());
				map().removeMapMarker(punto);

				MapMarkerDot puntoDestinoMapa = new MapMarkerDot("Destino", puntoDestino);
				puntoDestinoMapa.setBackColor(Color.YELLOW);

				map().addMapMarker(puntoDestinoMapa);
				campoDestino.setText(round(posicionActualMapa.getLat(), 4) +"; "+ round(posicionActualMapa.getLon(), 4));
				break;
			}
		}
	}
	/**
	 * Este metodo chequea si un punto marcado en el mapa esta siendo clickeado por el mouse.
	 * @param punto Es un MapMarker proveniente del JMapViewer
	 * @return boolean
	 */
	private boolean fueClickeado(MapMarker punto){
		/*
		 * La funcion toma la coordenada del JFrame del mouse, y calcula un punto ficticio alrededor del mismo
		 * al cual se le llama tolerancia. Este se obtiene de sumarle a la coordenada del mouse el radio del
		 * circulo que identifica al punto pasado como parametro. Las coordenadas reales se obtienen pasando
		 * estos puntos al mapa para que este devuelva las coordenadas reales. Luego se calcula distancia entre
		 * mouse, tolerancia y punto utilizando Pitagoras.
		 */
		
		// Coordenadas JFrame
		int toleranciaXFrame = (int)(posicionActualFrame.getX()+punto.getRadius());
		int toleranciaYFrame = (int)(posicionActualFrame.getY()+punto.getRadius());

		Point toleranciaMouse = new Point(toleranciaXFrame, toleranciaYFrame);
		
		// Coordenadas JMap
		Coordinate bordeDelMouse = map().getPosition(toleranciaMouse);
		Coordinate centroDelMouse = posicionActualMapa;
		
		// Distancia entre mouse y tolerancia
		Double dx = centroDelMouse.getLat() - bordeDelMouse.getLat();
		Double dy = centroDelMouse.getLon() - bordeDelMouse.getLon();
		final Double radioToleranciaMouse = Math.sqrt((dx*dx)+(dy*dy));
		
		// Distancia entre mouse y punto
		dx = centroDelMouse.getLat() - punto.getLat();
		dy = centroDelMouse.getLon() - punto.getLon();
		final Double distanciaMousePunto = Math.sqrt((dx*dx)+(dy*dy));
		
		// La Verdad
		if (distanciaMousePunto<=radioToleranciaMouse)
            return true;
        return false;
		
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	private void actualizaPuntosYPoligonos(){
		listaDePuntosDelMapa = map().getMapMarkerList();
		listaDePoligonosDelMapa = map().getMapPolygonList();
	}
	
	private void mostrarError(String message){
		JOptionPane.showMessageDialog(frame, message);
		
	}
}
