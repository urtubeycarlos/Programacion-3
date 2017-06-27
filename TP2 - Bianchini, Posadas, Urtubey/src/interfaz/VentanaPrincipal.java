package interfaz;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;

import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.awt.event.ActionEvent;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Point;

import javax.swing.JCheckBox;

public class VentanaPrincipal {
	
	private JFrame frame;
	private static JMapViewerTree mapViewerTree;
	private final ButtonGroup botonesDelPanel = new ButtonGroup();
	
	private JTextField campoInicio;
	private JTextField campoCoord1;
	private JTextField campoDestino;
	private JTextField campoCoord2;
	private JTextField campoPeajes;
	
	ArrayList<MapMarker> puntosSeleccionados;
	static Point posicionActualFrame;
	static Coordinate posicionActualMapa;
	
	private Interfaz interfaz;
	
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
		interfaz = new Interfaz();
		puntosSeleccionados = new ArrayList<>();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 970, 592);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnAgregarPunto = new JButton("Agregar Puntos");
		getBotonesDelPanel().add(btnAgregarPunto);
		btnAgregarPunto.setBounds(24, 420, 150, 23);
		frame.getContentPane().add(btnAgregarPunto);
		
		JButton btnQuitarPunto = new JButton("Quitar Puntos");
		getBotonesDelPanel().add(btnQuitarPunto);
		btnQuitarPunto.setBounds(24, 454, 150, 23);
		frame.getContentPane().add(btnQuitarPunto);
		
		JButton btnAgregarRuta = new JButton("Conectar Puntos");
		getBotonesDelPanel().add(btnAgregarRuta);
		btnAgregarRuta.setBounds(24, 488, 150, 23);
		frame.getContentPane().add(btnAgregarRuta);
		
		JButton btnInicio = new JButton("Marcar inicio");
		getBotonesDelPanel().add(btnInicio);
		btnInicio.setBounds(284, 420, 150, 23);
		frame.getContentPane().add(btnInicio);
		
		JButton btnDestino = new JButton("Marcar destino");
		getBotonesDelPanel().add(btnDestino);
		btnDestino.setBounds(444, 420, 150, 23);
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
		btnObtenerCamino.setBounds(697, 420, 150, 23);
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
		JMapViewer map = mapViewerTree.getViewer();
		
		// Action listeners
		btnObtenerCamino.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					interfaz.obtenerCaminoMinimo(map, campoPeajes, chckbxPeaje);
					habilitarBotones(false);
				}
				catch (Exception exception){
					exception.printStackTrace();
//					mostrarError(exception.getMessage());
				}
			}
		});
		btnBorrarTodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				interfaz.limpiarPuntos(map, campoInicio, campoDestino);
				map.removeAllMapMarkers();
				habilitarBotones(true);
			}
		});
		btnLimpiar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interfaz.limpiarPuntos(map, campoInicio, campoDestino);
				habilitarBotones(true);
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
		map.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                posicionActualFrame = e.getPoint();
                posicionActualMapa = map.getPosition(posicionActualFrame);
                map.setToolTipText(map.getPosition(e.getPoint()).toString());
                campoCoord2.setText(
                		Interfaz.round(posicionActualMapa.getLat(), 4) +
                		"; "+ 
                		Interfaz.round(posicionActualMapa.getLon(), 4));
            }
        });
		
		map.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                	if (btnAgregarPunto.isSelected()){
                		interfaz.agregarPunto(map, campoCoord1, posicionActualMapa);
                	}
                	if (btnQuitarPunto.isSelected()){
                		interfaz.eliminarPunto(map, campoInicio, campoDestino);
                	}
                	escucharSeleccionDePuntos(map, btnAgregarRuta, btnInicio, btnDestino, chckbxPeaje);
                }
            }
        });
	}

	public ButtonGroup getBotonesDelPanel() {
		return botonesDelPanel;
	}

	private static JMapViewer map(){
		return mapViewerTree.getViewer();
	}
	
	/**
	 * Generaliza el actionPerformed de los botones
	 * @param botones 
	 */
	private void myActionPerformed(ActionEvent action, JButton button) {
		
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
	
	private void escucharSeleccionDePuntos(JMapViewer map, JButton agregarRuta, JButton agregarInicio, JButton agregarDestino, JCheckBox chckbxPeaje){
		if (agregarRuta.isSelected()){
			seleccionandoRuta(chckbxPeaje);
		}
		else if (agregarInicio.isSelected()){
			interfaz.agregarPuntoDeInicio(map, campoInicio, posicionActualMapa);
		}
		else if (agregarDestino.isSelected()){
			interfaz.agregarPuntoDestino(map, campoDestino, posicionActualMapa);
		}
		else{
			deseleccionarPuntos();
		}
	}
	
	/**
	 * Este metodo agrega puntos a la seleccion de ruta. Cuando esta se completa,
	 * dibuja una linea entre ellos.
	 * @param chckbxPeaje 
	 */
	private void seleccionandoRuta(JCheckBox chckbxPeaje) {
		for (MapMarker punto: map().getMapMarkerList()){

			if (fueClickeado(punto) && !puntosSeleccionados.contains(punto)){
				this.puntosSeleccionados.add(punto);
				campoCoord1.setText(Interfaz.round(posicionActualMapa.getLat(), 4) +"; "+ Interfaz.round(posicionActualMapa.getLon(), 4));
			}
		}
		if (this.puntosSeleccionados.size()>=2){
			dibujarLineaEntrePuntosSeleccionados(chckbxPeaje);
			deseleccionarPuntos();
		}
	}
	
	private void dibujarLineaEntrePuntosSeleccionados(JCheckBox chckbxPeaje) {
		Coordinate inicio = this.puntosSeleccionados.get(0).getCoordinate();
		Coordinate destino = this.puntosSeleccionados.get(1).getCoordinate();
		dibujarLineaEntrePuntos(inicio, destino, null, chckbxPeaje, map());
	}
	
	private void deseleccionarBotones() {
		Enumeration<AbstractButton> botones = botonesDelPanel.getElements();
		while (botones.hasMoreElements()){
			JButton botonActual = (JButton) botones.nextElement();
			botonActual.setSelected(false);
			botonActual.setForeground(Color.BLACK);
		}
	}
	
	private void habilitarBotones(boolean flag){
		Enumeration<AbstractButton> botones = botonesDelPanel.getElements();
		while (botones.hasMoreElements()){
			JButton botonActual = (JButton) botones.nextElement();
			botonActual.setEnabled(flag);
		}
	}
	
	private void deseleccionarPuntos() {
		if (this.puntosSeleccionados != null)
			this.puntosSeleccionados.clear();
	}
	
	static void dibujarLineaEntrePuntos(Coordinate inicio, Coordinate destino, Color color, JCheckBox chckbxPeaje, JMapViewer map) {
		List<Coordinate> ruta = new ArrayList<Coordinate>(Arrays.asList(inicio, destino, destino));
		MapPolygonImpl rutaMap = new MapPolygonImpl(ruta);
		// bug
		if (chckbxPeaje.isSelected() && color==null){
			rutaMap.setName("Peaje");
			rutaMap.setColor(Color.MAGENTA);
		}
		else if (color!=null){
			rutaMap.setColor(color);
		}
		else
			rutaMap.setColor(Color.GREEN);
		map.addMapPolygon(rutaMap);
	}
	
	/**
	 * Este metodo chequea si un punto marcado en el mapa esta siendo clickeado por el mouse.
	 * @param punto Es un MapMarker proveniente del JMapViewer
	 */
	static boolean fueClickeado(MapMarker punto){
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
	
	private void mostrarError(String message){
		JOptionPane.showMessageDialog(frame, message);
	}
	
	
	
	
	
}
