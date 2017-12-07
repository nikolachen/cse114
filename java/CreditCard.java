public class CreditCard extends BankCard {

	private int expiration;
	protected double creditLimit;

	public CreditCard (String cardHolder, long cardNumber, int expiration, double limit) {
		super(cardHolder, cardNumber);
		this.expiration = expiration;
		this.creditLimit = limit;
	}

	public CreditCard(String cardHolder, long cardNumber, int expiration) {
		super(cardHolder, cardNumber);
		this.expiration = expiration;
		this.creditLimit = 500;
	}

	public double limit() {
		return this.creditLimit;
	}

	public double availableCredit() {
		return this.creditLimit - this.balance();
	}

	public String toString() {
		return super.toString();
	}

	@Override
	public boolean addTransaction(Transaction t) {

		if (t.type().equals("debit") && (t.amount() <= availableCredit()) ) {
			this.balance += t.amount();
			this.list.add(t);
			return true;
		}

		if (t.type().equals("debit") && (t.amount() > availableCredit()) ) {
			return false;
		}

		if (t.type().equals("credit")) {
			this.balance -= t.amount();
			this.list.add(t);
			return true;
		}

		return false;
	}

	@Override
	public void printStatement() {

		System.out.println("CardHolder: " + super.cardHolder() + "\t" + this.toString());

		for (Transaction t: list) {
			System.out.println(t.toString());
		}
	}
}