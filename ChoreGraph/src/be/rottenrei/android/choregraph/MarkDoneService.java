package be.rottenrei.android.choregraph;

import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import be.rottenrei.android.choregraph.db.ChoreTable;
import be.rottenrei.android.choregraph.db.Database;
import be.rottenrei.android.choregraph.model.Chore;
import be.rottenrei.android.lib.db.DatabaseException;
import be.rottenrei.android.lib.util.ExceptionUtils;
import be.rottenrei.android.lib.util.WidgetUtils;

/**
 * Marks a chore as done.
 */
public class MarkDoneService extends Service {

	public static final String DBID_EXTRA = MarkDoneService.class.getName() + ".dbId";

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		long dbId = intent.getLongExtra(DBID_EXTRA, -1);
		if (dbId > -1) {
			markDone(dbId);
		}
		stopSelf();
		return 0;
	}

	private void markDone(long dbId) {
		Database db = new Database(this).open();
		ChoreTable table = db.getChoreTable();
		try {
			Chore chore = table.get(dbId);
			chore.setLastTimeDone(new Date().getTime());
			table.update(chore);
		} catch (DatabaseException e) {
			ExceptionUtils.handleExceptionWithMessage(e, this, R.string.no_database, MarkDoneService.class);
		} finally {
			db.close();
		}
		WidgetUtils.forceUpdate(this, ChoreGraphWidget.class);
	}

}
