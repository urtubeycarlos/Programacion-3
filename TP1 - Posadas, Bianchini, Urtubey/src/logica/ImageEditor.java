package logica;

import interfaz.Pixel;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class ImageEditor{
	/**
	 * 
	 */
	
	private Pixel[][] pixels;
	private int height;
	private int width;
	
	
	public ImageEditor(Image image){
		
		BufferedImage im = (BufferedImage) image;
		
		width = im.getWidth();
		height = im.getHeight();
		pixels = new Pixel[height][width];
		
		for(int i=0; i<height; i++){
			
			for(int j=0; j<width; j++){
				
				pixels[i][j]=new Pixel(im.getRGB(j, i));
			}
		}
	}

	
	public ImageEditor(String archive){
		
		File file= new File(archive);
		try {
			BufferedImage image = ImageIO.read(file);
			width = image.getWidth();
			height = image.getHeight();
			pixels = new Pixel[height][width];
			for(int i=0; i<height; i++)
			{
				for(int j=0; j<width; j++)
				{
					pixels[i][j]=new Pixel(image.getRGB(j, i));
				}
			}
		} catch (IOException e) {
			System.err.println("No se encontró el archivo " + archive);
			System.exit(0);
		}
	}
	/**
	 * Guarda la imagen en un archivo según el formato indicado
	 * @param archive El nombre de archivo, e.g., "lena.bmp"
	 * @param formato El formato de imagen: "bmp", "png", "jpg", etc. 
	 */
	
	public Image getImage(){
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for(int i=0; i<height; i++)
		{
			for(int j=0; j<width; j++)
				image.setRGB(j,i, pixels[i][j].aInt());
		}
		
		return image;
	}
	
	public void save(String archive, String format)
	{
		BufferedImage image = (BufferedImage)getImage();
		
		try {
			ImageIO.write(image, format, new File(archive));
		} catch (IOException e) {
			System.err.println("Error al guardar en el archivo " + archive);
			e.printStackTrace();
		}
	}
	
	public void blush(int quantity){
		
		for (int i = 0; i<this.pixels.length; i++){
			
			for (int i2 = 0; i2<this.pixels[i].length; i2++){
				
				if (this.pixels[i][i2].red+quantity>255)		this.pixels[i][i2].red = 255;
				
				else if (this.pixels[i][i2].red+quantity<0) 	this.pixels[i][i2].red = 0;
				
				else											this.pixels[i][i2].red+=quantity;
			}
		}
	}
	
	public void brightness(int quantity){
		
		for (int i = 0; i<this.pixels.length; i++){
			
			for (int i2 = 0; i2<this.pixels[i].length; i2++){
				
				if 	(this.pixels[i][i2].red+quantity>255)		this.pixels[i][i2].red = 255;
				
				else if (this.pixels[i][i2].red+quantity<0) 	this.pixels[i][i2].red = 0;
				
				else											this.pixels[i][i2].red+=quantity;
				
				if 	(this.pixels[i][i2].green+quantity>255)		this.pixels[i][i2].green = 255;
				
				else if (this.pixels[i][i2].green+quantity<0) 	this.pixels[i][i2].green = 0;
				
				else											this.pixels[i][i2].green+=quantity;
				
				if 	(this.pixels[i][i2].blue+quantity>255)		this.pixels[i][i2].blue = 255;
				
				else if (this.pixels[i][i2].blue+quantity<0) 	this.pixels[i][i2].blue = 0;
				
				else											this.pixels[i][i2].blue+=quantity;
			}
		}
	}
	
	public void toGray(){
		
		for (int i = 0; i<this.pixels.length; i++){
			
			for (int i2 = 0; i2<this.pixels[i].length; i2++){
				
				this.pixels[i][i2].red = (int) ((this.pixels[i][i2].red* 0.3) + (this.pixels[i][i2].green*0.6) + (this.pixels[i][i2].blue*0.1));
				this.pixels[i][i2].green = this.pixels[i][i2].red;
				this.pixels[i][i2].blue = this.pixels[i][i2].red;
			}
		}
	}

	public void invest(){
		
		for (int i = 0; i<this.pixels.length; i++){
			
			for (int i2 = 0; i2<this.pixels[i].length; i2++){
				
				this.pixels[i][i2].red = 255-this.pixels[i][i2].red;
				this.pixels[i][i2].green = 255-this.pixels[i][i2].green;
				this.pixels[i][i2].blue = 255-this.pixels[i][i2].blue;
			}
		}
	}
	
	public void mirror(){

		for (int i = 0; i<this.pixels.length; i++){
			
			for (int i2 = 0; i2<this.pixels[i].length/2; i2++){
				
				int redPrevious = this.pixels[i][i2].red;
				this.pixels[i][i2].red = this.pixels[i][this.pixels[i].length-1-i2].red;
				this.pixels[i][this.pixels[i].length-1-i2].red = redPrevious;
				
				int greenPrevious = this.pixels[i][i2].green;
				this.pixels[i][i2].green = this.pixels[i][this.pixels[i].length-1-i2].green;
				this.pixels[i][this.pixels[i].length-1-i2].green = greenPrevious;
				
				int bluePrevious = this.pixels[i][i2].blue;
				this.pixels[i][i2].blue = this.pixels[i][this.pixels[i].length-1-i2].blue;
				this.pixels[i][this.pixels[i].length-1-i2].blue = bluePrevious;
			}
		}
	}
	
	public void turnRight(){
		
		Pixel[][] matrix;
		
		matrix = new Pixel[this.width][this.height];

		for (int i = 0; i<matrix.length; i++){
			
			for (int i2 = 0; i2<matrix[i].length; i2++){
				
				matrix[i][i2] = this.pixels[this.height-1-i2][i];

			}
		}
	}
	
	public Image[] trim(int dimemsion){
				
		int absHeight = height/dimemsion;
		int absWidth = width/dimemsion;
		
		BufferedImage[] ret = new BufferedImage[dimemsion*dimemsion];
		
		int startX = 0;
		int startY = 0;
		int endX = absWidth;
		int endY = absHeight;
		
		int accountant = 0;
		
		for(int h=0;h<dimemsion*dimemsion;h++){
		
			BufferedImage image = new BufferedImage(absWidth, absHeight, BufferedImage.TYPE_INT_RGB);
			
			int i2 = 0;
			for(int i=startY; i<endY; i++)
			{
				int j2 = 0;
				for(int j=startX; j<endX; j++){
					image.setRGB(j2,i2, pixels[i][j].aInt());
					j2++;
				}
				i2++;
			}			
			
			ret[h] = image;
			
			startX += absWidth;
			endX += absWidth;
			
			accountant++;
			
			if(accountant>=dimemsion){
				
				startY += absHeight;
				endY += absHeight;
				startX = 0;
				endX = absWidth;
				accountant = 0;
			}
		}
		
		return ret;
	}
	
	public ImageIcon[] cortarImagen(int dim){
		
		int altoAbs = width/dim;
		int anchoAbs = height/dim;
		
		ImageIcon[] ret = new ImageIcon[dim*dim];
		
		int iniX = 0;
		int iniY = 0;
		int finX = anchoAbs;
		int finY = altoAbs;
		
		int cont = 0;
		
		for(int h=0;h<dim*dim;h++){
		
			BufferedImage image = new BufferedImage(anchoAbs, altoAbs, BufferedImage.TYPE_INT_RGB);
			
			int i2 = 0;
			for(int i=iniY; i<finY; i++)
			{
				int j2 = 0;
				for(int j=iniX; j<finX; j++){
					image.setRGB(j2,i2, pixels[i][j].aInt());
					j2++;
				}
				i2++;
			}			
			
			ImageIcon im = new ImageIcon(image);
			
			ret[h] = im;
			
			iniX += anchoAbs;
			finX += anchoAbs;
			
			cont++;
			
			if(cont>=dim){
				
				iniY += altoAbs;
				finY += altoAbs;
				iniX = 0;
				finX = anchoAbs;
				cont = 0;
			}
		}
		
		return ret;
	}
	
	public void resize(int X, int Y){
		
		BufferedImage image = new BufferedImage(X, Y, BufferedImage.TYPE_INT_RGB);
		
		double xd = (double)(X)/width;
		double yd = (double)(Y)/height;
		
		for(int i=0; i<Y; i++)
		{
			for(int j=0; j<X; j++){				
				image.setRGB(j,i, pixels[(int) (i/yd)][(int) (j/xd)].aInt());
			}
		}
		
		ImageEditor ret = new ImageEditor(image);
		
		this.width = ret.width;
		this.height = ret.height;
		this.pixels = ret.pixels;
	}
	
	public static void main(String[] args) {
		
		ImageEditor im = new ImageEditor("imagen0.jpg");
		
		im.resize(100, 200);
		
		im.save("prueba.jpg", "jpg");
	}
}