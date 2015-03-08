import java.util.Random;
import java.io.PrintStream;

/**
 * The User class uses DLinkedList to build a price ordered list called 'wishlist' of products 
 * Products with higher Price fields should come earlier in the list.
 */
public class User {
	//Random number generator, used for generateStock. DO NOT CHANGE
	private static Random randGen = new Random(1234);
	
	private String username; //The name given to the user
	private String passwd; //The password for the user
	private int credit; //The amount of money the user has
	private ListADT<Product> wishList; //A list of the products the user wants
	
	/**
     * Constructs a User instance with a name, password, credit and an empty wishlist. 
     * 
     * @param username name of user
     * @param passwd password of user
     * @param credit amount of credit the user had in $ 
     */
	public User(String username, String passwd, int credit){
		// initializes all of the variables for the user
		this.username = username; 
		this.passwd = passwd;
		this.credit = credit;
		wishList = new DLinkedList<Product>();
	}
	
	/**
     * Checks if login for this user is correct
     * @param username the name to check
     * @param passwd the password to check
     * @return true if credentials correct, false otherwise
     */
	public boolean checkLogin(String username, String passwd){
		//The check of credentials
		return this.username.equals(username) && this.passwd.equals(passwd);
	}
	
	/**
     * Adds a product to the user's wishlist. 
     * Maintain the order of the wishlist from highest priced to lowest priced products.
     * @param product the Product to add
     */
	public void addToWishList(Product product){
		for(int i = 0; i < wishList.size(); ++i){ //A loop over the wishlist
			if(product.getPrice() >= wishList.get(i).getPrice()) { //Makes sure if the price order is correct
				wishList.add(i, product); // adds at the current loop position
				return;
			}
		}
		wishList.add(product); //If the added product was cheaper than all items on the wishlist
	}
	
	/**
     * Removes a product from the user's wishlist. 
     * Do not charge the user for the price of this product
     * @param productName the name of the product to remove
     * @return the product on success, null if no such product found
     */
	public Product removeFromWishList(String productName){
		for(int i = 0; i < wishList.size(); ++i) { // A loop over the wishlist
			if(wishList.get(i).getName().equals(productName)) { //finds the product specified
				return wishList.remove(i); //removes the found product
			}
		}
		return null; // returns null if product not found
	}
	
	/**
     * Print each product in the user's wishlist in its own line using the PrintStream object passed in the argument
	 * @param printStream The printstream object on which to print out the wishlist
     */
	public void printWishList(PrintStream printStream){ 
		for(int i = 0; i < wishList.size(); ++i) { // A loop over the wishlist
			printStream.println(wishList.get(i)); //Prints each item on the list
		}
	}
	
	/**
     * Buys the specified product in the user's wishlist.
     * Charge the user according to the price of the product by updating the credit
     * Remove the product from the wishlist as well
     * Throws an InsufficientCreditException if the price of the product is greater than the credit available.
     * 
     * @param productName name of the product
     * @return true if successfully bought, false if product not found 
     * @throws InsufficientCreditException if price > credit 
     */
	public boolean buy(String productName) throws InsufficientCreditException{
		Product product = null; //declares a product to copy a product from the wishlist
		for(int i = 0; i < wishList.size(); ++i) { //A loop over the wishlist
			if(wishList.get(i).getName().equals(productName)) { //if the product given is in the wishlist
				product = wishList.get(i); //sets the product to the found product
				break;
			}
		}
		if(product == null) return false; //checks if the product was not found
		if(product.getPrice() > getCredit()) throw new InsufficientCreditException(); //If the user doesn't have enough money
		credit -= product.getPrice(); //spends the money
		return true;
	}
	
	/** 
     * Returns the credit of the user
     * @return the credit
     */
	public int getCredit(){
		return credit; //returns the user's money
	}
	
	/**
	 * This method is already implemented for you. Do not change.
	 * Declare the first N items in the currentUser's wishlist to be in stock
	 * N is generated randomly between 0 and size of the wishlist
	 * 
	 * @returns list of products in stock 
	 */
	public ListADT<Product> generateStock() { 
		ListADT<Product> inStock= new DLinkedList<Product>();

		int size=wishList.size();
		if(size==0)
			return inStock;

		int n=randGen.nextInt(size+1);//N items in stock where n>=0 and n<size

		//pick first n items from wishList
		for(int ndx=0; ndx<n; ndx++)
			inStock.add(wishList.get(ndx));
		
		return inStock;
	}

}
