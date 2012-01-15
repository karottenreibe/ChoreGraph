package be.rottenrei.android.choregraph;

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
import be.rottenrei.android.lib.util.StringUtils;

public class ChoreGraphWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for (int appWidgetId : appWidgetIds) {
			Intent intent = new Intent(context, ListChoresActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
			views.setOnClickPendingIntent(R.id.widget, pendingIntent);
			views.setOnClickPendingIntent(R.id.addEditButton, pendingIntent);

			updateView(context, views);
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

		views.removeAllViews(R.id.barContainer);
		if (chores.isEmpty()) {
			RemoteViews emptyView = new RemoteViews(context.getPackageName(), R.layout.widget_no_chores);
			views.addView(R.id.barContainer, emptyView);
		} else {
			for (int i = 0; i < chores.size(); i++) {
				Chore chore = chores.get(i);
				RemoteViews choreBar = new RemoteViews(context.getPackageName(), R.layout.widget_chore_bar);
				CharSequence text = StringUtils.getTemplateText(context, R.string.widget_entry, chore.getName(),
						Integer.toString(chore.getDaysUntilDue()));
				choreBar.setTextViewText(R.id.barText, text);
				if (chore.getCycleDays() <= 0) {
					choreBar.setInt(R.id.barText, "setBackgroundResource", R.drawable.button_selector_red);
				}
				Intent intent = new Intent(context, MarkDoneService.class);
				intent.putExtra(MarkDoneService.DBID_EXTRA, chore.getDbId());
				PendingIntent pendingIntent = PendingIntent.getService(context, i, intent, 0);
				choreBar.setOnClickPendingIntent(R.id.barText, pendingIntent);
				views.addView(R.id.barContainer, choreBar);
			}
		}
	}

}
