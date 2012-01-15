package be.rottenrei.android.choregraph.model;

import java.util.Date;

import be.rottenrei.android.lib.db.IModelType;

/**
 * Stores all attributes of a chore.
 */
public class Chore implements IModelType {

	private Long dbId = null;
	private String name = "";
	private int cycleDays = 7;
	private long lastTimeDone = 0;

	public long getLastTimeDone() {
		return lastTimeDone;
	}

	public void setLastTimeDone(long lastTimeDone) {
		this.lastTimeDone = lastTimeDone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCycleDays() {
		return cycleDays;
	}

	public void setCycleDays(int cycleDays) {
		this.cycleDays = cycleDays;
	}

	@Override
	public Long getDbId() {
		return dbId;
	}

	@Override
	public void setDbId(Long id) {
		this.dbId = id;
	}

	public int getDaysUntilDue() {
		return getDaysUntilDue(new Date().getTime(), lastTimeDone, cycleDays);
	}

	public static int getDaysUntilDue(long now, long lastTimeDone, int cycleDays) {
		long oneDay = 24*3600*1000;
		long nowDays = now / oneDay;
		long thenDays = lastTimeDone / oneDay;
		return (int) (thenDays + cycleDays - nowDays);
	}

}
