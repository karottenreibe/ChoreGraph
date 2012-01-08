package be.rottenrei.android.choregraph;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import be.rottenrei.android.choregraph.model.Chore;
import be.rottenrei.android.choregraph.model.ChoreTransport;
import be.rottenrei.android.lib.util.UIUtils;

/**
 * Adds a new chore.
 */
public class AddChoreActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_edit);
		Intent intent = getIntent();
		Chore chore = null;
		if (savedInstanceState != null) {
			chore = savedInstanceState.getParcelable(ChoreTransport.EXTRA);
		} else if (intent != null) {
			chore = intent.getParcelableExtra(ChoreTransport.EXTRA);
		}
		if (chore != null) {
			setChore(chore);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable(ChoreTransport.EXTRA, new ChoreTransport(getChore()));
	}

	public void onCancel(@SuppressWarnings("unused") View view) {
		setResult(RESULT_CANCELED);
		finish();
	}

	public void onSave(@SuppressWarnings("unused") View view) {
		Chore chore = getChore();
		if (chore.getName().isEmpty()) {
			UIUtils.informUser(this, R.string.no_name);
			return;
		}
		Intent intent = new Intent();
		intent.putExtra(ChoreTransport.EXTRA, new ChoreTransport(chore));
		setResult(RESULT_OK, intent);
		finish();
	}

	private void setChore(Chore chore) {
		EditText nameEditor = (EditText) findViewById(R.id.nameEditor);
		nameEditor.setText(chore.getName());
		EditText cycleDaysPicker = (EditText) findViewById(R.id.cycleDaysPicker);
		cycleDaysPicker.setText(Integer.toString(chore.getCycleDays()));
	}

	private Chore getChore() {
		Chore chore = new Chore();
		EditText nameEditor = (EditText) findViewById(R.id.nameEditor);
		chore.setName(nameEditor.getText().toString());
		EditText cycleDaysPicker = (EditText) findViewById(R.id.cycleDaysPicker);
		chore.setCycleDays(Integer.parseInt(cycleDaysPicker.getText().toString()));
		chore.setLastTimeDone(new Date().getTime());
		return chore;
	}

}
