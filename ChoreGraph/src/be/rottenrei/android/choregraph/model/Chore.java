package be.rottenrei.android.choregraph.model;

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
	public void setDbId(long id) {
		this.dbId = id;
	}

}
