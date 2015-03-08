///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            AmazonStore
// Files:            AmazonStore, DLinkedList, Product, User, InsufficientCreditException 
// Semester:         CS367 Spring 2015
//
// Author:           Jeremy Koritzinsky
// Email:            j.koritzinsky@wisc.edu
// CS Login:         koritzinsky
// Lecturer's Name:  Skrentny
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
// Pair Partner:     Jeffrey Tucker
// Email:            jetucker@wisc.edu
// CS Login:         jtucker
// Lecturer's Name:  Skrentny
//
//////////////////////////// 80 columns wide //////////////////////////////////



import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The main class including the command line for user commands
 *
 * <p>Bugs: None known
 *
 * @authors Jeremy Koritzinsky and Jeffrey Tucker
 */
public class AmazonStore {

	//Store record of users and products
	private static ListADT<Product> products = new DLinkedList<Product>();
	private static ListADT<User> users = new DLinkedList<User>();
	private static User currentUser = null;//current user logged in

	//scanner for console input
	public static final Scanner stdin= new Scanner(System.in);


	//main method
	public static void main(String args[]) {


		//Populate the two lists using the input files: Products.txt User1.txt User2.txt ... UserN.txt
		if (args.length < 2) {
			System.out.println("Usage: java AmazonStore [PRODUCT_FILE] [USER1_FILE] [USER2_FILE] ...");
			System.exit(0);
		}

		//load store products
		loadProducts(args[0]);

		//load users one file at a time
		for(int i=1; i<args.length; i++)
			loadUser(args[i]);

		//User Input for login
		boolean done = false;
		while (!done) 
		{
			System.out.print("Enter username : ");
			String username = stdin.nextLine();
			System.out.print("Enter password : ");
			String passwd = stdin.nextLine();

			if(login(username, passwd)!=null)
			{
				//generate random items in stock based on this user's wish list
				ListADT<Product> inStock=currentUser.generateStock();
				//show user menu
				userMenu(inStock);
			}
			else
				System.out.println("Incorrect username or password");

			System.out.println("Enter 'exit' to exit program or anything else to go back to login");
			if(stdin.nextLine().equals("exit"))
				done = true;
		}

	}

	/**
	 * Tries to login for the given credentials. Updates the currentUser if successful login
	 * 
	 * @param username name of user
	 * @param passwd password of user
	 * @returns the currentUser 
	 */
	public static User login(String username, String passwd){
		for(int i = 0; i < users.size(); ++i) { // A loop over the users
			if(users.get(i).checkLogin(username, passwd)) { //checks if the user credentials match
				currentUser = users.get(i); // If successful, logs the user in
				return users.get(i); 
			}
		}
		return null; //returns null if the credentials were incorrect
	}

	/**
	 * Reads the specified file to create and load products into the store.
	 * Every line in the file has the format: <NAME>#<CATEGORY>#<PRICE>#<RATING>
	 * Create new products based on the attributes specified in each line and insert them into the products list
	 * Order of products list should be the same as the products in the file
	 * For any problem in reading the file print: 'Error: Cannot access file'
	 * 
	 * @param fileName name of the file to read
	 */
	public static void loadProducts(String fileName){
		try(Scanner fileScnr = new Scanner(new File(fileName))) //A scanner for the file
		{
			String line = "";
			String[] splitLine; //A string array for the split of line
			while(fileScnr.hasNext()) { //runs over the whole file
				line = fileScnr.nextLine(); 
				splitLine = line.split("#"); 
				products.add(new Product(splitLine[0], splitLine[1], Integer.parseInt(splitLine[2]), Float.parseFloat(splitLine[3]))); 
			}
		}
		catch(FileNotFoundException ex) { //if the file doesn't exist
			System.out.println("Error: Cannot access file");
		}
	}

	/**
	 * Reads the specified file to create and load a user into the store.
	 * The first line in the file has the format:<NAME>#<PASSWORD>#<CREDIT>
	 * Every other line after that is a name of a product in the user's wishlist, format:<NAME>
	 * For any problem in reading the file print: 'Error: Cannot access file'
	 * 
	 * @param fileName name of the file to read
	 */
	public static void loadUser(String fileName){
		try(Scanner fileScnr = new Scanner(new File(fileName))) // A scanner for the user file
		{
			String line = "";
			String[] splitLine; //A string array for the split of line
			User newUser = null;
			if(fileScnr.hasNextLine()) { //runs once to add user
				line = fileScnr.nextLine(); 
				splitLine = line.split("#"); 
				users.add(newUser = new User(splitLine[0], splitLine[1], Integer.parseInt(splitLine[2]))); 
			}
			while(fileScnr.hasNextLine()) { //runs for all the products under the user
				line = fileScnr.nextLine(); 
				for(int i = 0; i < products.size(); ++i) { //A loop over the products
					if(line.equals(products.get(i).getName())) { //If the specified product exists
						newUser.addToWishList(products.get(i)); //adds the product to the wishlist
						break;
					}
				}
			}
		}
		catch(FileNotFoundException ex) { //if the file doesn't exist
			System.out.println("Error: Cannot access file");
		}
	}

