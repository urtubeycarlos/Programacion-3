package interfaz;
import mapa.Coordenada;
import mapa.Mapa;

import java.awt.EventQueue;
import java.util.List;
import java.awt.Point;

import javax.swing.JFrame;

import org.junit.experimental.theories.Theories;
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
import java.awt.Font;

public class VentanaPrincipal {
	
	private JFrame frame;
	private JMapViewerTree mapViewerTree;
	private final ButtonGroup botonesDelPanel = new ButtonGroup();
	
	Point posicionActualFrame;
	Coordinate posicionActualMapa;
	ArrayList<MapMarker> puntosSeleccionados;
	
	// Trabajamos con coordinates para que sea mas agil la imple del jmap.
	Mapa mapa;
	ArrayList<Coordinate> listaDeCoordenadas;
	Coordinate puntoInicio;
	Coordinate puntoDestino;
	private JTextField campoInicio;
	private JTextField campoCoord1;
	private JTextField campoDestino;
	private JTextField campoCoord2;
	
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
		btnInicio.setBounds(364, 420, 150, 23);
		frame.getContentPane().add(btnInicio);
		
		JButton btnDestino = new JButton("Marcar destino");
		botonesDelPanel.add(btnDestino);
		btnDestino.setBounds(617, 420, 150, 23);
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
		
		//Se inicializa el Jmap
		mapViewerTree = new JMapViewerTree("Zones");
		mapViewerTree.setBounds(10, 10, 930, 400);
		frame.getContentPane().add(mapViewerTree);
		
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
		
		// Action listeners
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
                //esto es alta magia
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
		List<MapMarker> listaDePuntosDelMapa = map().getMapMarkerList();
		List<MapPolygon> listaDePoligonosDelMapa = map().getMapPolygonList();
		
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
		for (MapPolygon polygon: listaDePoligonosAQuitar){
			map().removeMapPolygon(polygon);
		}
		for (MapMarker punto: listaDePuntosAQuitar){
			map().removeMapMarker(punto);
			System.out.println("se elimino un punto");
			this.listaDeCoordenadas.remove(punto.getCoordinate());
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
			//TODO: hacer la seleccion de inicio de los puntos.
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
		List<MapMarker> listaDePuntosDelMapa = map().getMapMarkerList();
		for (MapMarker punto: listaDePuntosDelMapa){

			if (fueClickeado(punto) && !puntosSeleccionados.contains(punto))
				this.puntosSeleccionados.add(punto);
			}
			if (this.puntosSeleccionados.size()>=2){
				dibujarLineaEntrePuntosSeleccionados();
				deseleccionarPuntos();
			}
	}

	private void dibujarLineaEntrePuntosSeleccionados() {
		Coordinate inicio = this.puntosSeleccionados.get(0).getCoordinate();
		Coordinate destino = this.puntosSeleccionados.get(1).getCoordinate();
		List<Coordinate> ruta = new ArrayList<Coordinate>(Arrays.asList(inicio, destino, destino));
		map().addMapPolygon(new MapPolygonImpl(ruta));
		
	}
	
	private void deseleccionarPuntos() {
		if (this.puntosSeleccionados != null)
			this.puntosSeleccionados.clear();
	}
	
	private void agregarPuntoDeInicio(){
		List<MapMarker> listaDePuntosDelMapa = map().getMapMarkerList();
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
		List<MapMarker> listaDePuntosDelMapa = map().getMapMarkerList();
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
}
