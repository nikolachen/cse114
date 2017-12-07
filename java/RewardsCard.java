public class RewardsCard extends CreditCard {

	protected int rewards;

	public RewardsCard (String holder, long number, int expiration, double limit) {
		super(holder, number, expiration, limit);
		this.rewards = 0;
	}

	public RewardsCard(String holder, long number, int expiration) {
		super(holder, number, expiration, 500);
		this.rewards = 0;
	}

	public int rewardPoints() {
		return this.rewards;
	}

	public boolean redeemPoints(int points) {
		if (points <= this.rewards) {
			this.balance -= points / 100.00;
			this.rewards -= points;

			Transaction t = new Transaction("redemption", "CSEBank", points / 100.00);

			return true;
		}

		return false;

	}

	public String toString() {
		String pts = "" + this.rewardPoints();

		return super.toString() + "\tAvailable points: " + pts;
	}

	@Override
	public boolean addTransaction(Transaction t) {

		if (t.type().equals("debit") && (t.amount() <= super.availableCredit()) ) {
			this.balance += t.amount();
			this.list.add(t);
			this.rewards += (int) t.amount();
			return true;
		}

		if (t.type().equals("debit") && (t.amount() > super.availableCredit()) ) {
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