	/**
	 * See sample outputs
	 * Prints the entire store inventory formatted by category
	 * The input text file for products is already grouped by category, use the same order as given in the text file 
	 * format:
	 * <CATEGORY1>
	 * <NAME> [Price:$<PRICE> Rating:<RATING> stars]
	 * ...
	 * <NAME> [Price:$<PRICE> Rating:<RATING> stars]
	 * 
	 * <CATEGORY2>
	 * <NAME> [Price:$<PRICE> Rating:<RATING> stars]
	 * ...
	 * <NAME> [Price:$<PRICE> Rating:<RATING> stars]
	 */
	public static void printByCategory(){
		Product lastProduct = products.get(0); //Allows the user to check if the category is the same as the previous product
		System.out.println(products.get(0).getCategory() + ":"); //Prints out the first category
		for(int i = 0; i < products.size(); i++) {  //A loop over the products
			if(products.get(i).getCategory().equals(lastProduct.getCategory())) { //checks if the category has changed
				System.out.println(products.get(i).getName() + " [Price:$" + 
						products.get(i).getPrice() + " Rating:" + products.get(i).getRating() + " stars]");
				lastProduct = products.get(i); //sets the most recent product
			}
			else { //If the category was different
				System.out.println(products.get(i).getCategory() + ":"); //print out the new category
				System.out.println(products.get(i).getName() + " [Price:$" + 
						products.get(i).getPrice() + " Rating:" + products.get(i).getRating() + " stars]");
				lastProduct = products.get(i); //sets the most recent product
			}
		}
	}


	/**
	 * Interacts with the user by processing commands
	 * 
	 * @param inStock list of products that are in stock
	 */
	public static void userMenu(ListADT<Product> inStock){

		boolean done = false;
		while (!done) // Will loop until the user quits
		{
			System.out.print("Enter option : "); // prompts the user for commands
			String input = stdin.nextLine(); //accepts user input

			//only do something if the user enters at least one character
			if (input.length() > 0) 
			{
				String[] commands = input.split(":");//split on colon, because names have spaces in them
				if(commands[0].length()>1) //only commands beginning with a single character
				{
					System.out.println("Invalid Command");
					continue;
				}
				switch(commands[0].charAt(0)){ //switch statement over different commands
				case 'v': //For printing items
					if(commands[1].equals("all")) { //checks if the user wants to print all items
						printByCategory(); //prints all items
					}
					else if(commands[1].equals("wishlist")) { //checks if the user wants to print their wishlist
						currentUser.printWishList(System.out); //prints all items on the wishlist
					}
					else if(commands[1].equals("instock")) { //checks if the user wants to print the items in stock
						for(int i = 0; i < inStock.size(); ++i) { // a loop over the instock items
							System.out.println(inStock.get(i).toString());
						}
					}
					break;

				case 's': //For finding items
					for(int i = 0; i < products.size(); ++i) { //A loop over the products
						if(products.get(i).getName().startsWith(commands[1])) { //checks if the product exists
							System.out.println(products.get(i).toString());
						}
					}
					break;

				case 'a': // For adding products to the wishlist
					Product productToAdd = null; //declares a dummy product to be assigned to the found product
					for(int i = 0; i < products.size(); ++i) { // A loop over the products
						if(products.get(i).getName().startsWith(commands[1])) { //checks if the product specified exists
							productToAdd = products.get(i);
						}
					}
					if(!productToAdd.equals(null)) { //If the product was found
						currentUser.addToWishList(productToAdd); //adds the product
						System.out.println("Added to wishlist");
					}
					else { //Will only occur if the product was not found
						System.out.println("Product not found");
					}
					break;

				case 'r': //For removing products from the wishlist
					Product removed = currentUser.removeFromWishList(commands[1]); //will set removed to null if the product is not on the wishlist, otherwise it will be set to the item removed
					if(removed == null) {  //If the product was not found
						System.out.println("Product not found");
					}
					else { //Otherwise reports successful removal
						System.out.println("Removed from wishlist");
					}
					break;

				case 'b': //For buying items
					for(int i = 0; i < inStock.size(); ++i) { //A loop over the items in stock
						boolean success = false; //declares a boolean to check if the buy is successful
						try {
							currentUser.buy(inStock.get(i).getName()); //attempts to buy the item
							success = true; //only occurs if buy was successful
						}
						catch(InsufficientCreditException ex) { //will trigger if the user had insufficient funds
							System.out.println("Insufficient funds for " + inStock.get(i).getName());
						}
						if(success) { //reports successful buy
							System.out.println("Bought " + inStock.get(i).getName());
						}
					}
					break;

				case 'c': //returns the credit the user has
					System.out.println("$" + currentUser.getCredit());
					break;

				case 'l': //logs the user out
					done = true; //allows the loop to exit
					System.out.println("Logged Out");
					break;

				default:  //a command with no argument
					System.out.println("Invalid Command");
					break;
				}
			}
		}
	}

}
