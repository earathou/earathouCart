package com.amaysim.cart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * Retrieve Pricing Rules
 * 
 * @author Earathou Berdin
 * 
 */
public class PricingRules {

	private HashMap<String, ProductCatalog> productCatalog = new HashMap<String, ProductCatalog>();
	private HashMap<String, Vector<HashMap<String, ProductCatalog>>> pricingRuleMap = new HashMap<String, Vector<HashMap<String,ProductCatalog>>>();
	private String pricingCode;
	private static String PRICING_RULE_FILE = "c://temp/priceRuleFile.txt";
	
	
	public PricingRules() {
		readFile();
		pricingCode = "MID_YEAR_2017";
		retrievePricingRule();
	}
	
	/**
	 * Set pricing rule codes
	 * @param pricingCode
	 */
	public PricingRules(String pricingCode) {
		this.pricingCode = pricingCode;
		readFile();
		retrievePricingRule();
	}
	
	/**
	 * Retrieve Pricing Rule base on price code
	 */
	private void retrievePricingRule() {
		
		if (pricingRuleMap != null && pricingRuleMap.containsKey(pricingCode) ) {
			Vector<HashMap<String, ProductCatalog>> productCatalogList = (Vector<HashMap<String, ProductCatalog>>) pricingRuleMap.get(pricingCode);
			
			Iterator<HashMap<String, ProductCatalog>> productCatalogListIter = productCatalogList.iterator();
			while (productCatalogListIter.hasNext()) {
				productCatalog = (HashMap<String, ProductCatalog>) productCatalogListIter.next();
			}
		}
		
//		if (pricingCode.equals("MID_YEAR_2017")) {
//
//			// Product Code Product Name Price
//			// ult_small Unlimited 1GB $24.90
//			// ult_medium Unlimited 2GB $29.90
//			// ult_large Unlimited 5GB $44.90
//			// 1gb 1 GB Data-pack $9.90
//		}
	}

	/**
	 * Retrieve Product Catalog
	 * @return
	 */
	public HashMap<String, ProductCatalog> getProductCatalog() {
		return productCatalog;
	}

	/**
	 * Read Pricing Rule File
	 */
	private void readFile() {
		BufferedReader br = null;
		FileReader fr = null;

		try {

			fr = new FileReader(PRICING_RULE_FILE);
			br = new BufferedReader(fr);
			String sCurrentLine;

			br = new BufferedReader(new FileReader(PRICING_RULE_FILE));
			
			HashMap<String, ProductCatalog> productCatalogMapFromFile;

			while ((sCurrentLine = br.readLine()) != null) {
				// Sample output: MID_YEAR_2017|ult_small|Unlimited 1GB|24.90
//				System.out.println(sCurrentLine); 
				
				String[] priceRuleLine = sCurrentLine.split(":");

				if (priceRuleLine != null && priceRuleLine.length == 4) {
					
					// Save the parse data to product Catalog
					ProductCatalog productCatalog = new ProductCatalog();
					productCatalog.setProductCode(priceRuleLine[1]);
					productCatalog.setProductName(priceRuleLine[2]);
					productCatalog.setPrice(Double.parseDouble(priceRuleLine[3]));
					
					Vector<HashMap<String, ProductCatalog>>  productCatalogList;
					
					// If new priceRule code
					if (!pricingRuleMap.keySet().contains(priceRuleLine[0])) {
						productCatalogList = new Vector<HashMap<String, ProductCatalog>>();
						productCatalogMapFromFile = new HashMap<String, ProductCatalog>();
						
					}
					else {
						// contains list of price rule e.g. MID_YEAR_2017 prices
						productCatalogList = (Vector<HashMap<String, ProductCatalog>>) pricingRuleMap.get(priceRuleLine[0]);
						productCatalogMapFromFile =  (HashMap<String, ProductCatalog>) productCatalogList.get(0);
					}
					
					// Save product catalog to map
					productCatalogMapFromFile.put(productCatalog.getProductCode(), productCatalog);

					// Save the product catalog mapping into the list
					productCatalogList.add(productCatalogMapFromFile);
					
					// Save the list of product catalog with the same price rule
					pricingRuleMap.put(priceRuleLine[0], productCatalogList); 
//					System.out.println("productCatalogList.size() " + productCatalogList.size());

				}
//				System.out.println("Price Rule map count: " + pricingRuleMap.size());
			}
		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
	}

//	public static void main(String args[]) {
//		PricingRules pr = new PricingRules();
//	}
}
