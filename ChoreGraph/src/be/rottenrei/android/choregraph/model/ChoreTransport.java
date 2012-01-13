package be.rottenrei.android.choregraph.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Transports a Chore across Activity boundaries.
 */
public class ChoreTransport implements Parcelable {

	public static final String EXTRA = ChoreTransport.class.getName() + ".chore";

	private final Chore chore;

	public ChoreTransport(Chore chore) {
		this.chore = chore;
	}

	public Chore getChore() {
		return chore;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		Long dbId = chore.getDbId();
		parcel.writeLong(dbId == null ? -1 : dbId);
		parcel.writeString(chore.getName());
		parcel.writeInt(chore.getCycleDays());
		parcel.writeLong(chore.getLastTimeDone());
	}

	public static Creator<ChoreTransport> CREATOR = new Parcelable.Creator<ChoreTransport>() {

		@Override
		public ChoreTransport createFromParcel(Parcel parcel) {
			Chore chore = new Chore();
			long dbId = parcel.readLong();
			chore.setDbId(dbId == -1 ? null : dbId);
			chore.setName(parcel.readString());
			chore.setCycleDays(parcel.readInt());
			chore.setLastTimeDone(parcel.readLong());
			return new ChoreTransport(chore);
		}

		@Override
		public ChoreTransport[] newArray(int size) {
			return new ChoreTransport[size];
		}
	};

}
