package interfaz;
import librerias.*;

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
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;

public class VentanaPrincipal {
	
	private JFrame frame;
	private JMapViewerTree mapViewerTree;
	private ArrayList<Double[]> puntosEnElMapa;
	Point posicionActualFrame;
	Point posicionActualMapa;
	boolean agregandoPuntos;
	boolean quitandoPuntos;
	
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
		posicionActualMapa = new Point();
				
		frame = new JFrame();
		frame.setBounds(100, 100, 970, 592);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnAgregarPunto = new JButton("Agregar Puntos");
		btnAgregarPunto.setBounds(24, 420, 150, 23);
		frame.getContentPane().add(btnAgregarPunto);
		
		JButton btnQuitarPunto = new JButton("Quitar Puntos");
		btnQuitarPunto.setBounds(24, 454, 150, 23);
		frame.getContentPane().add(btnQuitarPunto);
		
		JButton btnAgregarRuta = new JButton("Conectar Puntos");
		btnAgregarRuta.setBounds(24, 488, 150, 23);
		frame.getContentPane().add(btnAgregarRuta);
		
		JButton btnNewButton = new JButton("Marcar inicio");
		btnNewButton.setBounds(284, 420, 150, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnDestino = new JButton("Marcar destino");
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
				if (agregandoPuntos){
					agregandoPuntos = false;
					btnAgregarPunto.setForeground(Color.BLACK);
				}
				else{
					agregandoPuntos = true;
					quitandoPuntos = false;
					btnQuitarPunto.setForeground(Color.BLACK);
					btnAgregarPunto.setForeground(Color.BLUE);
				}
			}
		});
		
		btnQuitarPunto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (quitandoPuntos){
					quitandoPuntos = false;
					btnQuitarPunto.setForeground(Color.BLACK);
				}
				else{
					quitandoPuntos = true;
					agregandoPuntos = false;
					btnAgregarPunto.setForeground(Color.BLACK);
					btnQuitarPunto.setForeground(Color.BLUE);
				}
				
			}
		});
		
		map().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                posicionActualFrame = e.getPoint();
                posicionActualMapa.setLocation(map().getPosition(posicionActualFrame).getLat(), map().getPosition(posicionActualFrame).getLon());
                
                map().setToolTipText(map().getPosition(posicionActualFrame).toString());
            }
        });
		
		map().addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                	if (agregandoPuntos){
                		guardarCoordenadasSeleccionadas();
                	}
                	if (quitandoPuntos){
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
		MapMarkerDot nuevoPunto = new MapMarkerDot(posicionActualMapa.getX(), posicionActualMapa.getY());
		map().addMapMarker(nuevoPunto);
	}
	
	private void eliminarCoordenadasSeleccionadas(){
		List<MapMarker> listaDePuntosDelMapa = map().getMapMarkerList();
		
		for (int punto=0;punto<listaDePuntosDelMapa.size();punto++){
			System.out.println(listaDePuntosDelMapa.get(punto).toString());
			// TODO: obtener las coordenadas donde hice click, crear un circulo de radio 5 y fijarse si hay un punto dentro de ese circulo. acto seguido borrarlo
		}
		
	}
	
}
