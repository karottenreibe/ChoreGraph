package be.rottenrei.android.choregraph.db;

import android.content.Context;
import be.rottenrei.android.lib.db.DatabaseOpenHelperBase;
import be.rottenrei.android.lib.db.IDatabaseVersion;

/*package*/ class DatabaseOpenHelper extends DatabaseOpenHelperBase {

	private static String DATABASE_NAME = "chores";

	static IDatabaseVersion VERSION = new Version1();

	public DatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, VERSION);
	}

}
