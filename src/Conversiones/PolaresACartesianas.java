package Conversiones;


public class PolaresACartesianas {
	
	
	double ro,theta;
	
	
	public void setRo(double ro) {
		this.ro=ro;
	}
	
	public void setTheta(double theta) {
		this.theta=theta;
	}
	
	public double getXCartesiano() {
		
		return this.ro * Math.cos(Math.toRadians(theta));
		
	}
	
	public double getYCartesiano(){
		
		return this.ro * Math.sin(Math.toRadians(theta));
	} 
	
	
}
