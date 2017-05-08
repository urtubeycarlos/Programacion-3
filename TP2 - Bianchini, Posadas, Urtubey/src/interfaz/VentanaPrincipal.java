package interfaz;
import librerias.*;
import mapa.Coordenada;
import mapa.Mapa;

import java.awt.EventQueue;
import java.awt.Font;
import java.util.List;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

public class VentanaPrincipal {
	
	private JFrame frame;
	private JMapViewerTree mapViewerTree;
	private ArrayList<Double[]> puntosEnElMapa;
	Point posicionActualFrame;
	Coordenada posicionActualMapa;
	boolean agregandoPuntos;
	boolean quitandoPuntos;
	private final ButtonGroup botonesDelPanel = new ButtonGroup();
	
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
		puntosEnElMapa = new ArrayList<>();
		posicionActualFrame = new Point();
		posicionActualMapa = new Coordenada();
				
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
		
		JButton btnNewButton = new JButton("Marcar inicio");
		botonesDelPanel.add(btnNewButton);
		btnNewButton.setBounds(284, 420, 150, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnDestino = new JButton("Marcar destino");
		botonesDelPanel.add(btnDestino);
		btnDestino.setBounds(489, 420, 150, 23);
		frame.getContentPane().add(btnDestino);
		
		//Se inicializa el mapa
		mapViewerTree = new JMapViewerTree("Zones");
		mapViewerTree.setBounds(10, 10, 930, 400);
		frame.getContentPane().add(mapViewerTree);
		
		// Action listeners
		btnAgregarPunto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnAgregarPunto.isSelected()){
					btnAgregarPunto.setSelected(false);
					btnAgregarPunto.setForeground(Color.BLACK);
				}
				else{
					Enumeration<AbstractButton> botones = botonesDelPanel.getElements();
					while (botones.hasMoreElements()){
						JButton botonActual = (JButton) botones.nextElement();
						botonActual.setSelected(false);
						botonActual.setForeground(Color.BLACK);
					}
					btnAgregarPunto.setSelected(true);
					btnAgregarPunto.setForeground(Color.BLUE);
				}
			}
		});
		
		btnQuitarPunto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnQuitarPunto.isSelected()){
					btnQuitarPunto.setSelected(false);
					btnQuitarPunto.setForeground(Color.BLACK);
				}
				else{
					Enumeration<AbstractButton> botones = botonesDelPanel.getElements();
					while (botones.hasMoreElements()){
						JButton botonActual = (JButton) botones.nextElement();
						botonActual.setSelected(false);
						botonActual.setForeground(Color.BLACK);
					}
					btnQuitarPunto.setSelected(true);
					btnQuitarPunto.setForeground(Color.BLUE);
				}
			}
		});
		
		map().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                posicionActualFrame = e.getPoint();
            	posicionActualMapa.setLatitudLongitud(map().getPosition(posicionActualFrame).getLat(), map().getPosition(posicionActualFrame).getLon());
                //esto es alta magia
                map().setToolTipText(map().getPosition(e.getPoint()).toString());
            }
        });
		
		map().addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                	if (btnAgregarPunto.isSelected()){
                		guardarCoordenadasSeleccionadas();
                	}
                	if (btnQuitarPunto.isSelected()){
                		eliminarCoordenadasSeleccionadas();
                	}
                }
            }
        });
	}
	
	private JMapViewer map() {
        return mapViewerTree.getViewer();
    }
	
	private void guardarCoordenadasSeleccionadas(){
		MapMarkerDot nuevoPunto = new MapMarkerDot(posicionActualMapa.getLatitud(), posicionActualMapa.getLongitud());
		map().addMapMarker(nuevoPunto);
	}
	
	private void eliminarCoordenadasSeleccionadas(){
		List<MapMarker> listaDePuntosDelMapa = map().getMapMarkerList();
		
		for (int punto=0;punto<listaDePuntosDelMapa.size();punto++){
			MapMarker puntoSeleccionado = listaDePuntosDelMapa.get(punto);
			
			if (estaSeleccionado(puntoSeleccionado)){
				map().removeMapMarker(puntoSeleccionado);
			}
		}
	}
	
	private void seleccionarPuntoInicial(){
		
	}
	
	/**
	 * Este metodo chequea si un punto marcado en el mapa esta siendo clickeado por el mouse.
	 * @param punto Es un MapMarker proveniente del JMapViewer
	 * @return boolean
	 */
	private boolean estaSeleccionado(MapMarker punto){
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
		Coordinate bordeDelMouseMapa = map().getPosition(toleranciaMouse);
		
		// Coordenadas finales 
		Coordenada centroDelMouse = posicionActualMapa;
		Coordenada bordeDelMouse = new Coordenada("", bordeDelMouseMapa.getLat(), bordeDelMouseMapa.getLon());
		Coordenada coordenadaPunto = new Coordenada("", punto.getLat(), punto.getLon());
		
		// Distancia entre mouse y tolerancia
		Double dx = centroDelMouse.getLatitud() - bordeDelMouse.getLatitud();
		Double dy = centroDelMouse.getLongitud() - bordeDelMouse.getLongitud();
		final Double radioMouse = Math.sqrt((dx*dx)+(dy*dy));
		
		// Distancia entre mouse y punto
		dx = centroDelMouse.getLatitud() - punto.getLat();
		dy = centroDelMouse.getLongitud() - punto.getLon();
		final Double distanciaMousePunto = Math.sqrt((dx*dx)+(dy*dy));
		
		// La Verdad
		if (distanciaMousePunto<=radioMouse)
            return true;
        return false;
		
	}
	
}
