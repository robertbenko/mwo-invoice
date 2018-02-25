package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import pl.edu.agh.mwo.invoice.product.Product;



public class Invoice {
	private Map<Product, Integer> products = new HashMap<Product, Integer>();

	private final int number; //to sobie dodaliśmy
	
	public Invoice (){
		this.number = new Random().nextInt(999999999) + 1;
	}
	
	public void addProduct(Product product) {
		addProduct(product, 1);
	}

	public void addProduct(Product product, Integer quantity) {
		if (product == null || quantity <= 0) {
			throw new IllegalArgumentException();
		}
		products.put(product, quantity);
	}

	public BigDecimal getNetTotal() {
		BigDecimal totalNet = BigDecimal.ZERO;
		for (Product product : products.keySet()) {
			BigDecimal quantity = new BigDecimal(products.get(product));
			totalNet = totalNet.add(product.getPrice().multiply(quantity));
		}
		return totalNet;
	}

	public BigDecimal getTaxTotal() {
		return getGrossTotal().subtract(getNetTotal());
	}

	public BigDecimal getGrossTotal() {
		BigDecimal totalGross = BigDecimal.ZERO;
		for (Product product : products.keySet()) {
			BigDecimal quantity = new BigDecimal(products.get(product));
			totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
		}
		return totalGross;
	}

	public int getNumber() {
		// TODO Auto-generated method stub
		return number;
	}
	

	public String preparePrint() {
		// TODO Auto-generated method stub
		String printed = String.valueOf(getNumber()); 
		
		for (Product product : products.keySet()) {
			printed += "\n";
			printed += product.getName();
			printed += " " + products.get(product);
			printed += " " + product.getPrice();
						
		}
		printed += "Liczba pozycji: " + products.size();
		return printed;
	}
}
