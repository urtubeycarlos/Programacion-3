package interfaz;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Pieza extends JButton {

	private static final long serialVersionUID = -5308806731775293982L;
	private boolean isTouched = false;
	public final Integer ID;
	
	public Pieza(Integer ID, ImageIcon img, Point coordenadas, Integer ancho, Integer alto) {
		
		super( (ID.intValue() == 0 || img != null)? "":ID.toString());
		setBounds(coordenadas.x, coordenadas.y, ancho, alto);
		this.ID = ID;

		if(ID != 0){
			this.setIcon(img);
			
			addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent arg0) {
					setTouched(true);
				}
			});	
		} this.setIconTextGap(ancho);	

	}

	public synchronized void setTouched(boolean isTouched){
		this.isTouched = isTouched;
	}
	
	public synchronized boolean isTouched(){
		return this.isTouched;
	}
	
	@Override
	public String toString(){
		return "Pieza número " + ID.toString();
	}
	
}
