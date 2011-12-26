package be.rottenrei.android.choregraph;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import be.rottenrei.android.choregraph.db.ChoreTable;
import be.rottenrei.android.choregraph.db.Database;
import be.rottenrei.android.choregraph.model.Chore;
import be.rottenrei.android.lib.db.DatabaseException;
import be.rottenrei.android.lib.util.ExceptionUtils;

public class ChoreGraphWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		// Perform this loop procedure for each App Widget that belongs to this provider
		for (int appWidgetId : appWidgetIds) {
			Intent intent = new Intent(context, ChoreGraphActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			// and fire it on click
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
			views.setOnClickPendingIntent(R.id.widget, pendingIntent);
			updateView(context, views);
			// tell the AppWidgetManager to perform an update on the current app widget
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	private void updateView(Context context, RemoteViews views) {
		Database db = new Database(context).open();
		ChoreTable table = db.getChoreTable();
		List<Chore> chores;
		try {
			chores = table.getAll();
		} catch (DatabaseException e) {
			ExceptionUtils.handleExceptionWithMessage(e, context, R.string.no_database, ChoreGraphWidget.class);
			return;
		} finally {
			db.close();
		}
		chores = new LinkedList<Chore>();
		for (int i = 0; i <= 10; i++) {
			Chore c = new Chore();
			c.setCycleDays(5);
			c.setLastTimeDone(new Date().getTime() - i*24*3600*1000);
			c.setName("Bar " + i);
			chores.add(c);
		}
		// TODO handle no chores case
		ChoreGraphView view = new ChoreGraphView(context, chores);
		view.renderTo(views, R.id.widget);
	}

}
