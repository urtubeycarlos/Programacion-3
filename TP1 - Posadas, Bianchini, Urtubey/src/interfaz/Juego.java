package interfaz;

import java.awt.EventQueue;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;

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
  private Pieza[] _arregloPiezas;
  private int _dimTablero, _dimPiezas;
  private String _directorioImagenes;
  private Imagen _imagen;
  private Tablero _tablero;

  
  //TODO: La DB, solo hay que cargar los .java y llamarlo desde el Tablero para obtener los puntajes.
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
		_directorioImagenes = "src/datos/imagenes";
		setearImagenAleatoria();
		Point[] posiciones = calcularPosiciones(_dimTablero, _dimPiezas);
		setearPiezas(posiciones);
		_tablero = new Tablero();
		_tablero.setearPiezas(_arregloPiezas);
		if( _tablero.gano() )
			System.out.println("Gano!");
  }
  
  //FIXME: Hay que generar bien las imagenes, es decir, la relacion indice/imagen debe estar ok y a su vez deben estar desordenados. Estaba hecho pero solo dios sabe en que archivo de cual proyecto esta :v
  private void setearPiezas(Point[] posiciones){
	Imagen[] imagenesPiezas  = _imagen.dividirImagen(_dimTablero);
	_arregloPiezas = new Pieza[_dimTablero*_dimTablero];
	for(int i=0; i<_arregloPiezas.length; i++){
		_arregloPiezas[i] = new Pieza(i, imagenesPiezas[i], posiciones[i], _dimPiezas, _dimPiezas);
		_frame.add(_arregloPiezas[i]);
	}
  }

  private void setearImagenAleatoria() throws IOException{
	  File f = new File(_directorioImagenes);
	  File[] listaImagenes = f.listFiles();
	  //String s = listaImagenes[_genNumeroRandom.nextInt(listaImagenes.length)].getPath();
	  //_imagen = new Imagen(s);
	  _imagen = new Imagen(listaImagenes[_genNumeroRandom.nextInt(listaImagenes.length)]);
	  _imagen.redimensionarImagen(_dimPiezas*_dimTablero, _dimPiezas*_dimTablero);
  }
  
  private Point[] calcularPosiciones(int dim, int dist){
	  int tam = dim*dim;
		
		Point[] posiciones = new Point[tam];
		
		int[] orden = new int[tam];
		
		for(int i=0;i<tam;i++){
			
			orden[i] = i;
		}
		
		orden = desordenarPosiciones(orden);
		
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