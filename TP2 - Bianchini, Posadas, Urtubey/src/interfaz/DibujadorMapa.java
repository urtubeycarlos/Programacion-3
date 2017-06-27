package interfaz;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.PaintContext;
import java.awt.Point;

import java.util.Random;

import javax.swing.JLabel;

import mapa.Coordenada;
import mapa.Mapa;

import java.util.HashMap;
import java.util.List;

public class DibujadorMapa extends Canvas {

	private Random random;
	private final int ANCHO, ALTO;
	private double radioVertices;
	private HashMap<Integer, Point> mapa_puntos;
	
	public DibujadorMapa(){
		random = new Random();
		ANCHO = getWidth();
		ALTO = getHeight();
		radioVertices = 16;
		mapa_puntos = new HashMap<Integer, Point>();
		setVisible(true);
	}
	
	public void dibujarMapa(Mapa mapa){
		// TODO: Implementar
		throw new RuntimeException();
	}
	
	@Override
	public void paint(Graphics g){
		g.fillRect(12, 13, 12, 12);
	}
	
}

