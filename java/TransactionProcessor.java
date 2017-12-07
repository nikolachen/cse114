// Driver class for the final project
import java.util.*;
import java.io.*;

public class TransactionProcessor
{
	private static String getCardType (long number)
	{
		// Return a String indicating whether 'number' belongs to a 
		// CreditCard, RewardsCard, or a PrepaidCard (or null if it's none
		// of the three)
		
		String result;
		
		int firstTwo = Integer.parseInt(("" + number).substring(0,2));
		
		switch(firstTwo)
		{
			case 84:
			case 85: result = "CreditCard"; break;
			case 86:
			case 87: result = "RewardsCard"; break;
			case 88:
			case 89: result = "PrepaidCard"; break;
			default: result = null; // invalid card number
		}
		
		return result;
	}
}
