package Dibujo;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;
public class PruebasDibujo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Marco mimarco = new Marco();
	}

}

class Marco extends JFrame{
	
	
	public Marco() {
		
		this.setBounds(300,200,500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new Panel());
		this.setVisible(true);
	}
	
}

class Panel extends JPanel{
	
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		/*
		
		g2.setStroke(new BasicStroke(10));
		
		g2.drawLine(15, 15, 15, 15);
		
		*/
		
		int ancho = g.getClipBounds().width;
		int alto = g.getClipBounds().height;
		
		
		Shape circle = new Ellipse2D.Double(50,50,ancho,alto);
		
		g2.fill(circle);
		
		g2.draw(circle);
		
		
	}
	
}
