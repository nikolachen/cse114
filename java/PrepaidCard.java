public class PrepaidCard extends BankCard {

	public PrepaidCard(String cardHolder, long cardNumber, double balance) {
		super(cardHolder, cardNumber);
		this.balance = balance;
	}

	public PrepaidCard(String cardHolder, long cardNumber) {
		super(cardHolder, cardNumber);
		this.balance = 0;
	}

	@Override
	public boolean addTransaction(Transaction t) {
		if (t.type().equals("debit") && (t.amount() <= super.balance())) {
			this.balance -= t.amount();
			this.list.add(t);
			return true;
		}

		if (t.type().equals("debit") && (t.amount() > super.balance())) {
			return false;
		}

		if (t.type().equals("credit")) {
			this.balance -= t.amount();
			this.list.add(t);
			return true;	
		}

		return false;
	}

	public boolean addFunds(double amount) {
		if(amount > 0) {
			this.balance += amount;
			Transaction t = new Transaction("top-up", "User payment", -1 * amount);
			this.list.add(t);

			return true;
		}

		return false;
	}

	public String toString() {
		return super.toString();
	}

	@Override
	public void printStatement() {
		System.out.println("CardHolder: " + super.cardHolder() + "\t" + this.toString());

		for (Transaction t: list) {
			System.out.println(t.toString());
		}
	}
}