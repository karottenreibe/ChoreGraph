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
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		canvas.drawText("test", 50, 50, paint);
	}

}
