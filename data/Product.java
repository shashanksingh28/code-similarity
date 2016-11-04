import java.text.NumberFormat;



public abstract class Product {

		protected String productId;
		protected int quantity;
		protected double unitPrice;
		protected double totalCost;
		
		
		
		public Product (String productId, int quantity, double unitPrice){
			
			this.productId = productId;
			this.quantity = quantity;
			this.unitPrice = unitPrice;
			
		}

		public String getProductId( ){
			return productId;
		}
		
		public abstract void computeTotalCost( );
		
		public String toString(){
			
			return "Product ID:\t\t"+productId+"\n"+
					"Quantity:\t\t"+quantity+"\n"+
					"Unit Price:\t\t"+NumberFormat.getPercentInstance().format(unitPrice)+"\n"+
					"Total Cost:\t\t"+NumberFormat.getPercentInstance().format(totalCost)+"\n";
								
		}
		
}
