package be.rottenrei.android.choregraph;

import java.util.Date;

import android.os.Parcelable;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import be.rottenrei.android.choregraph.db.ChoreTable;
import be.rottenrei.android.choregraph.db.Database;
import be.rottenrei.android.choregraph.model.Chore;
import be.rottenrei.android.choregraph.model.ChoreTransport;
import be.rottenrei.android.lib.app.AddEditModelTypeActivityBase;
import be.rottenrei.android.lib.db.DatabaseException;
import be.rottenrei.android.lib.util.StringUtils;
import be.rottenrei.android.lib.util.UIUtils;
import be.rottenrei.android.lib.util.WidgetUtils;

/**
 * Adds a new chore or edits an existing one.
 */
public class AddEditChoreActivity extends AddEditModelTypeActivityBase<Chore> {

	private long lastTimeDone;

	@Override
	protected void onStop() {
		WidgetUtils.forceUpdate(this, ChoreGraphWidget.class);
		super.onStop();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.add_edit;
	}

	@Override
	protected Parcelable parcel(Chore chore) {
		return new ChoreTransport(chore);
	}

	@Override
	protected Chore unparcel(Parcelable parcelable) {
		ChoreTransport transport = (ChoreTransport) parcelable;
		return transport.getChore();
	}

	@Override
	protected String getExtra() {
		return ChoreTransport.EXTRA;
	}

	@Override
	protected void persist(Chore chore) throws DatabaseException {
		Database db = new Database(this).open();
		db.getChoreTable().update(chore);
		db.close();
	}

	@Override
	protected boolean validate(Chore chore) {
		if (chore.getName().isEmpty()) {
			UIUtils.informUser(this, R.string.no_name);
			return false;
		}
		return true;
	}

	@Override
	protected void setModel(Chore chore) {
		lastTimeDone = chore.getLastTimeDone();
		int daysUntilDue = chore.getDaysUntilDue();
		fillLastDoneText(daysUntilDue);
		EditText nameEditor = (EditText) findViewById(R.id.nameEditor);
		nameEditor.setText(chore.getName());
		EditText cycleDaysPicker = (EditText) findViewById(R.id.cycleDaysPicker);
		cycleDaysPicker.setText(Integer.toString(chore.getCycleDays()));
	}

	private void fillLastDoneText(int daysUntilDue) {
		TextView lastDoneText = (TextView) findViewById(R.id.lastDoneText);
		String lastDoneDate = DateFormat.getDateFormat(this).format(new Date(lastTimeDone));
		lastDoneText.setText(StringUtils.getTemplateText(this, R.string.last_done_at,
				lastDoneDate, Integer.toString(daysUntilDue)));
	}

	@Override
	protected Chore getModel() {
		Chore chore = new Chore();
		EditText nameEditor = (EditText) findViewById(R.id.nameEditor);
		chore.setName(nameEditor.getText().toString());
		EditText cycleDaysPicker = (EditText) findViewById(R.id.cycleDaysPicker);
		chore.setCycleDays(Integer.parseInt(cycleDaysPicker.getText().toString()));
		chore.setLastTimeDone(lastTimeDone);
		return chore;
	}

	public void onDoneClicked(@SuppressWarnings("unused") View view) {
		lastTimeDone = new Date().getTime();
		fillLastDoneText(0);
	}

	@Override
	protected void delete(Chore model) throws DatabaseException {
		Database db = new Database(this).open();
		ChoreTable table = db.getChoreTable();
		table.delete(model);
		db.close();
	}

}
