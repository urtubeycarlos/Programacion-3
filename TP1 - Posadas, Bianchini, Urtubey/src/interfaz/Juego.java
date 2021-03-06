package interfaz;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import logica.ArrayListRandom;
import logica.Tablero;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Juego {

  private JFrame _frame;
  
  public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Juego window = new Juego();
					window._frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
  
  private Random _genNumeroRandom;
  private ArrayList<Pieza> _listaPiezas;
  private int _dimTablero, _dimPiezas;
  private String _directorioImagenes;
  private Imagen _imagen;
  private Tablero _tablero;
  private Thread _hiloDeControl;
  private Point[] _posiciones;

  public Juego() throws Exception {
	  initialize();
  }

  public void initialize() throws Exception{
	  
		_genNumeroRandom = new Random();
		_dimTablero = 4;
		_dimPiezas = 128;
		_listaPiezas = new ArrayList<Pieza>();
		
	  	_frame = new JFrame();
		_frame.setBounds(160, 90, (_dimTablero*_dimPiezas)+2, (_dimTablero*_dimPiezas)+_dimPiezas/2);
		_frame.setLocationRelativeTo(null);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	_frame.setResizable(false);
	  	_frame.getContentPane().setLayout(null);
	  	
	  	JMenuBar menuBar = new JMenuBar();
	  	menuBar.setBounds(0, 0, 93, 21);
	  	_frame.getContentPane().add(menuBar);
	  	
	  	JMenu menuJuego = new JMenu("Archivo");
	  	menuBar.add(menuJuego);
	  	
	  	JMenuBar menuBar2 = new JMenuBar();
		menuBar.setToolTipText("");
		menuBar.setBounds(0, 0, 90, 21);
		_frame.getContentPane().add(menuBar2);
		
		JMenu mnNewMenu2 = new JMenu("Ver");
		menuBar.add(mnNewMenu2);
		
		JMenuItem mntmPuntajes = new JMenuItem("Puntajes");
		mnNewMenu2.add(mntmPuntajes);
		
		mntmPuntajes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Puntajes puntaje = new Puntajes(_tablero);
				puntaje.mostrar();
			}
		});
	  		   	
	  	JMenuItem mntmSalir = new JMenuItem("Salir");
	  	menuJuego.add(mntmSalir);
	  	
	  	mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
	  	
		_directorioImagenes = "src/datos/imagenes";
		seleccionarImagenAleatoria();
		_posiciones = generarPosiciones(_dimTablero, _dimPiezas);
		setearPiezas();
		_tablero = new Tablero();
		_tablero.setearPiezas(_listaPiezas);
		setearHiloDeControl();

  }
  
  /**
   * 
   * Divide una imagen, seleccionada previamente de forma aleatoria, y la divide en tantas imagenes como piezas tenga el juego.
   * Finalmente toma una de las imagenes obtenidas de forma aleatoria y se la asigna a una pieza junto con su ID correspondiente y
   * una posici�n del arreglo de posiciones.
   * 
   */
  private void setearPiezas(){
	Imagen[] imagenesPiezas  = _imagen.dividirImagen(_dimTablero);
	ArrayListRandom<Integer> listaIDsPosibles = new ArrayListRandom<Integer>();
	for(int IDs=0; IDs<_posiciones.length; IDs++){
		listaIDsPosibles.add(IDs);
	}
	
// //Algoritmo para no mezclar las piezas de la imagen.
//	
//	int i = 0;
//	while( i < _posiciones.length ){
//		_listaPiezas.add(i, new Pieza(i, imagenesPiezas[i], _posiciones[i], _dimPiezas, _dimPiezas));
//		_frame.add(_listaPiezas.get(i));
//		i++;
//	}
	
	int i = 0;
	while( !listaIDsPosibles.isEmpty() ){
		int idRandom = listaIDsPosibles.removeRandom();
		_listaPiezas.add(i, new Pieza(idRandom, imagenesPiezas[idRandom], _posiciones[i], _dimPiezas, _dimPiezas));
		_frame.getContentPane().add(_listaPiezas.get(i));
		i++;
	}
	
  }

  /**
   * 
   * Devuelve una imagen de forma aleatoria tomando del directorio establecido internamente.
   * 
   * @throws IOException
   * 
   */
  private void seleccionarImagenAleatoria() throws IOException{
	  File f = new File(_directorioImagenes);
	  File[] listaImagenes = f.listFiles();
	  _imagen = new Imagen(listaImagenes[_genNumeroRandom.nextInt(listaImagenes.length)]);
	  _imagen.redimensionarImagen(_dimPiezas*_dimTablero, _dimPiezas*_dimTablero);
  }
  
  
  /**
   * Setea un hilo de ejecuci�n que se encarga de controlar constantemente si el jugador gano.
   * En caso de que el jugador gane interrumpe la ejecuci�n y muestra la ventana para que el jugador
   * muestre su nombre.
   */
  private void setearHiloDeControl(){
	  _hiloDeControl = new Thread(new Runnable() {
		@Override
		public void run() {
			while( !_tablero.gano() ){
				continue;
			}
			_hiloDeControl.interrupt();
			VentanaVictoria _ventanaEmergentePuntos = new VentanaVictoria(_tablero);
			_ventanaEmergentePuntos.mostrar();
		}
	  });
	  
	  _hiloDeControl.start();
  }
  
  /**
   * 
   * @param La cantidad de piezas deseadas para el tablero.
   * @param El tama�o de cada pieza.
   * @return Devuelve un arreglo de posiciones generado a base de calculos realizados con los parametros ingresados.
   * 
   */
  private Point[] generarPosiciones(int dim, int dist){
	  	int tam = dim*dim;
		Point[] posiciones = new Point[tam];
		int[] orden = new int[tam];
		
		for(int i=0;i<tam;i++){
			orden[i] = i;
		}
		
		int x = 0;
		int y = 25;
		int cont = 0;
		
		for(int i=0;i<tam;i++){
			posiciones[orden[i]]= new Point(x,y);
			x += dist;
			cont++;
			if(cont>dim-1){
				y += dist;
				x = 0;
				cont = 0;
			}
		} return posiciones;
  }
}