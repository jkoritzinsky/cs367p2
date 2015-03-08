/**
 * Stores the name, category, price and rating of a product
 */
public class Product {
	//
	private String name; //The name of the product
	private String category; //The type of product
	private int price; //How much the product costs
	private float rating; //How well the product has been rated
	
	/**
     * Constructs a Product with a name, category, price and rating. 
     * 
     * @param name name of product
     * @param category category of product
     * @param price price of product in $ 
     * @param rating rating of product out of 5
     */
	public Product(String name, String category, int price, float rating){
		//Initializes the variables of the product
		this.name = name;
		this.category = category;
		this.price = price;
		this.rating = rating;
	}
	
	/** 
     * Returns the name of the product
     * @return the name
     */
	public String getName(){
		return name; //Returns the product name
	}
	
	/** 
     * Returns the category of the product
     * @return the category
     */
	public String getCategory(){
		return category; //Returns the product category
	}
	
	/** 
     * Returns the price of the product
     * @return the price
     */
	public int getPrice(){
		return price; //Returns the product price
	}
	
	/** 
     * Returns the rating of the product
     * @return the rating
     */
	public float getRating(){
		return rating; //Returns the product rating
	}
	
	/** 
     * Returns the Product's information in the following format: <NAME> [Price:$<PRICE> Rating:<RATING> stars]
     */
	public String toString(){
		StringBuilder builder = new StringBuilder(); //declares a builder to put together the string
		builder.append(getName()); //adds the name of the product
		builder.append(" [Price:$").append(getPrice()); //adds the price
		builder.append(" Rating:").append(getRating()); //adds the rating
		builder.append(" stars]"); //adds "stars" to the end of the rating
		return builder.toString(); //returns the completed string
	}

}
