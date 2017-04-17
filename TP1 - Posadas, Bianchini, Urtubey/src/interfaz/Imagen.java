package interfaz;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Imagen extends ImageIcon {

	private static final long serialVersionUID = 5877473038430233580L;
	BufferedImage _bImg;
	Pixel[][] _pixeles;

	public Imagen(File archivo) throws IOException{
	    super(archivo.getPath());
	    _bImg = ImageIO.read(archivo);
		setPixeles();
	}
	  
	private Imagen(Image image){
	  	super(image);
	  	_bImg = (BufferedImage) image;
	  	setPixeles();
	}

	private void setPixeles() {
		_pixeles = new Pixel[getIconHeight()][getIconWidth()];
		for(int i=0; i<getIconHeight(); i++)
		for(int j=0; j<getIconWidth(); j++)
			_pixeles[i][j] = new Pixel(_bImg.getRGB(i, j));
	}
	 
	public void redimensionarImagen(int ancho, int alto){
		BufferedImage image = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
		
		double xd = (double) ancho/getIconWidth();
		double yd = (double) alto/getIconHeight();
		
		for(int i=0; i<alto; i++){		
			for(int j=0; j<ancho; j++){				
				image.setRGB(j,i, _pixeles[(int) (i/yd)][(int) (j/xd)].aInt());
			}
		}
		_bImg = image;
		setImage(image);
		setPixeles();
		
	}
	
	public Imagen[] dividirImagen(int dimension){
		
		int absHeight = getIconHeight()/dimension;
		int absWidth = getIconWidth()/dimension;
		
		BufferedImage[] aux = new BufferedImage[dimension*dimension];
			
		int startX = 0;
		int startY = 0;
		int endX = absWidth;
		int endY = absHeight;
		
		int accountant = 0;
		
		for(int h=0;h<dimension*dimension;h++){
			
			BufferedImage image = new BufferedImage(absWidth, absHeight, BufferedImage.TYPE_INT_RGB);
				
			int i2 = 0;
			for(int i=startY; i<endY; i++)
			{
				int j2 = 0;
				for(int j=startX; j<endX; j++){
					image.setRGB(j2,i2, _pixeles[i][j].aInt());
					j2++;
				}
				i2++;
			}			
				
			aux[h] = image;
				
			startX += absWidth;
			endX += absWidth;
			
			accountant++;
				
			if(accountant>=dimension){
					
				startY += absHeight;
				endY += absHeight;
				startX = 0;
				endX = absWidth;
				accountant = 0;
			}
		}
			
			Imagen[] ret = new Imagen[dimension*dimension];
			for(int i=0; i<ret.length; i++)
				ret[i] = new Imagen(aux[i]);
			return ret;
		}  
}