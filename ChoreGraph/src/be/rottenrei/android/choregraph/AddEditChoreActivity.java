package be.rottenrei.android.choregraph;

import java.util.Date;

import android.os.Parcelable;
import android.widget.EditText;
import be.rottenrei.android.choregraph.db.Database;
import be.rottenrei.android.choregraph.model.Chore;
import be.rottenrei.android.choregraph.model.ChoreTransport;
import be.rottenrei.android.lib.app.AddEditModelTypeActivityBase;
import be.rottenrei.android.lib.db.DatabaseException;
import be.rottenrei.android.lib.util.UIUtils;

/**
 * Adds a new chore or edits an existing one.
 */
public class AddEditChoreActivity extends AddEditModelTypeActivityBase<Chore> {

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
		EditText nameEditor = (EditText) findViewById(R.id.nameEditor);
		nameEditor.setText(chore.getName());
		EditText cycleDaysPicker = (EditText) findViewById(R.id.cycleDaysPicker);
		cycleDaysPicker.setText(Integer.toString(chore.getCycleDays()));
	}

	@Override
	protected Chore getModel() {
		Chore chore = new Chore();
		EditText nameEditor = (EditText) findViewById(R.id.nameEditor);
		chore.setName(nameEditor.getText().toString());
		EditText cycleDaysPicker = (EditText) findViewById(R.id.cycleDaysPicker);
		chore.setCycleDays(Integer.parseInt(cycleDaysPicker.getText().toString()));
		chore.setLastTimeDone(new Date().getTime());
		return chore;
	}

}
