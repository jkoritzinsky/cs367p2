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
		for(int i = 0; i < users.size(); ++i) {
			if(users.get(i).checkLogin(username, passwd)) {
				currentUser = users.get(i);
				return users.get(i);
			}
		}
		return null;
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
		try(Scanner fileScnr = new Scanner(new File(fileName)))
		{
			String line = "";
			String[] splitLine;
			while(fileScnr.hasNext()) {
				line = fileScnr.nextLine();
				splitLine = line.split("#");
				products.add(new Product(splitLine[1], splitLine[2], Integer.parseInt(splitLine[3]), Float.parseFloat(splitLine[4]))); 
			}
		}
		catch(FileNotFoundException ex) {
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
		try(Scanner fileScnr = new Scanner(new File(fileName)))
		{
			String line = "";
			String[] splitLine;
			while(fileScnr.hasNext()) {
				line = fileScnr.nextLine();
				splitLine = line.split("#");
				users.add(new User(splitLine[1], splitLine[2], Integer.parseInt(splitLine[3]))); 
			}
		}
		catch(FileNotFoundException ex) {
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
		Product lastProduct = products.get(0);
		System.out.println(products.get(0).getCategory());
		for(int i = 0; i < products.size(); i++) {
			if(products.get(i).getCategory().equals(lastProduct.getCategory())) {
				System.out.println(products.get(i).getName() + " [Price:$" + 
						products.get(i).getPrice() + " Rating:" + products.get(i).getRating() + " stars]");
			}
			else {
				System.out.println(products.get(i).getCategory());
				System.out.println(products.get(i).getName() + " [Price:$" + 
						products.get(i).getPrice() + " Rating:" + products.get(i).getRating() + " stars]");
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
		while (!done) 
		{
			System.out.print("Enter option : ");
			String input = stdin.nextLine();

			//only do something if the user enters at least one character
			if (input.length() > 0) 
			{
				String[] commands = input.split(":");//split on colon, because names have spaces in them
				if(commands[0].length()>1)
				{
					System.out.println("Invalid Command");
					continue;
				}
				switch(commands[0].charAt(0)){
				case 'v':
					if(commands[1].equals("all")) {
						for(int i = 0; i < inStock.size(); ++i) {
							products.add(inStock.get(1)); 
						}
						printByCategory();
					}
					else if(commands[1].equals("wishlist")) {
						currentUser.printWishList(System.out); 
					}
					else if(commands[1].equals("instock")) {
						for(int i = 0; i < inStock.size(); ++i) {
							System.out.println(inStock.get(i).toString());
						}
					}
					break;

				case 's':
					for(int i = 0; i < products.size(); ++i) {
						if(commands[1].equals(products.get(i).getName())) {
							System.out.println(products.get(i).toString());
						}
					}
					break;

				case 'a':
					Product productToAdd = null;
					for(int i = 0; i < products.size(); ++i) {
						if(products.get(i).getName().equals(commands[1])) {
							productToAdd = products.get(i);
						}
					}
					if(!productToAdd.equals(null)) {
						currentUser.addToWishList(productToAdd);
					}
					else {
						System.out.println("Product not found");
					}
					break;

				case 'r':
					boolean foundProduct = false;
					for(int i = 0; i < products.size(); ++i) {
						if(products.get(i).getName().equals(commands[1])) {
							currentUser.removeFromWishList(products.get(i).getName());
							foundProduct = true;
						}
					}
					if(!foundProduct) {
						System.out.println("Product not found");
					}
					break;

				case 'b':
					for(int i = 0; i < inStock.size(); ++i) {
						boolean success = false;
						try {
							currentUser.buy(inStock.get(i).getName());
							success = true;
						}
						catch(InsufficientCreditException ex) {
							System.out.println("Insufficient funds for " + inStock.get(i).getName());
						}
						if(success) {
							System.out.println("Bought " + inStock.get(i).getName());
						}
					}
					break;

				case 'c':
					System.out.println("$" + currentUser.getCredit());
					break;

				case 'l':
					done = true;
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
