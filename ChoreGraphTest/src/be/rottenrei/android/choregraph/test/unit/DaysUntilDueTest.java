package be.rottenrei.android.choregraph.test.unit;

import java.util.Date;

import junit.framework.TestCase;
import be.rottenrei.android.choregraph.model.Chore;

public class DaysUntilDueTest extends TestCase {

	public void testSameDate() {
		long now = new Date(2011, 1, 1).getTime();
		long then = new Date(2011, 1, 1).getTime();
		int cycle = 7;
		assertEquals(cycle, Chore.getDaysUntilDue(now, then, cycle));
	}

	public void testSameDay() {
		long now = new Date(2011, 1, 1, 20, 30).getTime();
		long then = new Date(2011, 1, 1, 10, 20).getTime();
		int cycle = 7;
		assertEquals(cycle, Chore.getDaysUntilDue(now, then, cycle));
	}

	public void testNextDay() {
		long now = new Date(2011, 1, 2, 10, 30).getTime();
		long then = new Date(2011, 1, 1, 10, 20).getTime();
		int cycle = 7;
		assertEquals(cycle - 1, Chore.getDaysUntilDue(now, then, cycle));
	}

	public void testNextDayClose() {
		long now = new Date(2011, 1, 2, 01, 30).getTime();
		long then = new Date(2011, 1, 1, 23, 20).getTime();
		int cycle = 7;
		assertEquals(cycle - 1, Chore.getDaysUntilDue(now, then, cycle));
	}

	public void testDue() {
		long now = new Date(2011, 1, 8, 01, 30).getTime();
		long then = new Date(2011, 1, 1, 23, 20).getTime();
		int cycle = 7;
		assertEquals(0, Chore.getDaysUntilDue(now, then, cycle));
	}

	public void testOverdue() {
		long now = new Date(2011, 1, 10, 01, 30).getTime();
		long then = new Date(2011, 1, 1, 23, 20).getTime();
		int cycle = 7;
		assertEquals(-2, Chore.getDaysUntilDue(now, then, cycle));
	}

}
