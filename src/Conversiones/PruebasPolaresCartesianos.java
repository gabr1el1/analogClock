package Conversiones;

public class PruebasPolaresCartesianos {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PolaresACartesianas pc = new PolaresACartesianas();
		
		double[] arreglo = {1.1,2.1,3.1,4.1};
		
		pc.setRo(5);
		pc.setTheta(420);
		
		System.out.println(pc.getXCartesiano());
		System.out.println(pc.getYCartesiano());
		
		
		
		
	}

}
