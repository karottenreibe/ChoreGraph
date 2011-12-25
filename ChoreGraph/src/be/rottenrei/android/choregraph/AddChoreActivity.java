package be.rottenrei.android.choregraph;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import be.rottenrei.android.choregraph.model.Chore;
import be.rottenrei.android.choregraph.model.ChoreTransport;
import be.rottenrei.android.lib.util.UIUtils;
import be.rottenrei.android.lib.views.NumberPicker;

/**
 * Adds a new chore.
 */
public class AddChoreActivity extends Activity {

	public static final String CHORE_EXTRA = AddChoreActivity.class.getName() + ".chore";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_card);
		Intent intent = getIntent();
		Chore chore = null;
		if (savedInstanceState != null) {
			chore = savedInstanceState.getParcelable(CHORE_EXTRA);
		} else if (intent != null) {
			chore = intent.getParcelableExtra(CHORE_EXTRA);
		}
		if (chore != null) {
			setChore(chore);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable(CHORE_EXTRA, new ChoreTransport(getChore()));
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
		intent.putExtra(CHORE_EXTRA, new ChoreTransport(chore));
		setResult(RESULT_OK, intent);
		finish();
	}

	private void setChore(Chore chore) {
		EditText nameEditor = (EditText) findViewById(R.id.nameEditor);
		nameEditor.setText(chore.getName());
		NumberPicker cycleDaysPicker = (NumberPicker) findViewById(R.id.cycleDaysPicker);
		cycleDaysPicker.setCurrent(chore.getCycleDays());
	}

	private Chore getChore() {
		Chore chore = new Chore();
		EditText nameEditor = (EditText) findViewById(R.id.nameEditor);
		chore.setName(nameEditor.getText().toString());
		NumberPicker cycleDaysPicker = (NumberPicker) findViewById(R.id.cycleDaysPicker);
		chore.setCycleDays(cycleDaysPicker.getCurrent());
		return chore;
	}

}
