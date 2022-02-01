package assignment1;

public class Basket {

	private MarketProduct[] productList;
	
	public Basket() {
		productList = new MarketProduct[0];
	}
	
	public MarketProduct[] getProducts() {
		MarketProduct[] tempList = new MarketProduct[productList.length];
		
		for(int i = 0; i < this.productList.length; i++) {
			tempList[i] = this.productList[i];
		}
		
		return tempList;
	}
	
	public void add(MarketProduct item) {
		
		MarketProduct[] tempList = this.getProducts();
		this.productList = new MarketProduct[tempList.length+1];
		
		for(int i = 0; i< tempList.length; i++) {
			this.productList[i] = tempList[i];
		}
		
		this.productList[this.productList.length-1] = item;
	}
	
	public boolean remove(MarketProduct item) {
		boolean removed = false;
		
		for(int i = 0; i < this.productList.length; i++) {
			
			if(item.equals(productList[i])) {
				
				if(i != this.productList.length-1) {
					
					for(int j = i; j < this.productList.length; j++) {
						this.productList[j]  = this.productList[j+1];
					}
				}
				
				removed = true;
				MarketProduct[] tempList = this.getProducts();
				this.productList = new MarketProduct[tempList.length-1];
				
				for(int k = 0; k< this.productList.length; k++) {
					this.productList[k] = tempList[k];
				}
					
				break;
			}
		}
		
		return removed;
	}
	
	public void clear() {
		this.productList  = new MarketProduct[0];
	}
	
	public int getNumOfProducts() {
		return this.productList.length;
	}
	
	public int getSubTotal() {
		int subtotal = 0;
		
		for(int i = 0; i < this.productList.length; i++) {
			subtotal += productList[i].getCost();
		}
		return subtotal;
	}
	
	public int getTotalTax() {
		int tax = 0;
		
		for(int i = 0; i < this.productList.length; i++) {
			
			if(this.productList[i] instanceof Jam) {
				
				tax += this.productList[i].getCost()*0.15;
			}
		}
		return tax;
	}
	
	public int getTotalCost() {
		int cost = this.getSubTotal() + this.getTotalTax();
		return cost;
	}
	
	public String toString() {
		String receipt = "";
		
		for(int i = 0; i < this.productList.length; i++) {
			receipt += this.productList[i].getName() + "\t" + intToString(this.productList[i].getCost()) + "\n"; 
		}
		
		receipt += "\n";
		
		receipt += "Subtotal\t" + intToString(this.getSubTotal()) + "\n";
		receipt += "Total Tax\t" + intToString(this.getTotalTax()) + "\n";
		
		receipt += "\n";
		
		receipt += "Total Cost\t" + intToString(this.getTotalCost());
		
		return receipt;
	}
	
	public String intToString(int i) {
		String result;
		
		if(i>0) {
			
			int dollar = i/100;
			int digit2 = i % 10;
			int digit1 = (i/10) % 10;
			
			result = dollar + "." + digit1 + digit2;
		}
		else result = "-";
		
		return result;
	}
	
}
