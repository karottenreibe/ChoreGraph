package be.rottenrei.android.choregraph;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import be.rottenrei.android.choregraph.db.ChoreTable;
import be.rottenrei.android.choregraph.db.Database;
import be.rottenrei.android.choregraph.model.Chore;
import be.rottenrei.android.choregraph.model.ChoreTransport;
import be.rottenrei.android.lib.db.ModelCursorAdapterBase;

public class ListChoresActivity extends ListActivity implements OnClickListener {

	private Database db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ListView listView = getListView();
		Button addButton = (Button) getLayoutInflater().inflate(R.layout.list_header,
				listView, false);
		listView.addHeaderView(addButton);
		addButton.setOnClickListener(this);

		db = new Database(this).open();
		ChoreTable table = db.getChoreTable();
		setListAdapter(new ChoreCursorAdapter(this, table));
	}

	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}

	private class ChoreCursorAdapter extends ModelCursorAdapterBase<Chore> {

		public ChoreCursorAdapter(Context context, ChoreTable table) {
			super(context, table, R.layout.list_item);
		}

		@Override
		protected void fillView(View view, final Chore chore) {
			TextView nameText = (TextView) view.findViewById(R.id.nameText);
			nameText.setText(chore.getName());
			nameText.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(ListChoresActivity.this, AddEditChoreActivity.class);
					intent.putExtra(ChoreTransport.EXTRA, new ChoreTransport(chore));
					startActivityForResult(intent, 0);
				}
			});
		}

	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(this, AddEditChoreActivity.class);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			ChoreTransport transport = data.getParcelableExtra(ChoreTransport.EXTRA);
			ChoreCursorAdapter adapter = (ChoreCursorAdapter) getListAdapter();
			adapter.getCursor().requery();
		}
	}

}