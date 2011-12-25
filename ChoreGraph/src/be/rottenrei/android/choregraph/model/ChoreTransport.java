package be.rottenrei.android.choregraph.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Transports a Chore across Activity boundaries.
 */
public class ChoreTransport implements Parcelable {

	private final Chore chore;

	public ChoreTransport(Chore chore) {
		this.chore = chore;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(chore.getName());
		parcel.writeInt(chore.getCycleDays());
		parcel.writeLong(chore.getLastTimeDone());
	}

	public static Creator<ChoreTransport> CREATOR = new Parcelable.Creator<ChoreTransport>() {

		@Override
		public ChoreTransport createFromParcel(Parcel parcel) {
			Chore chore = new Chore();
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