package com.amaysim.cart;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;
/**
 * Amaysim Shopping Cart Exercise
 * 
 * Amaysim is rebuilding our shopping cart. In this new version we want to allow our customers to purchase multiple SIM cards simultaneously. You
 * have been engaged to build this new version
 * 
 * @author Earathou Berdin
 *
 */
public class ShoppingCart {

	private static HashMap<String, String> PRODUCT_DISPLAY_MAP =  new HashMap<String, String>();
	private Vector<Item> cartItems;
	private Vector<Item> updatedCartItems;
	private String promoCode;
	private double totalPrice;
	private PricingRules pricingRule;
	
	static {
		// Map display with product codes
		PRODUCT_DISPLAY_MAP.put("1", "ult_small");
		PRODUCT_DISPLAY_MAP.put("2", "ult_medium");
		PRODUCT_DISPLAY_MAP.put("3", "ult_large");
		PRODUCT_DISPLAY_MAP.put("4", "1gb");
	}
	
	public ShoppingCart(PricingRules pricingRule) {
		cartItems = new Vector<>();
		this.pricingRule = pricingRule;
	}
	
	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	
	public PricingRules getPricingRule() {
		return pricingRule;
	}

	public void setPricingRule(PricingRules pricingRule) {
		this.pricingRule = pricingRule;
	}
	
	/**
	 * Display product
	 */
	private static void displayProduct() {
		System.out.println("Welcome to Amaysim Store!\n");
		System.out.println("See our products below: ");
		listProduct("");
	}
	
	private static void listProduct(String selected) {
		System.out.println();
		if (!"1".equals(selected)) System.out.println("1 - Unlimited 1 GB for only $24.90");
		if (!"2".equals(selected)) System.out.println("2 - Unlimited 2 GB for only $29.90");
		if (!"3".equals(selected)) System.out.println("3 - Unlimited 5 GB for only $44.90");
		if (!"4".equals(selected)) System.out.println("4 - 1 GB Data-pack $9.90");
	}

	/**
	 * Check out items in the cart
	 * 
	 * @param cartItems
	 * @param promoCode
	 */
	public void total() {
		updatedCartItems = new Vector<Item>();
		Iterator<Item> items = cartItems.iterator();

		while (items.hasNext()) {
			Item item = items.next();
			ProductCatalog productCatalog = pricingRule.getProductCatalog().get(item.getProductCode());

			switch (item.getProductCode()) {

			case "ult_small": // Unlimited 1GB Sim

				// A 3 for 2 deal on Unlimited 1GB Sims.
				// So for example, if you buy 3
				// Unlimited 1GB Sims, you will pay the price of 2 only for the
				// first
				// month.
				
				if (item.getQuantity() >= 3) {
					
					// will pay the price of 2 only for the first month
					double newItemPrice = 
							((item.getQuantity() / 3)	* (2 * productCatalog.getPrice()))	+ // get total multiples of 3 to set price for 2 only
							((item.getQuantity() % 3)	* productCatalog.getPrice()); 
					// if they buy 4, 3 will have bulk of 2 price and 1  will have regular  price
					item.setTotalPrice(newItemPrice);
					
				} else {
					// normal price
					item.setTotalPrice(item.getQuantity() * productCatalog.getPrice());
				}
				updatedCartItems.add(item);
				break;

			case "ult_large": // Unlimited 5GB Sim

				// The Unlimited 5GB Sim will have a bulk discount applied;
				// whereby the
				// price will drop to $39.90 each for the first month, if the
				// customer
				// buys more than 3.

				if (item.getQuantity() > 3) {
					double newItemPrice = 39.90;
					item.setTotalPrice(newItemPrice * item.getQuantity());
					
				} else
					item.setTotalPrice(productCatalog.getPrice() * item.getQuantity());
				
				updatedCartItems.add(item);
				break;

			case "ult_medium": // Unlimited 2GB

				// We will bundle in a free 1 GB Data-pack free-of-charge with
				// every Unlimited 2GB sold.

				item.setTotalPrice(productCatalog.getPrice() * item.getQuantity());
				updatedCartItems.add(item);
				
				if (item.getQuantity() > 0) {
					
					// free 1 GB Data-pack free-of-charge with every Unlimited 2GB sold.
					ProductCatalog freeBundle1GBDataPack = pricingRule.getProductCatalog()
							.get("1gb");

					Item freeItem = new Item();
					freeItem.setPrice(0);
					freeItem.setProductCode(freeBundle1GBDataPack
							.getProductCode());
					freeItem.setQuantity(item.getQuantity());
					freeItem.setTotalPrice(0);

					updatedCartItems.add(freeItem);
				}
				break;
				
			case "1gb":  // 1 GB Data-pack $9.90
				item.setTotalPrice(productCatalog.getPrice() * item.getQuantity());
				updatedCartItems.add(item);
				
				break;
			}

			totalPrice = totalPrice + item.getTotalPrice();

		}
		
		totalPrice = validatePromoCode(totalPrice);

	}

