package assignment1;

public class Fruit extends MarketProduct{

	private double weight;
	private int pricePerKilo;
	
	public Fruit(String name, double weight, int pricePerKilo) {
		super(name);
		this.weight = weight;
		this.pricePerKilo = pricePerKilo;
	}
	
	public int getCost() {
		double  priceperkilo = this.pricePerKilo;
		return (int) (this.weight * priceperkilo);
	}
	
	public boolean equals(Object item) {
		
		if(item instanceof Fruit) {
			return this.getName() == ((Fruit) item).getName() && this.weight == ((Fruit) item).weight && this.pricePerKilo == ((Fruit) item).pricePerKilo;
		}
		else return false;
	}
	
}
