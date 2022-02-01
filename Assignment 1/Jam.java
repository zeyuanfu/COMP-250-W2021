package assignment1;

public class Jam extends MarketProduct {

	private int numOfJars;
	private int pricePerJar;
	
	public Jam(String name, int numOfJars, int pricePerJar) {
		super(name);
		this.numOfJars = numOfJars;
		this.pricePerJar = pricePerJar;
	}
	
	public int getCost() {
		return this.numOfJars * this.pricePerJar;
	}
	
	public boolean equals(Object item) {
		
		if(item instanceof Jam) {
			return this.getName() == ((Jam) item).getName() && this.numOfJars == ((Jam) item).numOfJars && this.pricePerJar == ((Jam) item).pricePerJar;
		}
		else return false;
	}
	
}
