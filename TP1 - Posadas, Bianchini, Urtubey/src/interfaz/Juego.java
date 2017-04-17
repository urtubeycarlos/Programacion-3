package interfaz;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import logica.ImageEditor;

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
  private Pieza[] _arregloPiezas;
  private int _dimTablero, _dimPiezas;
  private String _directorioImagenes;
  private ImageEditor _imagen;

  public Juego() throws IOException {
	  initialize();
  }

  public void initialize() throws IOException{
		_dimTablero = 4;
		_dimPiezas = 150;
	  	_frame = new JFrame();
		_frame.setBounds(100, 100, (_dimTablero*_dimPiezas)+15, (_dimTablero*_dimPiezas)+37);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.getContentPane().setLayout(null);
	  	
		_genNumeroRandom = new Random();
		System.out.println("Genero el random");
		_directorioImagenes = "src/datos/imagenes";
		System.out.println("Genero el dir de imagenes");
		setearImagenAleatoria();
		System.out.println("Seteo Imagen");
		Point[] posiciones = calcularPosiciones(_dimTablero, _dimPiezas);
		System.out.println("Genero las posiciones: " + Arrays.asList(posiciones));
		generarPiezas(posiciones);
		System.out.println("Genero las piezas");
		//agregarPiezasAlJuego();
		System.out.println("Agrego las piezas");
		
  }
  
  private void generarPiezas(Point[] posiciones){
	ImageIcon[] imagenesPiezas  = _imagen.cortarImagen(_dimPiezas);
	_arregloPiezas = new Pieza[_dimTablero*_dimTablero];
	for(int i=0; i<_arregloPiezas.length; i++){
		_arregloPiezas[i] = new Pieza(i, imagenesPiezas[i], posiciones[i], _dimPiezas, _dimPiezas);
		_frame.add(_arregloPiezas[i]);
	}
  }

  private void setearImagenAleatoria() throws IOException{
	  File f = new File(_directorioImagenes);
	  File[] listaImagenes = f.listFiles();
	  String s = listaImagenes[_genNumeroRandom.nextInt(listaImagenes.length)].getPath();
	  System.out.println(s);
	  _imagen = new ImageEditor(s);
	  System.out.println("Dim de la imagen" + _dimPiezas*_dimTablero);
	  _imagen.resize(_dimPiezas*_dimTablero, _dimPiezas*_dimTablero);
  }
  
  private void agregarPiezasAlJuego(){
	  for(Pieza p:_arregloPiezas)
		  //_frame.getContentPane().add(p);
		  _frame.getContentPane().add(p);
  }
  
  private Point[] calcularPosiciones(int dim, int dist){
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
  
  private int[] desordenarPosiciones(int[] posiciones){
	  	int[] ret = new int[posiciones.length];
		
		if(posiciones.length-1<0) return ret;
		
		int corte = _genNumeroRandom.nextInt(posiciones.length);
		
		ret[0] = posiciones[corte];
		
		if(posiciones.length-1==0) return ret;
		
		int[] siguiente = new int[posiciones.length-1];
		
		int j = 0;		
				
		for(int i=0; i<posiciones.length;i++){

			if(i!=corte){
				siguiente[j] = posiciones[i];
				j++;
			}
		}			
		
		int[] nuevo = desordenarPosiciones(siguiente);
		
		j=1;
		
		for(int i=0;i<nuevo.length;i++){
			
			ret[j] = nuevo[i];
			j++;
		}
		
		return ret;
  }
  
  //TODO: Hacer el Thread cuando Sebas tenga lo de la ventana listo
  @SuppressWarnings("unused")
private void setearThredDeControl(){
	  return;
  }
  
}