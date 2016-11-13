

public class ProductParser {

	
	public static Product parseStringToProduct(String lineToParse){
		
		Product product;
		String[] inputToken = lineToParse.split("/");
		
		String type = inputToken[0];  // food or clothing
		int quantity = Integer.parseInt(inputToken[2]);
		double unitPrice = Double.parseDouble(inputToken[3]);

		
		
		if(type.equalsIgnoreCase("Food")){
			double damageRate = Double.parseDouble(inputToken[5]);
			product = new Food(inputToken[1],quantity,unitPrice,inputToken[4],damageRate,inputToken[6]);
			
		}else{
			product = new Clothing(inputToken[1],quantity,unitPrice,inputToken[4],inputToken[5]);
		}
		
		return product;
	}
	
}
