package be.rottenrei.android.choregraph;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.RemoteViews;
import be.rottenrei.android.choregraph.model.Chore;

/**
 * Renders onto the ChoreGraphWidget.
 */
public class ChoreGraphView extends View {

	private static final int HEIGHT = 220;
	private static final int WIDTH = 220;

	private final List<Chore> chores;

	public ChoreGraphView(Context context, List<Chore> chores) {
		super(context);
		this.chores = chores;
	}

	public void renderTo(RemoteViews views, int imageViewId) {
		this.measure(WIDTH, HEIGHT);
		this.layout(0, 0, WIDTH, HEIGHT);
		this.setDrawingCacheEnabled(true);
		Bitmap bitmap = this.getDrawingCache();
		views.setImageViewBitmap(imageViewId, bitmap);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int min = 0;
		int max = 0;
		for (Chore chore : chores) {
			min = Math.min(min, chore.getDaysUntilDue());
			max = Math.max(max, chore.getDaysUntilDue());
		}
		int baseline = getHeight() - max;
		drawBaseline(canvas, baseline);

		float columns = chores.size() * 2 + 1;
		float columnWidth = getWidth() / columns;
		float range = (max - min);
		float oneHeight = getHeight() / range;
		for (int i = 0; i < chores.size(); i++) {
			Chore chore = chores.get(i);
			int column = (i + 1) * 2;
			float height = oneHeight * chore.getDaysUntilDue();
			drawColumn(canvas, chore.getName(), column, columnWidth, height, baseline);
		}
	}

	private void drawColumn(Canvas canvas, String name, int column, float columnWidth, float height, int baseline) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.BLUE);
		float left = ((column - 1) * columnWidth);
		float top = (height);
		float right = (column * columnWidth);
		float bottom = baseline;
		canvas.drawRect(left, top, right, bottom, paint);

		paint.setColor(Color.RED);
		float columnMiddle = (left + right) / 2;
		canvas.save();
		canvas.rotate(-90, columnMiddle, baseline);
		canvas.drawText(name, columnMiddle, baseline, paint);
		canvas.restore();
	}

	private void drawBaseline(Canvas canvas, int y) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		canvas.drawLine(0, y, getWidth(), y, paint);
	}

}
