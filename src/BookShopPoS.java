import java.util.*;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Book implements Serializable
{
	private static int booksCount = 0;
	private String title;
	private String author; //String array to store the authors' names
	private String publisher;
	private int ISBN;
	private double price;
	private int copies;  //Variable to store the No. of copies

	public Book()
	{
		booksCount++;
		title = "";
		author = "";
		ISBN = 0;
		price = 0.00;
		copies = 0;
	}
	public static int getBooksCount()
	{
		return booksCount;
	}
	public static void incrementBooksCount(int d)
	{
		booksCount += d;
	}
	public static void decrementBooksCount(int d)
	{
		booksCount -= d;
	}
	public void setter(ArrayList<Book> books)
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the Title of book: ");
		title = sc.nextLine();
		System.out.println("Enter the Author of book: ");
		author = sc.nextLine();
		System.out.println("Enter the Publisher of book: ");
		publisher = sc.nextLine();
		do
		{	
			System.out.println("Enter the ISBN of book: ");
			ISBN = sc.nextInt();
			if(searchBook(books, ISBN) == -1)
				break;
			else
				System.out.println("This ISBN already exists in the database, try again with the correct ISBN ....!");
				
		} while(true);
		System.out.println("Enter the Price of book: ");
		price = sc.nextInt();
		System.out.println("Enter the number of copies of book: ");
		copies = sc.nextInt();
	}
	public static int searchBook(ArrayList<Book> books, int isbn)
	{
		for(Book book : books)
			if(book.ISBN == isbn)
				return books.indexOf(book);
		return -1;
	}
	public String getTitle()
	{
		return title;
	}
	public int getISBN()
	{
		return ISBN;
	}
	public double getPrice()
	{
		return price;
	}
	public void decCopies(int cop)
	{
		copies -= cop;
	}
	public int getCopies()
	{
		return copies;
	}
	public void print()
	{
		System.out.println("----------------------------------------------------------------------");
		System.out.println("Title: " + title);
		System.out.println("Ayther: " + author);
		System.out.println("Publisher: " + publisher);
		System.out.println("ISBN: " + ISBN);
		System.out.println("Price: $" + price);
		System.out.println("Copies: " + copies);
		System.out.println("----------------------------------------------------------------------");
	}
}; //End of Class Book


abstract class Member implements Serializable
{
	private static int membersCount = 0;
	private String name;
	private int ID;
	private int noOfBooks; //Variable to store the No. of books bought
	private double amountSpent;

	public abstract double discount(double bookPrice); //Abstract Method
	public Member() //Constructor
	{
		membersCount++;
		name = "";
		ID = 0;
		noOfBooks = 0;
		amountSpent = 0.0;
	}
	public static int getMembersCount() //Return the No. of members in the database
	{
		return membersCount;
	}
	public static void incrementMembersCount(int d)
	{
		membersCount += d;
	}
	public static void decrementMembersCount(int d)
	{
		membersCount -= d;
	}
	public void setter(ArrayList<Member> members) //Setter Method
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name: ");
		name = sc.nextLine();
		do
		{
			try
			{
				System.out.println("Enter the ID: ");
				ID = sc.nextInt();
				if(searchMember(members, ID) != -1) //Checking if a user already has this ID
					throw new InputMismatchException("ID already taken by another member, try another one ....!");
				else
					break;
			}
			catch(InputMismatchException e)
			{
				System.out.println(e);
			}
		} while(true);
		System.out.println("Enter the number of books bought: ");
		noOfBooks = sc.nextInt();
		System.out.println("Enter the amount spent: ");
		amountSpent = sc.nextDouble();
	}
	public static int searchMember(ArrayList<Member> members, int sID)
	{
		for(Member mem: members)
		{
			if (mem.ID == sID)
				return members.indexOf(mem);
		}
		return -1;
	}
	public String getName()
	{
		return name;
	}
	public int getID()
	{
		return ID;
	}
	public int getNoOfBooks()
	{
		return ID;
	}
	public double getAmountSpent()
	{
		return amountSpent;
	}
	public void incrementAmountSpent(double m)
	{
		amountSpent += m;
	}
	public void incBooks(int books)
	{
		noOfBooks += books;
	}
	public void print()
	{
		System.out.println("----------------------------------------------------------------------");
		System.out.println("Name: " + name);
		System.out.println("ID: " + ID);
		System.out.println("Books Bought: " + noOfBooks);
		System.out.println("Amount Spent: $" + amountSpent);
		System.out.println("----------------------------------------------------------------------");
	};
}; //End of Class Member

