package logica;

import interfaz.Pieza;
//import datos.GestorPuntajes;

import java.awt.Point;
import java.awt.Rectangle;
//import java.sql.SQLException;
import java.util.ArrayList;

public class Tablero {

	private Pieza _piezaVacia;
	private Pieza[] _piezas;
	private Thread _hiloDeControl;
	public static int _cantMovimientos; 
//	private GestorPuntajes _gestorPuntajes;

	public Tablero() throws Exception{
		_cantMovimientos = 0;
//		_gestorPuntajes = new GestorPuntajes();
	}
	
	public void setearPiezas(ArrayList<Pieza> piezas){
		_piezas = new Pieza[piezas.size()];
		for(int i=0; i<piezas.size(); i++){
			_piezas[i] = piezas.get(i);
			if( piezas.get(i).ID == 0 )
				_piezaVacia = piezas.get(i);
		} setearThreadDeControl();
		iniciarHiloDeControl();
	}
	
	public synchronized boolean gano(){
//		for(int i=0; i<_piezas.size(); i++)
//			if ( i != _piezas.get(i).ID)
//				return false;
//		return true;
		for(int i=0; i<_piezas.length; i++){
			if( i != _piezas[i].ID){
				return false;
			}
		} return true;
	}
	
	private void setearThreadDeControl() {
		_hiloDeControl = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while( !gano() ){
					for(Pieza p:_piezas){
						if( p.isTouched() ){
							if( estanAlineadas(p, _piezaVacia) ){
								swapearPiezas(p, _piezaVacia);
								_cantMovimientos++;
							} p.setTouched(false);
						}
					}
				} System.out.println(_piezas);
				finalizarJuego();
			}
		}); 
	}
	
	private void iniciarHiloDeControl(){
		_hiloDeControl.start();
	}
	
	public void finalizarJuego(){
		_hiloDeControl.interrupt();
	}
	
	public static int obtenerPuntaje(){
		return _cantMovimientos;
	}
	
//	public boolean guardarPuntaje(String nombre){
//		try {
//			_gestorPuntajes.insertarPuntaje(nombre, obtenerPuntaje());
//			return true;
//		} catch (SQLException e) {
//			return false;
//		}
//	}
	
	private void swapearPiezas(Pieza p1, Pieza p2){
		
		Rectangle rectPieza1 = new Rectangle(p1.getBounds());
		Rectangle rectPieza2 = new Rectangle(p2.getBounds());
		p2.setBounds(rectPieza1);
		p1.setBounds(rectPieza2);
		
		int indiceP1 = obtenerIndice(p1);
		int indiceP2 = obtenerIndice(p2);
		
		Pieza temp = _piezas[indiceP1];
		_piezas[indiceP1] = _piezas[indiceP2];
		_piezas[indiceP2] = temp;
		
	}
	
	
	private boolean estanAlineadas(Pieza pieza1, Pieza pieza2){
		int margenError = 5;
		Point posPieza1 = new Point( (int) pieza1.getBounds().getCenterX(), (int) pieza1.getBounds().getCenterY());
		Point posPieza2 = new Point( (int) pieza2.getBounds().getCenterX(), (int) pieza2.getBounds().getCenterY());
		if ( distancia(posPieza1, posPieza2) <= pieza1.getWidth() + margenError )
			return true;
		return false;
	}
	
	private int distancia(Point p1, Point p2){
		double ret = Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2);
		ret = Math.sqrt(ret);
		return (int) ret;
	}
	
	private int obtenerIndice(Pieza p){
		for(int i=0; i<_piezas.length; i++){
			if(_piezas[i].ID == p.ID){
				return i;
			}
		} return -1;
	}
	
}
