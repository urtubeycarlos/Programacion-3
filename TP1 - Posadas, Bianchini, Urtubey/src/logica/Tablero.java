package logica;

import interfaz.Pieza;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Tablero {

	ArrayList<Pieza> _piezas;
	Thread _hiloDeControl;
	Pieza _piezaVacia;
	
	//FIXME: ES mejor usar un arreglo estatico.
	public Tablero(){
		_piezas = new ArrayList<Pieza>();
	}
	
	public void setearPiezas(ArrayList<Pieza> piezas){
		for(Pieza p:piezas){
			_piezas.add(p);
			if(p.ID == 0)
				_piezaVacia = p;
		} setearThreadDeControl();
		iniciarHiloDeControl();
	}
	
	public synchronized boolean gano(){
//		for(int i=0; i<_piezas.size(); i++)
//			if ( i != _piezas.get(i).ID)
//				return false;
//		return true;
		for(int i=0; i<_piezas.size()-1; i++){
			if( i != _piezas.get(i).ID){
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
								System.out.println("Entro!");
								swapearPiezas(p, _piezaVacia);
								System.out.println(_piezas);
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
	
	private void swapearPiezas(Pieza p1, Pieza p2){
		
		Rectangle rectPieza1 = new Rectangle(p1.getBounds());
		Rectangle rectPieza2 = new Rectangle(p2.getBounds());
		p2.setBounds(rectPieza1);
		p1.setBounds(rectPieza2);
		
		int indiceP1 = _piezas.indexOf(p1);
		int indiceP2 = _piezas.indexOf(p2);
		_piezas.remove(p1); 
		_piezas.remove(p2);

		if(indiceP1 < indiceP2 ){
			_piezas.add(indiceP1, p2);
			_piezas.add(indiceP2, p1);
		} else {
			_piezas.add(indiceP2, p1);
			_piezas.add(indiceP1, p2);
		}
		
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
	
}
