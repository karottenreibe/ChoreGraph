package be.rottenrei.android.choregraph.mock;

import be.rottenrei.android.choregraph.model.Chore;

public class MockChore extends Chore {

	private final int daysUntilDue;

	public MockChore(int daysUntilDue) {
		super();
		this.daysUntilDue = daysUntilDue;
	}

	@Override
	public int getDaysUntilDue() {
		return daysUntilDue;
	}

}
