/**
 * A book class which holds the state of any given book in the Library class.
 * Book number: unique key associated with each book
 * Name: The title of each book.
 * CheckedOut: determines if the book is checked out from the library.
 * Date Published: data associated with the publication of the book.
 * @Tenzin Norden, @Vedant Mehta 
 */

package Project1.src;

public class Book {

	private String number; //a 5-digit serial number unique to the book
  	private String name;
	private boolean checkedOut;
	private Date datePublished;

	public Book(String number, String name, Date datePublished) {
		// this.number = number;
		this.name = name;
		this.checkedOut = false;
		this.datePublished = datePublished;
	}

	/**
	 * Checks to see if the book is checked out.
	 * @return "is not available" if the book is unavailable, "is available" otherwise.
	 */
	public String isAvailable(){
		if(this.checkedOut){
			return "is not available";
		} else return "is available";
	}

	/**
	 * Sets the book number with the desired value.
	 * @param number of the book
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * Gets the book number.
	 * @return the book number
	 */
	public String getNumber() {
		return this.number;
	}

	/**
	 * Sets the state of the checkout status of the book.
	 * @param checkoutStatus of the book
	 */
	public void setCheckout(boolean checkoutStatus) {
		this.checkedOut = checkoutStatus;
	}

	@Override
	public boolean equals(Object obj) {
		if (this.number.equals(((Book)obj).number)){
			return true;
		} else return false;
	}

	@Override
	public String toString() {
		return ("Book#"+number+"::"+name+"::"+datePublished.toString()+"::"+isAvailable());
	}
}