class RegularMember extends Member
{
	private static int RMembersCount = 0;
	private static final double REG_CUS_DISC = 0.15;
	
	public RegularMember() //Constructor
	{
		RMembersCount++;
	}

	@Override
	public double discount(double bookPrice) //Overridden Method
	{
		double dis = 0;
		if (getNoOfBooks() % 11 == 0)
		{
			dis = (bookPrice * REG_CUS_DISC);
		}
		return dis;
	}
	public static int getRMembersCount()
	{
		return RMembersCount;
	}
}; //End of Class RegularMember

class OccassionalMember extends Member
{
	private static final double OCC_CUS_DISC = 0.05;
	private static int OMembersCount = 0;
	
	public OccassionalMember() //Constructor
	{
		OMembersCount++;
	}
	
	@Override
	public double discount(double bookPrice) //Overridden Method
	{
		double dis = 0;
		if (getNoOfBooks() % 11 == 0)
			dis = bookPrice * OCC_CUS_DISC;
		return dis;
	}
	public static int getOMembersCount()
	{
		return OMembersCount;
	}
}; //End of Class OccassionalMember


public class BookShopPoS //Main Class
{
	private static final int MAX_BOOKS = 100; //books limit to be set by the Programmer
	private static final int MAX_MEMBERS = 50; //members limit to be set by the Programmer
	private static ArrayList<Book> books = new ArrayList<Book>(); // books ArrayList
	private static ArrayList<Member> members = new ArrayList<Member>(); //members ArrayList
	
