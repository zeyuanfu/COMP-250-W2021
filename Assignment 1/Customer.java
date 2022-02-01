package assignment1;

public class Customer {
	
	private String name;
	private int balance;
	private Basket basket;
	
	public Customer(String name, int balance) {
		this.name = name;
		this.balance = balance;
		basket = new Basket();
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getBalance() {
		return this.balance;
	}
	
	public Basket getBasket() {
		return this.basket;
	}
	
	public int addFunds(int funds) {
		int newBalance;
		
		if(funds<0) throw new IllegalArgumentException ("Please enter a positive balance");
		
		newBalance = this.balance + funds;
		
		return newBalance;
	}
	
	public void addToBasket(MarketProduct item) {
		this.basket.add(item);
	}
	
	public boolean removeFromBasket(MarketProduct item) {
		this.basket.remove(item);
		
		return this.basket.remove(item);
	}
	
	public String checkOut() {
		
		if(this.balance < this.basket.getTotalCost()) throw new IllegalStateException ("Your balance is not sufficient to complete the transaction");
		
		this.balance -= this.basket.getTotalCost();
		
		String receipt = this.basket.toString();
		
		this.basket.clear();
		
		return receipt;
	}

}
