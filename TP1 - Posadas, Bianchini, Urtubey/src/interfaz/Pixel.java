package interfaz;

public class Pixel {
	
	public int red;
	public int green;
	public int blue;
	
	public Pixel(int rgb) {
		this.red = (rgb & 0x00ff0000) >> 16;
	    this.green = (rgb & 0x0000ff00) >> 8;
	    this.blue = rgb & 0x000000ff;
	}
	
	public int aInt(){
		int rgb = 0;
		rgb += this.red;
		rgb = rgb << 8;
		rgb += this.green;
		rgb = rgb << 8;
		rgb += this.blue;
		return rgb;	
	}

}