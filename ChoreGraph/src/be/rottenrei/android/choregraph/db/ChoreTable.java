package be.rottenrei.android.choregraph.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import be.rottenrei.android.choregraph.model.Chore;
import be.rottenrei.android.lib.db.DatabaseException;
import be.rottenrei.android.lib.db.TableBase;

/**
 * Table for {@link Chore}s.
 */
public class ChoreTable extends TableBase<Chore> {

	public static class Columns {
		public static final String name = "name";
		public static final String cycleDays = "cycleDays";
		public static final String lastTimeDone = "lastTimeDone";
	}

	public static final String TABLE_NAME = "chores";

	private final ChoreTableSerializer serializer;
	private final String[] columns;

	public ChoreTable(SQLiteDatabase db) {
		super(db);
		this.serializer = new ChoreTableSerializer();
		this.columns = new String[] { KEY_ID, Columns.name,
				Columns.cycleDays, Columns.lastTimeDone };
	}

	@Override
	public TableSerializer<Chore> getSerializer() {
		return serializer;
	}

	@Override
	protected String[] getColumns() {
		return columns;
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	private static class ChoreTableSerializer implements TableBase.TableSerializer<Chore> {

		@Override
		public ContentValues serialize(Chore chore) {
			ContentValues values = new ContentValues();
			values.put(Columns.name, chore.getName());
			values.put(Columns.cycleDays, chore.getCycleDays());
			values.put(Columns.lastTimeDone, chore.getLastTimeDone());
			return values;
		}

		@Override
		public Chore deSerialize(Cursor cursor) throws DatabaseException {
			try {
				Long dbid = cursor.getLong(cursor.getColumnIndexOrThrow(KEY_ID));
				String name = cursor.getString(cursor.getColumnIndexOrThrow(Columns.name));
				int cycleDays = cursor.getInt(cursor.getColumnIndexOrThrow(Columns.cycleDays));
				long lastTimeDone = cursor.getLong(cursor.getColumnIndexOrThrow(Columns.lastTimeDone));

				Chore chore = new Chore();
				chore.setDbId(dbid);
				chore.setName(name);
				chore.setCycleDays(cycleDays);
				chore.setLastTimeDone(lastTimeDone);
				return chore;
			} catch (IllegalArgumentException e) {
				throw new DatabaseException(e);
			}
		}

	}

}
