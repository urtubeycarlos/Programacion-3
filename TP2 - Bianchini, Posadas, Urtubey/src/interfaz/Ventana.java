package interfaz;

import mapa.Coordenada;
import mapa.MapaRutas;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.openstreetmap.gui.jmapviewer.tilesources.MapQuestOpenAerialTileSource;

import java.awt.List;
import java.awt.TextField;
import java.awt.Label;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Choice;
import java.awt.Checkbox;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.event.ActionEvent;

public class Ventana {

	private JFrame frame;
	private List lista_ciudades;
	private Choice selector_ciudad1, selector_ciudad2, cant_peajes;
	MapaRutas mapa = new MapaRutas();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana window = new Ventana();
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
	public Ventana() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		DibujadorMapa canvas = new DibujadorMapa();
		canvas.setBounds(10, 10, 764, 300);
		canvas.setVisible(true);
		frame.getContentPane().add(canvas);
		
		lista_ciudades = new List();
		lista_ciudades.setBounds(10, 437, 368, 115);
		frame.getContentPane().add(lista_ciudades);
		
		TextField text_nombreCiudad = new TextField();
		text_nombreCiudad.setBounds(10, 409, 156, 22);
		frame.getContentPane().add(text_nombreCiudad);
		
		TextField text_latitud = new TextField();
		text_latitud.setBounds(172, 409, 100, 22);
		frame.getContentPane().add(text_latitud);
		
		TextField text_longitud = new TextField();
		text_longitud.setBounds(278, 409, 100, 22);
		frame.getContentPane().add(text_longitud);
		
		Label label_nombre = new Label("Nombre");
		label_nombre.setBounds(10, 381, 62, 22);
		frame.getContentPane().add(label_nombre);
		
		Label label_lat = new Label("Latitud");
		label_lat.setBounds(172, 381, 62, 22);
		frame.getContentPane().add(label_lat);
		
		Label label_long = new Label("Longitud");
		label_long.setBounds(278, 381, 62, 22);
		frame.getContentPane().add(label_long);
		
		Button agregar_ciudad = new Button("Agregar Ciudad");
		agregar_ciudad.setBounds(384, 409, 110, 22);
		agregar_ciudad.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Coordenada c = new Coordenada( text_nombreCiudad.getText(), new Double( text_latitud.getText() ), new Double( text_longitud.getText() ) );
				mapa.agregarCoordenada(c);
				String ciudad = lista_ciudades.getItemCount() + ": " + c.toString(); 
				boolean esta = false;
				for( String ciudad_en_lista:lista_ciudades.getItems() )
					if( ciudad_en_lista.substring(3).equals( ciudad.substring(3) ) )
						esta = true;
				if ( !esta )
					lista_ciudades.add( ciudad );
				actualizarSelectorCiudades();
			}
		});
		frame.getContentPane().add(agregar_ciudad);
		
		Button eliminar_ciudad = new Button("Eliminar");
		eliminar_ciudad.setBounds(384, 441, 110, 22);
		eliminar_ciudad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( lista_ciudades.getItemCount() != 0  && lista_ciudades.getSelectedItem() != null )
					lista_ciudades.remove(lista_ciudades.getSelectedIndex());
				actualizarSelectorCiudades();
			}
		});
		frame.getContentPane().add(eliminar_ciudad);
		
		Button limpiar_lista = new Button("Limpiar");
		limpiar_lista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lista_ciudades.removeAll();
			}
		});
		limpiar_lista.setBounds(384, 469, 110, 22);
		frame.getContentPane().add(limpiar_lista);
		
		Button set_destino = new Button("Marcar Destino");
		set_destino.setBounds(384, 530, 110, 22);
		frame.getContentPane().add(set_destino);
		
		Button set_origen = new Button("Marcar Origen");
		set_origen.setBounds(384, 502, 110, 22);
		frame.getContentPane().add(set_origen);
		
		List lista_vecinos = new List();
		lista_vecinos.setBounds(537, 437, 237, 115);
		frame.getContentPane().add(lista_vecinos);
		
		selector_ciudad1 = new Choice();
		selector_ciudad1.setBounds(537, 411, 115, 20);
		frame.getContentPane().add(selector_ciudad1);
		
		selector_ciudad2 = new Choice();
		selector_ciudad2.setBounds(658, 411, 115, 20);
		frame.getContentPane().add(selector_ciudad2);

		cant_peajes = new Choice();
		cant_peajes.setBounds(10, 316, 50, 20);
		frame.getContentPane().add(cant_peajes);
		
		Checkbox tiene_peaje = new Checkbox("Peaje");
		tiene_peaje.setBounds(658, 381, 95, 22);
		frame.getContentPane().add(tiene_peaje);
		
		Button set_vecinos = new Button("Setear Vecinos");
		set_vecinos.setBounds(537, 381, 110, 22);
		set_vecinos.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Coordenada c1 = mapa.getCoordenada( new Integer(selector_ciudad1.getSelectedItem()) );
				Coordenada c2 = mapa.getCoordenada( new Integer(selector_ciudad2.getSelectedItem()) );
				
				if( !c1.equals(c2) ){
					mapa.agregarRuta(c1, c2, tiene_peaje.getState() );
					actualizarPeajesMaximos();
				} else {
					throw new IllegalArgumentException("No se puede setear una ruta entre la misma ciudad!");
				}

			}
		});
		frame.getContentPane().add(set_vecinos);
		
		Label text_peajes = new Label("Peajes Maximos");
		text_peajes.setBounds(66, 314, 100, 22);
		frame.getContentPane().add(text_peajes);
		
		Button obtener_camino_minimo = new Button("Obtener Camino Minimo");
		obtener_camino_minimo.setActionCommand("Obtener Camino Minimo");
		obtener_camino_minimo.setBounds(172, 314, 150, 22);
		frame.getContentPane().add(obtener_camino_minimo);
	}
	
	public void actualizarSelectorCiudades(){
		selector_ciudad1.removeAll();
		selector_ciudad2.removeAll();
		for( int i=0; i<lista_ciudades.getItemCount(); i++ ){
			selector_ciudad1.add( "" + i );
			selector_ciudad2.add( "" + i );
		}
	}
	
	public void actualizarPeajesMaximos(){
		cant_peajes.removeAll();
		for( int i=0; i<=mapa.cantPeajes(); i++ )
			cant_peajes.add( "" + i );
			
	}
}
