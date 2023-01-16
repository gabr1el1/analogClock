package Conversiones;

//Convierte coordenadas cartesianas a las del dispositivo
public class CoordenadasNormalizadas{
	
	double XMmin, YMmin, XMmax,YMmax,XM,YM;
	int XDmin,YDmin,XDmax,YDmax;
	
	public void setRangoMundiales(double XMmin, double YMmin, double XMmax, double YMmax){
		
		this.XMmin=XMmin;
		this.YMmin=YMmin;
		this.XMmax=XMmax;
		this.YMmax=YMmax;
	}
	
	public void setRangoDispositivo(int XDmin,int YDmin,int XDmax, int YDmax) {
		
		this.XDmin=XDmin;
		this.YDmin=YDmin;
		this.XDmax=XDmax;
		this.YDmax=YDmax;
	}
	
	public void setXM(double XM) {
		this.XM=XM;
	}
	
	public void setYM(double YM) {
		this.YM=YM;
	}
	
	public double getXM() {
		return XM;
	}
	
	public double getYM() {
		return YM;
	}
	
	public int getXD() {
		
		double calculo = XDmin + (XM-XMmin)*(XDmax-XDmin)/(XMmax-XMmin);
		
		return (int)calculo;
		
	}
	
	public int getYD() {
		
		double calculo = YDmax - (YM-YMmin)*(YDmax-YDmin)/(YMmax-YMmin);
		
		return (int)calculo;
		
	}
}
