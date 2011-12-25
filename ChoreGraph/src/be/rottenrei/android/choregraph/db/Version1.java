package be.rottenrei.android.choregraph.db;

import android.database.sqlite.SQLiteDatabase;
import be.rottenrei.android.choregraph.db.ChoreTable.Columns;
import be.rottenrei.android.lib.db.DatabaseVersionBase;
import be.rottenrei.android.lib.db.IDatabaseVersion;
import be.rottenrei.android.lib.db.TableCreateStatement;
import be.rottenrei.android.lib.db.TableCreateStatement.Type;

/**
 * Base version of the database.
 */
public class Version1 extends DatabaseVersionBase {

	/** Database creation SQL statement. */
	private static final String DATABASE_CREATE = new TableCreateStatement(ChoreTable.TABLE_NAME)
	.col(Columns.name, Type.text, false, "''")
	.col(Columns.cycleDays, Type.integer, false, "7")
	.col(Columns.lastTimeDone, Type.integer, false, "0")
	.end();

	@Override
	public void upgrade(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public IDatabaseVersion getPrevious() {
		return null;
	}

	@Override
	public int getVersion() {
		return 1;
	}

	@Override
	public boolean willPerformUpgrade(SQLiteDatabase db) {
		return !tableExists(db, ChoreTable.TABLE_NAME);
	}

}
