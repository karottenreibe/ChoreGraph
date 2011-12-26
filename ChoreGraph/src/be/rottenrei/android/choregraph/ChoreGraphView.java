package be.rottenrei.android.choregraph;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.view.View;
import android.widget.RemoteViews;
import be.rottenrei.android.choregraph.ChoreGraphLayouter.Bar;
import be.rottenrei.android.choregraph.ChoreGraphLayouter.Graph;
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
		ChoreGraphLayouter layouter = new ChoreGraphLayouter(chores);
		layouter.layout(WIDTH, HEIGHT);
		Graph graph = layouter.getGraph();
		drawBaseline(canvas, graph.getBaseline());

		List<Bar> bars = graph.getBars();
		for (Bar bar: bars) {
			drawBar(canvas, bar, graph.getBaseline());
		}
	}

	private void drawBar(Canvas canvas, Bar bar, float baseline) {
		GradientDrawable gradient = new GradientDrawable(Orientation.TL_BR, getColors());
		gradient.setCornerRadius(0);
		gradient.setBounds((int) bar.getLeft(), (int) bar.getTop(),
				(int) bar.getRight(), (int) bar.getBottom());
		gradient.setDither(true);
		gradient.draw(canvas);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		float columnMiddle = (bar.getLeft() + bar.getRight()) / 2;
		float textStart = baseline;
		canvas.save();
		canvas.rotate(-90, columnMiddle, textStart);
		canvas.drawText(bar.getName(), columnMiddle, textStart, paint);
		canvas.restore();
	}

	private int[] getColors() {
		return new int [] { 0xffee2233, 0xffaa1100 };
	}

	private void drawBaseline(Canvas canvas, float baseline) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		canvas.drawLine(0, baseline, getWidth(), baseline, paint);
	}

}
