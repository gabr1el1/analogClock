package Dibujo;

/****************************************************************************
 *   PROGRAMA    : RelojAnalogico.java
 *   AUTOR       : Gabriel Hernandez Grajeda
 *   FECHA       : 2022-04-04
 *   DESCRIPCION : Programa que dibuja un reloj analogico y permite cambiar la hora
 *   			   , pausar y continuar contando el tiempo
 *****************************************************************************/


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Conversiones.CoordenadasNormalizadas;
import Conversiones.PolaresACartesianas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;



public class RelojAnalogico {

	public static void main(String[] args) {
		
		MarcoReloj mimarco = new MarcoReloj();
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mimarco.setVisible(true);
		

	}

}

//Es necesario crear un hilo que espere un segundo e incremente los segundos ademas de que haga
//las operaciones necesarias con esos incrementos de segundos
class Segundos implements Runnable{
	
	public Segundos(MarcoReloj marco,PanelReloj panel) {
		
		this.marco=marco;
		this.panel=panel;
		
	}
	@Override
	public void run() {
		
		while(true) {
			
			System.out.println("");
			
			if(this.marco.start==true) {
				
				this.panel.repaint();
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				
				//Se iran incrementando los segundos
				this.panel.segundos+=1;
				this.panel.gradosSegundos=90-(6*this.panel.segundos); //Se restará a 90 
				//grados (que representa 0 segundos) los segundos que se hayan incrementando * 6 grados
				// ya que hay 60 segundos en un minuto, 360/60 = 6
				

				this.panel.gradosMinutos=90-(6*this.panel.minutos)-(0.1*this.panel.segundos);//Se restará a 90 
				//grados (que representa 0 minutos) los minutos que se hayan incrementando * 6 grados
				//ya que hay 60 minutos en una hora, 360 grados/60 minutos = 6, se le restará tambien los segundos que en 
				//un minuto que hayan pasado * 0.1 grados ya que 6 grados de un minuto / 60 segundos = 0.1
				
				
				this.panel.gradosHoras=90-(30*this.panel.horas)-(0.5*this.panel.minutos)-((30/3600)*this.panel.segundos);
				//Se restara a 90 grados (que representa 12 horas) las horas que hayan pasado * 30 grados
				// ya que 360/12 horas = 30, ademas se restaran los minutos que hayan pasado * 0.5 
				//ya que 30 grados / 60 minutos = 0.5 grados , se restara tambien 30/3600 
				//(30 grados de una hora / 60minutos * 60 segundos) * los segundos que hayan pasado
				
				if(this.panel.segundos==60) {
					this.panel.segundos=0;
					this.panel.minutos+=1;
					
				}
				
				if(this.panel.minutos==60) {
					this.panel.minutos=0;
					this.panel.horas+=1;
				}
				
				if(this.panel.horas==13) {
					
					this.panel.horas=1;
				}
				
				

				this.panel.contr_sec.setValue(this.panel.segundos);
				this.panel.contr_min.setValue(this.panel.minutos);
				this.panel.contr_hr.setValue(this.panel.horas);
				
			}
			
			
		}
		
	}
	
	MarcoReloj marco;
	PanelReloj panel;
	
	
}


//Aqui iran todos los componentes de la ventana
class MarcoReloj extends JFrame{
	
	PanelReloj miPanelReloj;
	
	int inicio=0;
	
	boolean start=false,stop=false;

	JSpinner control_hr,control_min,control_sec;
	
	
	
