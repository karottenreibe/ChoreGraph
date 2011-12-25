package be.rottenrei.android.choregraph.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Provides access to the db.
 */
public class Database {

	private SQLiteDatabase db;
	private final DatabaseOpenHelper dbHelper;

	public Database(Context context) {
		dbHelper = new DatabaseOpenHelper(context);
	}

	public Database open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		db = null;
		dbHelper.close();
	}

	public ChoreTable getChoreTable() {
		return new ChoreTable(db);
	}

}
