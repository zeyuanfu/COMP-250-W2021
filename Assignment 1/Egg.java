package assignment1;

public class Egg extends MarketProduct{

	private int numEggs;
	private int pricePerDozen;
	
	public Egg(String name, int quantity, int pricePerDozen) {
		super(name);
		this.numEggs = quantity;
		this.pricePerDozen = pricePerDozen;
	}
	
	public int getCost() {
		return (this.pricePerDozen * this.numEggs)/12;
	}
	
	public boolean equals(Object item) {
		
		if(item instanceof Egg) {
			return this.getName() == ((Egg) item).getName() && this.numEggs == ((Egg) item).numEggs && this.pricePerDozen == ((Egg) item).pricePerDozen;
		}
		else return false;
	}

}