	public MarcoReloj() {
		
		this.setBounds(380,60,500,600);
		
		this.setTitle("Clock");
		
		this.setLayout(new BorderLayout());
		
		JPanel laminaNorte = new JPanel();
		
		laminaNorte.setLayout(new GridLayout(3,1));
		
		laminaNorte.setBorder(BorderFactory.createEtchedBorder());
		
		
		//LAMINA ETIQUETAS
		
		JPanel laminaEtiquetas = new JPanel();
		
		laminaEtiquetas.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel tagHr = new JLabel("Hr");
		
		JLabel tagMin = new JLabel("Min");
		
		JLabel tagSeconds = new JLabel("Seconds");
		
		laminaEtiquetas.add(tagHr);
		
		laminaEtiquetas.add(Box.createHorizontalStrut(30));
		
		laminaEtiquetas.add(tagMin);
		
		laminaEtiquetas.add(Box.createHorizontalStrut(30));
		
		laminaEtiquetas.add(tagSeconds);
		
		//LAMINA CONTROLES
		
		JPanel laminaControles = new JPanel();
		
		laminaControles.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		//Control de las horas
		JSpinner control_hr = new JSpinner(new SpinnerNumberModel(12,1,12,1));
		
		control_hr.setPreferredSize(new Dimension(50,15));
		
		control_hr.addChangeListener(new ChangeListener() {//Si se hace uso de este control (JSpinner)

			@Override
			public void stateChanged(ChangeEvent e) {
				
				MarcoReloj.this.miPanelReloj.horas=(int)control_hr.getValue();
				
				//Cada 1 hora la manecilla de la hora avanza (se restan) 30 grados  (360/12 horas)
				//30 grados /60 minutos en 1 hora
				
				//30 grados /60 minutos en 1 hora
			 
				//30 grados /3600 segundos de 1 hora 
							
				//Se inicializará la manecilla de la hora de acuerdo a los calculos de los grados
				
				int horas = MarcoReloj.this.miPanelReloj.horas;
				int minutos = MarcoReloj.this.miPanelReloj.minutos;
				int segundos = MarcoReloj.this.miPanelReloj.segundos;
				
				MarcoReloj.this.miPanelReloj.gradosHoras=90-(30*horas)-(0.5*minutos)-((30/3600)*segundos);
				MarcoReloj.this.miPanelReloj.repaint();
			}
			
			
			
		});
		
		laminaControles.add(control_hr);
		
		
		//Control de los minutos
		JSpinner control_min = new JSpinner(new SpinnerNumberModel(0,0,59,1));
		
		control_min.setPreferredSize(new Dimension(50,15));
		
		control_min.addChangeListener(new ChangeListener() {//Si se hace uso de este control (JSpinner)

			@Override
			public void stateChanged(ChangeEvent e) {
				
				MarcoReloj.this.miPanelReloj.minutos=(int)control_min.getValue();
				//Se inicializará la manecilla de la hora de acuerdo a los calculos de los grados
				//Cada 1 minuto la manecilla del minuto avanza (se restan) 6 grados  (360/60 minutos)
				
				int horas = MarcoReloj.this.miPanelReloj.horas;
				int minutos = MarcoReloj.this.miPanelReloj.minutos;
				int segundos = MarcoReloj.this.miPanelReloj.segundos;
				
				MarcoReloj.this.miPanelReloj.gradosHoras=90-(30*horas)-(0.5*minutos)-((30/3600)*segundos);
				MarcoReloj.this.miPanelReloj.gradosMinutos=90-(6*minutos)-(0.1*segundos);
				
				
				MarcoReloj.this.miPanelReloj.repaint();	
			}
			
			
		});
		
		laminaControles.add(control_min);
		
		
		//Control de los segundos
		JSpinner control_sec = new JSpinner(new SpinnerNumberModel(0,0,59,1));
		
		control_sec.setPreferredSize(new Dimension(50,15));
		
		
		control_sec.addChangeListener(new ChangeListener() {//Si se hace uso de este control (JSpinner)

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				MarcoReloj.this.miPanelReloj.repaint();
				
			
				MarcoReloj.this.miPanelReloj.segundos=(int)control_sec.getValue();
				//Se inicializará la manecilla de la hora de acuerdo a los calculos de los grados
				//Cada 1 segundo la manecilla del minuto avanza (se restan) 6 grados  (360/60 segundos)
				
				int minutos = MarcoReloj.this.miPanelReloj.minutos;
				int segundos = MarcoReloj.this.miPanelReloj.segundos;
				
				MarcoReloj.this.miPanelReloj.gradosMinutos = 90-(6*minutos)-(0.1*segundos);
				MarcoReloj.this.miPanelReloj.gradosSegundos=90-6*segundos;
				
				MarcoReloj.this.miPanelReloj.repaint();
			}
			
		});
		
		laminaControles.add(control_sec);
		
		
		
		//LAMINA BOTONES
		
		JPanel laminaBotones = new JPanel();
		
		laminaBotones.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JButton botonStart = new JButton("Start");
		
		botonStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				MarcoReloj.this.inicio+=1;
				
				MarcoReloj.this.start=true;
				
				MarcoReloj.this.stop=false;
				
