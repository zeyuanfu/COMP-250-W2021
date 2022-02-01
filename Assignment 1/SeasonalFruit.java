package assignment1;

public class SeasonalFruit extends Fruit{

	public SeasonalFruit(String name, double weight, int pricePerKilo) {
		super(name, weight, pricePerKilo);
	}
	
	public int getCost() {
		return (int) (super.getCost() * 0.85);
	}
}
