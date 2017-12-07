import java.util.ArrayList;
import java.util.*;
import java.io.*;

public abstract class BankCard {

	private String cardholderName;
	protected long cardNumber;
	protected double balance;
	protected ArrayList<Transaction> list = new ArrayList <Transaction>();

  	public BankCard(String cardholderName, long cardNumber) {
  		this.cardholderName = cardholderName;
  		this.cardNumber = cardNumber; 
  		this.balance = 0;
  	}

  	public double balance() {
  		return this.balance;
  	}

  	public long number() {
  		return this.cardNumber;
  	}

  	public String cardHolder() {
  		return this.cardholderName;
  	}

  	// returns a String containing (at minimum) the card number and the current balance, with appropriate labels
  	public String toString() {
  		String num = "" + cardNumber;
  		String bal = "" + Math.floor(balance * 100) / 100.00;

  		return "Card #" + num + "\t" + "Balance:$" + bal;
  	}

  	public abstract boolean addTransaction(Transaction t);

  	public abstract void printStatement(); 
}