				if(MarcoReloj.this.inicio==1) {
					MarcoReloj.this.empieza();
				}
				
				
			}
			
			
		});
		
	

		JButton botonStop = new JButton("Stop");
		
		
		botonStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				MarcoReloj.this.stop=true;
				
				MarcoReloj.this.start=false;
				
			}
			
			
		});
		
		
		laminaBotones.add(botonStart);
		
		laminaBotones.add(botonStop);
		
		//
		
		
		laminaNorte.add(laminaEtiquetas);
		
		laminaNorte.add(laminaControles);
		
		laminaNorte.add(laminaBotones);
		
		
		this.add(laminaNorte, BorderLayout.NORTH);
		
		
		miPanelReloj = new PanelReloj(this,control_hr,control_min,control_sec);
		
		this.add(miPanelReloj,BorderLayout.CENTER);
		
		
		
	}
	
	
	public void empieza() {
		
			
			Segundos ru = new Segundos(this,miPanelReloj);
			Thread t = new Thread(ru);
			t.start();
			
		
	}
	
	
	
}

//Clase que contiene los metodos que dibujan el reloj
class PanelReloj extends JPanel{
	
	int maxX,maxY;
	
	int xCentro,yCentro;
	
	CoordenadasNormalizadas cn = new CoordenadasNormalizadas(); 
	
	PolaresACartesianas pol_cart= new PolaresACartesianas(); 
	
	Graphics2D g2d;
	
	int segundos=0,minutos=0,horas=12;
	
	double gradosSegundos=90,gradosMinutos=90,gradosHoras=90;
	
	MarcoReloj padre;
	
	JSpinner contr_hr,contr_min,contr_sec;
	
	
	
	public PanelReloj(MarcoReloj padre,JSpinner contr_hr,JSpinner contr_min,JSpinner contr_sec) {
		
		this.padre = padre;
		this.contr_hr=contr_hr;
		this.contr_min=contr_min;
		this.contr_sec=contr_sec;
		
	}
	
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		this.g2d=g2;
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		this.g2d.setRenderingHints(rh); //Para que dibuje de una manera suave
		
		
		//ANCHO Y ALTO DEL PANEL
		maxX = g2d.getClipBounds().width-1;
		maxY = g2d.getClipBounds().height-1;
		
		
		
		//ESTABLECEMOS LOS RANGOS MUNDIALES Y DE DISPOSITIVO DEL CANVAS
		cn.setRangoDispositivo(0, 0, maxX, maxY);
		cn.setRangoMundiales(-5, -5, 5, 5);

		//CALCULAMOS LA COORDENADA CENTRO DESDE LA QUE SE DIBUJARAN LAS MANECILLAS
		cn.setXM(0);
		cn.setYM(0);
		
		this.xCentro=cn.getXD();
		this.yCentro=cn.getYD();
		
		
		
		
		this.dibujaManecillaSegundos();
		
		this.dibujaManecillaMinutos();
		
		this.dibujaManecillaHoras();
		
