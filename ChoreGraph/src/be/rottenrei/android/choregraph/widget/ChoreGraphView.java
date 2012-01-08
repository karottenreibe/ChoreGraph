package be.rottenrei.android.choregraph.widget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.Uri;
import android.view.View;
import android.widget.RemoteViews;
import be.rottenrei.android.choregraph.R;
import be.rottenrei.android.choregraph.model.Chore;
import be.rottenrei.android.choregraph.widget.ChoreGraphLayouter.Bar;
import be.rottenrei.android.choregraph.widget.ChoreGraphLayouter.Graph;
import be.rottenrei.android.lib.util.ExceptionUtils;

/**
 * Renders onto the ChoreGraphWidget.
 */
public class ChoreGraphView extends View {

	private final int width;
	private final int height;

	private final List<Chore> chores;

	public ChoreGraphView(Context context, List<Chore> chores, Point size) {
		super(context);
		this.chores = chores;
		this.width = size.x;
		this.height = size.y;
	}

	public void renderTo(RemoteViews views, int imageViewId) {
		this.measure(width, height);
		this.layout(0, 0, width, height);
		this.setDrawingCacheEnabled(true);
		Bitmap bitmap = this.getDrawingCache();
		try {
			String cacheFileName = "graphCache.png";
			FileOutputStream outputStream = getContext().openFileOutput(cacheFileName, 0);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
			outputStream.close();
			File filesDir = getContext().getFilesDir();
			File cacheFile = new File(filesDir, cacheFileName);
			views.setUri(imageViewId, "setImageURI", Uri.fromFile(cacheFile));
		} catch (FileNotFoundException e) {
			ExceptionUtils.handleExceptionWithMessage(e, getContext(), R.string.no_external_storage, ChoreGraphView.class);
		} catch (IOException e) {
			ExceptionUtils.handleExceptionWithMessage(e, getContext(), R.string.no_external_storage, ChoreGraphView.class);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		ChoreGraphLayouter layouter = new ChoreGraphLayouter(chores);
		layouter.layout(width, height);
		Graph graph = layouter.getGraph();
		drawBaseline(canvas, graph.getBaseline());

		List<Bar> bars = graph.getBars();
		for (Bar bar: bars) {
			drawBar(canvas, bar, graph.getBaseline());
		}
	}

	private void drawBar(Canvas canvas, Bar bar, float baseline) {
		GradientDrawable gradient = new GradientDrawable(Orientation.TL_BR, getColors(bar.getPercent()));
		gradient.setCornerRadius(0);
		// we need to invert top and bottom for some strange reason...
		gradient.setBounds((int) bar.getLeft(), (int) bar.getTop(),
				(int) bar.getRight(), (int) bar.getBottom());
		gradient.setDither(true);
		gradient.draw(canvas);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		canvas.drawRect((int) bar.getLeft(), (int) bar.getBottom(),
				(int) bar.getRight(), (int) bar.getBottom() - 3, paint);
		float columnMiddle = (bar.getLeft() + bar.getRight()) / 2;
		float textStart = baseline;
		canvas.save();
		canvas.rotate(-90, columnMiddle, textStart);
		canvas.drawText(bar.getName(), columnMiddle, textStart, paint);
		canvas.restore();
	}

	private int[] getColors(float percent) {
		if (percent <= 0) {
			return new int [] { 0xffee2233, 0xffaa1100 };
		} else if (0 < percent && percent <= 0.25) {
			return new int [] { 0xffff4500, 0xffff8c00 };
		} else if (0.25 < percent && percent <= 0.50) {
			return new int [] { 0xffffff00, 0xffadff2f };
		} else {
			return new int [] { 0xff008000, 0xff006400 };
		}
	}

	private void drawBaseline(Canvas canvas, float baseline) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		canvas.drawLine(0, baseline, getWidth(), baseline, paint);
	}

}
