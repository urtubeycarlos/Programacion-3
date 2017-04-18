package logica;

import interfaz.Pieza;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Tablero {

	ArrayList<Pieza> _piezas;
	Thread _hiloDeControl;
	Pieza _piezaVacia;
	
	public Tablero(){
		_piezas = new ArrayList<Pieza>();
	}
	
	public void setearPiezas(Pieza[] piezas){
		for(Pieza p:piezas){
			_piezas.add(p);
			if(p.ID == 0)
				_piezaVacia = p;
		} setearThreadDeControl();
		iniciarHiloDeControl();
	}
	
	public synchronized boolean gano(){
		for(int i=0; i<_piezas.size(); i++)
			if ( i != _piezas.get(i).ID)
				return false;
		return true;
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
							} p.setTouched(false);
						}
					}
				}
			}
		}); 
	}
	
	private void iniciarHiloDeControl(){
		_hiloDeControl.start();
	}
	
	
	private void finalizarHiloDeControl() {
		_hiloDeControl.interrupt();
	}
	
	private void swapearPiezas(Pieza p1, Pieza p2){
		
		Rectangle temp = p2.getBounds();
		p2.setBounds(p2.getBounds());
		p1.setBounds(temp);
		
		int indiceP1 = _piezas.indexOf(p1);
		int indiceP2 = _piezas.indexOf(p2);
		_piezas.remove(p1); 
		_piezas.remove(p2);
		_piezas.add(indiceP1, p2);
		_piezas.add(indiceP2, p1);
		
	}
	
	
	private boolean estanAlineadas(Pieza pieza1, Pieza pieza2){
		Point posPieza1 = new Point( (int) pieza1.getBounds().getCenterX(), (int) pieza1.getBounds().getCenterY());
		Point posPieza2 = new Point( (int) pieza2.getBounds().getCenterX(), (int) pieza2.getBounds().getCenterY());
		if ( distancia(posPieza1, posPieza2) <= pieza1.getWidth() )
			return true;
		return false;
	}
	
	private int distancia(Point p1, Point p2){
		double ret = Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2);
		ret = Math.sqrt(ret);
		return (int) ret;
	}
	
}