	/*
	 * Validate Promo Code
	 */
	private double validatePromoCode(double totalPrice) {

		// Adding the promo code 'I<3AMAYSIM' will apply a 10% discount
		// across the board.
		if ("I<3AMAYSIM".equals(promoCode)) {
			return totalPrice * 0.90;
		} else
			return totalPrice;
	}
	
	/**
	 * Add item to cart
	 * @param productNumberSelected
	 * @param qty
	 * @return
	 */
	private Item getItem(String productNumberSelected, int qty) {
		
		String productCode = PRODUCT_DISPLAY_MAP.get(productNumberSelected);
		ProductCatalog product = pricingRule.getProductCatalog().get(productCode);
		
		Item item = new Item(productCode, qty, product.getPrice());
		
		return item;
		
	}
	
	private void add(Item item) {
		cartItems.add(item);
	}
	
	private void add(Item item, String promoCode) {
		cartItems.add(item);
		this.promoCode = promoCode;
	}
	
	/**
	 * Display the items selected/purchase, total price and cart items for checkout
	 */
	private void displayItems() {
		Iterator<Item> items = cartItems.iterator();

		System.out.println("\n** ITEMS ADDED **");
		while (items.hasNext()) {
			Item item = items.next();
			System.out.println(item.getQuantity() + " x " + pricingRule.getProductCatalog().get(item.getProductCode()).getProductName());
		}
		
		System.out.print("\nCART TOTAL: $" );
		System.out.printf("%.2f", totalPrice);
		System.out.println();
		
		Iterator<Item> updatedCart = updatedCartItems.iterator();
		System.out.println("\n** CART ITEMS **");
		
		while (updatedCart.hasNext()) {
			Item item = updatedCart.next();
			System.out.println(item.getQuantity() + " x " + pricingRule.getProductCatalog().get(item.getProductCode()).getProductName());
		}
	}
	
	
	
	/**
	 * This will call the main program of Shopping Cart
	 * @param args
	 */
	public static void main(String args[]) {
		
		ShoppingCart amaysimCart = new ShoppingCart(new PricingRules("MID_YEAR_2017"));
		ShoppingCart.displayProduct();
		
		Scanner userInput = new Scanner(System.in);  
		
		System.out.println("\nPlease enter the number of product you want to purchase [1-4]: ");
		String product1 = userInput.next();
		
		System.out.println("\nHow many?: " );
		int qty1 = userInput.nextInt();
		
		Item item1 = amaysimCart.getItem(product1, qty1);
		
		System.out.println("\nThank you! Your item selected has been added to cart!");
		System.out.println("\nDo you want to buy again? Here is the remaining list! ");
		listProduct(product1);
		
		System.out.println("\nPlease enter the number of another product you want to purchase [1-4] (except " + product1 + ")] : ");
		String product2 = userInput.next();

		System.out.println("\nHow many?: ");
		int qty2 = userInput.nextInt();
		
		System.out.println("\n Thank you! Your item selected has been added to cart!");

		Item item2 = amaysimCart.getItem(product2, qty2);

		System.out.println("\nPlease enter promo code (Type any then press enter): ");
		String promoCode = userInput.next();
		
		System.out.println("Please wait.. ");
		
		System.out.println("\nHere are the items in your cart!");
		
		amaysimCart.add(item1);
		amaysimCart.add(item2, promoCode);
		amaysimCart.total();
		amaysimCart.displayItems();
	}


	public static void mainx(String args[]) {
		
		ShoppingCart amaysimCart = new ShoppingCart(new PricingRules("MID_YEAR_2017"));
		
		String product1 = new String();
		String product2 = new String();
		String promoCode = new String();
		int qty1 = 0;
		int qty2 = 0;
		
		// Scenario 1
//		product1 = "1";
//		product2 = "3";
//		promoCode = "";
//		qty1 = 3;
//		qty2 = 1;
		
		// Scenario 2
//		product1 = "1";
//		product2 = "3";
//		promoCode = "";
//		qty1 = 2;
//		qty2 = 4;
		
		// Scenario 3
		product1 = "1";
		product2 = "2";
		promoCode = "";
		qty1 = 1;
		qty2 = 2;
		
		// Scenario 4
//		product1 = "1";
//		product2 = "4";
//		promoCode = "I<3AMAYSIM";
//		qty1 = 1;
//		qty2 = 1;
		
		displayProduct();
		
		System.out.println("\nPlease enter the number of product you want to purchase [1-4]: " + product1);//		System.in();
		System.out.println("\nHow many?: " + qty1);// System.in
		Item item1 = amaysimCart.getItem(product1, qty1);
		
		System.out.println("\nPlease enter the number of another product you want to purchase[1-4]: "  + product2);
		// System.in
		System.out.println("\nHow many?: " + qty2);
		// System.in
		Item item2 = amaysimCart.getItem(product2, qty2);

		System.out.println("\nPlease enter promo code (type any then press enter): ");
		// System.in
//		
		amaysimCart.add(item1);
		amaysimCart.add(item2, promoCode);
		amaysimCart.total();
		amaysimCart.displayItems();
	
	}
}
