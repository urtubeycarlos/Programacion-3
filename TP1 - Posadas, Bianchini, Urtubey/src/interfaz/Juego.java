package interfaz;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.font.NumericShaper.Range;
import java.io.File;
import java.io.IOException;
import java.io.LineNumberInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JFrame;

import logica.ArrayListRandom;
import logica.Tablero;

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

  
  //TODO: La DB, solo hay que cargar los .java y llamarlo desde el Tablero para obtener los puntajes.
  public Juego() throws IOException {
	  initialize();
  }

  public void initialize() throws IOException{
	  
		_genNumeroRandom = new Random();
		_dimTablero = 4;
		_dimPiezas = 150;
		_listaPiezas = new ArrayList<Pieza>();
		
	  	_frame = new JFrame();
		_frame.setBounds(100, 100, (_dimTablero*_dimPiezas)+15, (_dimTablero*_dimPiezas)+37);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.getContentPane().setLayout(null);
	  	

		_directorioImagenes = "src/datos/imagenes";
		seleccionarImagenAleatoria();
		_posiciones = generarPosiciones(_dimTablero, _dimPiezas);
		System.out.println(Arrays.asList(_posiciones));
		setearPiezas();
		_tablero = new Tablero();
		System.out.println("Lista de piezas: " + _listaPiezas);
		_tablero.setearPiezas(_listaPiezas);
		setearHiloDeControl();

  }
  
  //FIXME: Hay que generar bien las imagenes, es decir, la relacion indice/imagen debe estar ok y a su vez deben estar desordenadas las posiciones. Estaba hecho pero solo dios sabe en que archivo de cual proyecto esta :v
  private void setearPiezas(){
	Imagen[] imagenesPiezas  = _imagen.dividirImagen(_dimTablero);
	ArrayListRandom<Integer> listaIDsPosibles = new ArrayListRandom<Integer>();
	for(int IDs=0; IDs<_posiciones.length; IDs++){
		listaIDsPosibles.add(IDs);
	}
	int i = 0;
	while( !listaIDsPosibles.isEmpty() ){
		int idRandom = listaIDsPosibles.removeRandom();
		_listaPiezas.add(i, new Pieza(idRandom, imagenesPiezas[idRandom], _posiciones[i], _dimPiezas, _dimPiezas));
		_frame.add(_listaPiezas.get(i));
		i++;
	}
  }

  private void seleccionarImagenAleatoria() throws IOException{
	  File f = new File(_directorioImagenes);
	  File[] listaImagenes = f.listFiles();
	  _imagen = new Imagen(listaImagenes[_genNumeroRandom.nextInt(listaImagenes.length)]);
	  _imagen.redimensionarImagen(_dimPiezas*_dimTablero, _dimPiezas*_dimTablero);
  }
  
  private void setearHiloDeControl(){
	  _hiloDeControl = new Thread(new Runnable() {
		@Override
		public void run() {
			while( !_tablero.gano() ){
				continue;
			} System.out.println("Gano!");
			_hiloDeControl.interrupt();
		}
	  });
	  
	  _hiloDeControl.start();
  }
  
  private Point[] generarPosiciones(int dim, int dist){
	  	int tam = dim*dim;
		
		Point[] posiciones = new Point[tam];
		
		int[] orden = new int[tam];
		
		for(int i=0;i<tam;i++){
			
			orden[i] = i;
		}
		
		//orden = desordenarPosiciones(orden);
		
		int x = 0;
		int y = 0;
		
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
		
		}
		return posiciones;
  }
//  
//  private int[] desordenarPosiciones(int[] posiciones){
//	  	int[] ret = new int[posiciones.length];
//		
//		if(posiciones.length-1<0) return ret;
//		
//		int corte = _genNumeroRandom.nextInt(posiciones.length);
//		
//		ret[0] = posiciones[corte];
//		
//		if(posiciones.length-1==0) return ret;
//		
//		int[] siguiente = new int[posiciones.length-1];
//		
//		int j = 0;		
//				
//		for(int i=0; i<posiciones.length;i++){
//
//			if(i!=corte){
//				siguiente[j] = posiciones[i];
//				j++;
//			}
//		}			
//		
//		int[] nuevo = desordenarPosiciones(siguiente);
//		
//		j=1;
//		
//		for(int i=0;i<nuevo.length;i++){
//			
//			ret[j] = nuevo[i];
//			j++;
//		}
//		
//		return ret;
//  }
  
}