	public static void main(String [] args) //Main Method
	{
		try
		{
			readFromDisk(); // Reads the saved data from the disk when the program starts
		} 
		catch (ClassNotFoundException e) 
		{	}
		Scanner sc = new Scanner(System.in);
		int menuChoice; //Variable to store the choice for Menu Selection entered by the user
		//variables to store temporary data, e.g. values given to a Method or returned by a Method
		int res;
		char ch;
		int isbn;
		int srchIndex;
		int id;
		
		boolean menuCheck = true; //If false the, program asks to save the database and terminates
		
		do
		{
			try
			{
				showMenu(); //Display the Menu
				menuChoice = sc.nextInt(); //Get the Choice of the user
				if (menuChoice >= 0 && menuChoice <= 12)
				{
					switch (menuChoice)
					{
					case 1:
						addBooks();
						break;
					case 2:
						addMembers();
						break;
					case 3:
						makeSale();
						break;
					case 4:
						System.out.println("Enter the ISBN of the Book: ");
						isbn = sc.nextInt();
						srchIndex = Book.searchBook(books, isbn);
						if(srchIndex != -1)
						{
							System.out.println("Found...:)");
							books.get(srchIndex).print();
						}	
						else	
							System.out.println("Not Found...!");
						break;
					case 5:
						System.out.println("Enter the ID of the Member: ");
						id = sc.nextInt();
						srchIndex = Member.searchMember(members, id);
						if(srchIndex != -1)
						{
							System.out.println("Found...:)");
							if(members.get(srchIndex) instanceof RegularMember)
								System.out.println("*Regular Member*");
							else 
								System.out.println("*Occassional Member*");
							members.get(srchIndex).print();
						}		
						else	
							System.out.println("Not Found...!");
						break;
					case 6:
						res = deleteBook();
						if(res == 1)
							System.out.println("Deleted Successfully...:)");
						else if(res == -1)
							System.out.println("Not Found....!");
						break;
					case 7:
						res = deleteMember();
						if(res == 1)
							System.out.println("Deleted Successfully...:)");
						else if(res == -1)
							System.out.println("Not Found....!");
						break;
					case 8:
						if(Book.getBooksCount() == 0)
							System.out.println("No Books Present....!");
						else
						{
							do
							{
								try
								{
									System.out.println("Confirm Deleting the Whole Books.(Y/N): ");
									ch = sc.next().charAt(0);
									if(ch == 'Y' || ch == 'y' || ch == 'N' || ch == 'n')
										break;
									else
										throw new InputMismatchException("In-valid Input...!");
								}
								catch(InputMismatchException e)
								{
									System.out.println(e);
								}
							} while(true);
							if(ch == 'Y' || ch == 'y')
							{
								books.clear();
								Book.decrementBooksCount(Book.getBooksCount());
							}
						}
						break;
					case 9:
						if(Member.getMembersCount() == 0)
							System.out.println("No Members Present....!");
						else
						{
							do
							{
								try
								{
									System.out.println("Confirm Deleting the Whole Members.(Y/N): ");
									ch = sc.next().charAt(0);
									if(ch == 'Y' || ch == 'y' || ch == 'N' || ch == 'n')
										break;
									else
										throw new InputMismatchException("In-valid Input...!");
								}
								catch(InputMismatchException e)
								{
									System.out.println(e);
								}
							} while(true);
							if(ch == 'Y' || ch == 'y')
							{
								members.clear();
								Member.decrementMembersCount(Member.getMembersCount());
							}
						}
						break;
					case 10:
						if (Book.getBooksCount() == 0)
							System.out.println("No Data Available...!");
						else
							for (Book book : books)
								book.print();
						break;
					case 11:
						if (Member.getMembersCount() == 0)
							System.out.println("No Data Available...!");
						else
						{
							for(Member member : members)
							{
								if(member instanceof OccassionalMember)
									System.out.println("*Occassional Member*");
								else 
									System.out.println("*Regular Member*");
								member.print();
							}
						}
						break;
					case 12:
						saveToDisk();
						break;
					case 0:
						do
						{
							try
							{
								System.out.println("Do you want to save the database?(Y/N): ");
								ch = sc.next().charAt(0);
								if (ch == 'Y' || ch == 'y' || ch == 'N' || ch == 'n')
									break;
								else
									throw new InputMismatchException("In-valid Input...!");
							}
							catch(InputMismatchException e)
							{
								System.out.println(e);
							}
						} while (true);
						if(ch == 'Y' || ch == 'y')
							saveToDisk();
						menuCheck = false;
					} //End of switch
				}		
			else
				throw new InputMismatchException("In-valid Input...!");
			}
			catch(InputMismatchException e)
			{
				System.out.println(e);
			}
		} while (menuCheck);
			
	} //End of main
	public static void showMenu() //MainMenu
	{
		System.out.println("******* - MENU - *******");
		System.out.println("1. Add Books.");
		System.out.println("2. Add Members: ");
		System.out.println("3. Make a Sale.");
		System.out.println("4. Search a Book.");
		System.out.println("5. Search a Member.");
		System.out.println("6. Delete a Book From Database.");
		System.out.println("7. Delete a Member From Database.");
		System.out.println("8. Delete the whole Books.");
		System.out.println("9. Delete the whole Members.");
		System.out.println("10.Display the Books Database.");
		System.out.println("11.Display the Members Database.");
		System.out.println("12.Save Database.");
		System.out.println("0. Exit");
		System.out.println("Please Enter your Choice: \n");
	}
	public static void addBooks() //Adds books to the database
	{
		Scanner sc = new Scanner(System.in);
		char ch;
		if (Book.getBooksCount() == MAX_BOOKS)
			System.out.println("Space is full...!");
		else
		{
			do
			{
				Book temp = new Book();
				System.out.println("*-*-*-*-* Book #: " + Book.getBooksCount() + " *-*-*-*-*");
				temp.setter(books);
				books.add(temp);
				do
				{
					try
					{
						System.out.println("Do you want to Add more Books?(Y/N): ");
						ch = sc.next().charAt(0);
						if (ch == 'Y' || ch == 'y' || ch == 'N' || ch == 'n')
							break;
						else
							throw new InputMismatchException("In-valid Input...!");
					}
					catch(InputMismatchException e)
					{
						System.out.println(e);
					}
				} while (true);
				if(ch == 'N' || ch == 'n')
					break;
			}while(true);
		}
	}
	public static void addMembers() //Adds members to the database
	{
		char status;
		char ch;
		Scanner sc = new Scanner(System.in);
		if (Member.getMembersCount() == MAX_MEMBERS)
			System.out.println("Space is full...!");
		else
			System.out.println("*-*-*-*-* Member #: " + (Member.getMembersCount() + 1) + " *-*-*-*-*");
		do
		{
			do
			{
				try
				{
					System.out.println("Enter the Status of Member(R for Regular, O for Occassional): ");
					status = sc.next().charAt(0);
					if (status == 'R' || status == 'r' || status == 'O' || status == 'o')
						break;
					else
						throw new InputMismatchException();
				}
				catch(InputMismatchException e)
				{
					System.out.println(e);
				}
			} while (true);
			if (status == 'R' || status == 'r')
			{
				RegularMember temp = new RegularMember();
				temp.setter(members);
				members.add(temp);
			}
			else
			{
				OccassionalMember temp = new OccassionalMember();
				temp.setter(members);
				members.add(temp);
			}
			do
			{
				try
				{
					System.out.println("Do you want to Add more Members?(Y/N): ");
					ch = sc.next().charAt(0);
					if (ch == 'Y' || ch == 'y' || ch == 'N' || ch == 'n')
						break;
					else
						throw new InputMismatchException("In-valid Input...!");
				}
				catch(InputMismatchException e)
				{
					System.out.println(e);
				}
			} while (true);
			if(ch == 'N' || ch == 'n')
				break;
		}while(true);
	}
	public static void makeSale() //Make a sale for a member which may include more than 1 copies of more than 1 books, and then calls the displayBIll Method
	{
		Scanner sc = new Scanner(System.in);
		double grossTotal = 0;
		double netTotal = 0;
		int id;
		int bookSrchIndex;
		int memSrchIndex;
		int isbn;
		int copies;
		char ch;
		
		if(Book.getBooksCount() == 0)
			System.out.println("No Books Available....!");
		else
		{
			if(Member.getMembersCount() == 0)
				System.out.println("No Members Data....!");
			else
			{
				System.out.println("Enter the ID of the Member: ");
				id = sc.nextInt();
				memSrchIndex = Member.searchMember(members, id);
				if(memSrchIndex == -1)
					System.out.println("Member Not Found...!");
				else
				{
					if(members.get(memSrchIndex) instanceof RegularMember)
						System.out.println("*-*-*-*-* Regular Member *-*-*-*-*");
					else
						System.out.println("*-*-*-*-* Occassional Member *-*-*-*-*");
					members.get(memSrchIndex).print();
					do
					{
						System.out.println("\nEnter the ISBN of the Book to be sold: ");
						isbn = sc.nextInt();
						bookSrchIndex = Book.searchBook(books, isbn);
						if(bookSrchIndex == -1)
							System.out.println("Not Found...!");
						else
						{
							if(books.get(bookSrchIndex).getCopies() == 0)
								System.out.println("Book is out of Stock...! Sorry for inconvevience.");
							else
							{
								books.get(bookSrchIndex).print();
								System.out.println("Enter the Number of copies to be sold...!");
								copies = sc.nextInt();
								if(copies <= 0)
									System.out.println("In-valid Input...!");
								else if (copies > books.get(bookSrchIndex).getCopies() || copies > 0)
								{
									ch = 'Y';
									if(copies > books.get(bookSrchIndex).getCopies())
									{
										do
										{
											System.out.println("Only " + books.get(bookSrchIndex).getCopies() + " are available, Wanna Buy only these...(Y/N)?");
											ch = sc.next().charAt(0);
											if(ch == 'Y' || ch == 'y' || ch == 'N' || ch == 'n')
												break;
											else
												System.out.println("In-valid Input...!");
										} while(true);
										copies = books.get(bookSrchIndex).getCopies();
									}
									if(ch == 'Y' || ch == 'y')
									{
										do
										{
											System.out.println("Confirm Sale(Y/N): ");
											ch = sc.next().charAt(0);
											if(ch == 'Y' || ch == 'y' || ch == 'N' || ch == 'n')
												break;
											else
												System.out.println("In-valid Input...!");
										} while(true);
										if(ch == 'Y' || ch == 'y')
										{
											books.get(bookSrchIndex).decCopies(copies);
											members.get(memSrchIndex).incBooks(copies);
											grossTotal += books.get(bookSrchIndex).getPrice() * copies;
											netTotal = grossTotal - copies * members.get(memSrchIndex).discount(books.get(bookSrchIndex).getPrice());
										}
									}
								}	
							}
						}
						do
						{
							System.out.print("Wanna Buy More Books?(Y/N)");
							ch = sc.next().charAt(0);
							if(ch == 'Y' || ch == 'y' || ch == 'N' || ch == 'n')
								break;
							else
								System.out.println("In-valid Input...!");
						} while(true);
						if(ch == 'N' || ch == 'n')
							break;
					} while(true);
					if(netTotal > 0)
					{
						members.get(memSrchIndex).incrementAmountSpent(netTotal);
						printBill(netTotal, grossTotal);
					}
					else
						System.out.println("Thank you for visiting us. Have a nice day....:)");
				}
			}
		}
	}
	public static void printBill(double grossTotal, double netTotal) //Prints the Bill of a sale
	{
		System.out.println("\n********** - BILL - **********");
		System.out.println("Gross Total: $" + grossTotal);
		System.out.println("Discount: $" + (grossTotal - netTotal));
		System.out.println("Net Total: $" + netTotal);
		System.out.println();
	}
	public static int deleteBook() //Deletes a book from the database
	{
		Scanner sc = new Scanner(System.in);
		char ch;
		int isbn;
		int delIndex;
		System.out.println("Enter the ISBN of the Book: ");
		isbn = sc.nextInt();
		delIndex = Book.searchBook(books, isbn);
		if(delIndex == -1)
			return -1;			
		else
		{
			do
			{
				try
				{
					System.out.println("Confirm Deletion (Y/N): ");
					ch = sc.next().charAt(0);
					if (ch == 'Y' || ch == 'y' || ch == 'N' || ch == 'n')
						break;
					else
						throw new InputMismatchException("In-valid Input...!");
				}
				catch(InputMismatchException e)
				{
					System.out.println(e);
				}
			} while (true);
			if(ch == 'N' || ch == 'n')
				return 0;
			else
			{
				books.remove(delIndex);
				Book.decrementBooksCount(1);
				return 1;
			}
		}
	}
	public static int deleteMember() //Deletes a member from the database
	{
		Scanner sc = new Scanner(System.in);
		char ch;
		int id;
		int delIndex;
		System.out.println("Enter the ID of the Member: ");
		id = sc.nextInt();
		delIndex = Member.searchMember(members, id);
		if(delIndex == -1)
			return -1;			
		else
		{
			do
			{
				try
				{
					System.out.println("Confirm Deletion (Y/N): ");
					ch = sc.next().charAt(0);
					if (ch == 'Y' || ch == 'y' || ch == 'N' || ch == 'n')
						break;
					else
						throw new InputMismatchException("In-valid Input...!");
				}
				catch(InputMismatchException e)
				{
					System.out.println(e);
				}
			} while (true);
			if(ch == 'N' || ch == 'n')
				return 0;
			else
			{
				members.remove(delIndex);
				Member.decrementMembersCount(1);
				return 1;
			}
		}
	}
	public static void saveToDisk() //Saves the database(books and members) to disk
	{
		String booksFileName = "books.bin";
		String membersFileName = "members.bin";
		try
		{
			ObjectOutputStream bookOutputStream = new ObjectOutputStream(new FileOutputStream(booksFileName));
			ObjectOutputStream memberOutputStream = new ObjectOutputStream(new FileOutputStream(membersFileName));
			for(Book book : books)
				bookOutputStream.writeObject(book);
			bookOutputStream.close();
			for(Member member : members)
				memberOutputStream.writeObject(member);
			memberOutputStream.close();
			
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
	public static void readFromDisk() throws ClassNotFoundException //Reads the database(books and members) form the disk
	{
		String bookFileName = "books.bin";
		String memberFileName = "members.bin";
		try
		{
			ObjectInputStream bookInputStream = new ObjectInputStream(new FileInputStream(bookFileName));
			ObjectInputStream memberInputStream = new ObjectInputStream(new FileInputStream(memberFileName));
			try
			{
				while(true)
				{
					books.add((Book) bookInputStream.readObject());
					Book.incrementBooksCount(1);
				}
			}
			catch(EOFException e)
			{
				bookInputStream.close();
			}
			try
			{
				while(true)
				{
					members.add((Member) memberInputStream.readObject());
					Member.incrementMembersCount(1);
				}
			}
			catch(EOFException e)
			{
				memberInputStream.close();
			}
		}
		catch(FileNotFoundException e)
		{}
		catch(IOException e)
		{
			System.out.println(e);
		}	
	}
} //End of Main Class