		this.dibujaRelojYMarcas();
		
		
		
		
		
	}
	
	public void dibujaRelojYMarcas() {
		

		//---------DIBUJAMOS EL CIRCULO DEL RELOJ-----------------
	
		cn.setXM(-4.8);
		cn.setYM(4.8);
		
		int xFrameCirculo = cn.getXD();
		int yFrameCirculo = cn.getYD();
	

		cn.setXM(4.8);
		cn.setYM(-4.8);
		
		int xAnchoCirculo = cn.getXD()-xFrameCirculo;
		int yAltoCirculo = cn.getYD()-yFrameCirculo;
		
		
		g2d.setStroke(new BasicStroke(4));
		g2d.setColor(Color.GRAY);
		
		g2d.drawOval(xFrameCirculo, yFrameCirculo, xAnchoCirculo, yAltoCirculo);
		
		//--------------DIBUJAMOS LAS MARCAS DEL RELOJ  (PUNTOS)---------------
		
		pol_cart.setRo(4.4);
		

		double anguloVertPuntos=90, radio=0.1;
		//SE OBTIENEN LOS PUNTOS TANTO CARTESIANOS Y DESPUES DE DISPOSITIVO PARA LAS HORAS
		
		int xFrameOval,yFrameOval,anchoFrameOval,altoFrameOval;//El punto superior izquierdo del rectangulo que contendra a los puntos de 
		//las marcas de punto en coordenadas de dispositivo
		
		
		
		g2d.setStroke(new BasicStroke(1));
		
		g2d.setColor(Color.BLACK);
		
		for(int i=0;i<12;i++) {
			
			
			
			//ESTABLECEMOS EN QUE GRADO ESTAMOS PARA CADA HORA
			pol_cart.setTheta(anguloVertPuntos);
			
			cn.setXM(pol_cart.getXCartesiano());
			cn.setYM(pol_cart.getYCartesiano());
			
			//g2d.drawLine(cn.getXD(),cn.getYD(), cn.getXD(), cn.getYD()); Centro de las marcas de punto
			
			//CALCULAMOS EL ANCHO Y ALTO DEL CIRCULO PRIMERO EN CARTESIANO Y LUEGO EN DISPOSITIVO
			cn.setXM(pol_cart.getXCartesiano()-radio);
			cn.setYM(pol_cart.getYCartesiano()+radio);
			
			xFrameOval = cn.getXD();
			yFrameOval = cn.getYD();
			
			//ANCHO DEL FRAME DEL CIRCULO PRIMERO EN CARTESIANO Y LUEGO EN DIPOSITIVO
			cn.setXM(pol_cart.getXCartesiano()+radio);
			cn.setYM(pol_cart.getYCartesiano()-radio);

			anchoFrameOval = cn.getXD()-xFrameOval;
			altoFrameOval = cn.getYD()-yFrameOval;
			
			//g2d.drawLine(xFrameOval, yFrameOval, xFrameOval, yFrameOval); Punto en el ancho del frame del circulo
			
			g2d.drawOval(xFrameOval,yFrameOval, anchoFrameOval, altoFrameOval);
			g2d.fillOval(xFrameOval,yFrameOval, anchoFrameOval, altoFrameOval);
			
			anguloVertPuntos-=30;//Las horas se muestran cada 30 horas en un reloj analogico (360/12 = 30)
			
			
			
			
		}
		
		
		
		//--------DIBUJAMOS LAS MARCAS DEL RELOJ  (RAYAS)----------------
		
		g2d.setStroke(new BasicStroke(1));
		
		
		double anguloRaya=84;
		double decrementoRaya=6;
		double x1Cartesiano,y1Cartesiano,x2Cartesiano,y2Cartesiano;
		int x1DispRaya,y1DispRaya,x2DispRaya,y2DispRaya;
		int contadorRayas=0;
		
		for(int i=0;i<48;i++) {
			
			contadorRayas+=1;
			
			//Se calculan los primeras coordenadas cartesianas
			pol_cart.setTheta(anguloRaya);
			
			
			pol_cart.setRo(4.5);
			x1Cartesiano=pol_cart.getXCartesiano();
			y1Cartesiano=pol_cart.getYCartesiano();
			
			//Se calculan los segundas coordenadas cartesianas
			pol_cart.setRo(4.3);
			x2Cartesiano=pol_cart.getXCartesiano();
			y2Cartesiano=pol_cart.getYCartesiano();
			
			//Se calculan los primeros puntos en coordenadas de dispositivo
			cn.setXM(x1Cartesiano);
			cn.setYM(y1Cartesiano);
			
			x1DispRaya = cn.getXD();
			y1DispRaya= cn.getYD();
			
			
			//Se calculan los segundos coordenadas de dispositivo
			cn.setXM(x2Cartesiano);
			cn.setYM(y2Cartesiano);
			
			x2DispRaya = cn.getXD();
			y2DispRaya= cn.getYD();
			

			
			if(contadorRayas%4==0) {
				
				anguloRaya-=12;
				
			}else {
				
				anguloRaya-=decrementoRaya;
				
			}
			
			g2d.drawLine(x1DispRaya, y1DispRaya, x2DispRaya, y2DispRaya);
			
	
		}
		

	}
	
	public void dibujaManecillaSegundos() {
		
		dibujaManecilla(4.0,this.gradosSegundos,Color.RED,1);
		
	}
	
	
	public void dibujaManecillaMinutos() {
		
		dibujaManecilla(3.6,this.gradosMinutos,Color.BLUE,2);
	}
	
	public void dibujaManecillaHoras() {
		
		dibujaManecilla(3.2,this.gradosHoras,Color.BLACK,3);
		
	}
	
	public void dibujaManecilla(double radioManecilla,double gradosManecilla, Color colorManecilla,float grosorManecilla) {
		
		pol_cart.setRo(radioManecilla);//Longitud de la manecilla
		
		pol_cart.setTheta(gradosManecilla);  //Se establecen los grados en los que está el minutero
		
		//Se obtienen las coordenadas cartesianas de ese punto al que debe apuntar el minutero
		
		cn.setXM(pol_cart.getXCartesiano());
		
		cn.setYM(pol_cart.getYCartesiano());
		
		g2d.setColor(colorManecilla);
		
		//Se dibuja una linea del centro hacia el punto obtenido segun los grados, en coordenadas de dispositivo
		
		g2d.setStroke(new BasicStroke(grosorManecilla));
		
		g2d.drawLine(this.xCentro, this.yCentro, cn.getXD(), cn.getYD());
		
	}
	
 	

}

