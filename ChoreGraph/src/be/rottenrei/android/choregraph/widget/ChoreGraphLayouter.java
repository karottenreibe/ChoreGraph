package be.rottenrei.android.choregraph.widget;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import be.rottenrei.android.choregraph.model.Chore;

/**
 * Layouts the graph for the {@link ChoreGraphView}.
 * 
 * [0,0] is in the top left corner of the widget.
 */
public class ChoreGraphLayouter {

	private final List<Chore> chores;
	private Graph graph;

	public ChoreGraphLayouter(List<Chore> chores) {
		this.chores = chores;
	}

	public void layout(int width, int height) {
		Log.e("be.rottenrei.flashcards.test", width + ", " + height);
		graph = new Graph(width, height, chores.size());
		for (Chore chore : chores) {
			float percent = chore.getDaysUntilDue() / chore.getCycleDays();
			graph.addBar(chore.getName(), chore.getDaysUntilDue(), percent);
		}
		graph.finish();
	}

	public Graph getGraph() {
		return graph;
	}

	public static class Graph {

		private final List<Bar> bars;
		private final float width;
		private final float height;
		private final float size;
		private final float barWidth;
		private float minHeight;
		private float maxHeight;
		private float baseline;

		public Graph(int width, int height, int size) {
			bars = new ArrayList<Bar>(size);
			this.width = width;
			this.height = height;
			this.size = size;
			minHeight = 0;
			maxHeight = 0;
			barWidth = this.width / (this.size * 2 + 1);
		}

		public void addBar(String name, float height, float percent) {
			Bar bar = new Bar(name, height);
			bars.add(bar);
			bar.setLeft(barWidth * (bars.size() * 2 - 1));
			bar.setRight(barWidth * (bars.size() * 2));
			minHeight = Math.min(minHeight, height);
			maxHeight = Math.max(maxHeight, height);
			bar.setPercent(percent);
		}

		public void finish() {
			calculateBaseline();
			calculateTopBottom();
		}

		private void calculateBaseline() {
			baseline = maxHeight * getUnitHeight();
		}

		private void calculateTopBottom() {
			for (Bar bar : bars) {
				float height = bar.getHeight();
				float top = baseline - height * getUnitHeight();
				float bottom = baseline;
				if (top < bottom) {
					float tmp = top;
					top = bottom;
					bottom = tmp;
				}
				bar.setTop(top);
				bar.setBottom(bottom);
			}
		}

		private float getUnitHeight() {
			return height / (maxHeight - minHeight);
		}

		public List<Bar> getBars() {
			return bars;
		}

		public float getBaseline() {
			return baseline;
		}

	}

	public static class Bar {

		private final String name;
		private float top;
		private float bottom;
		private float left;
		private float right;
		private final float height;
		private float percent;

		public Bar(String name, float height) {
			this.name = name;
			this.height = height;
		}

		public float getPercent() {
			return percent;
		}

		public void setPercent(float percent) {
			this.percent = percent;
		}

		public float getHeight() {
			return height;
		}

		public float getTop() {
			return top;
		}

		public void setTop(float top) {
			this.top = top;
		}

		public float getBottom() {
			return bottom;
		}

		public void setBottom(float bottom) {
			this.bottom = bottom;
		}

		public float getLeft() {
			return left;
		}

		public void setLeft(float left) {
			this.left = left;
		}

		public float getRight() {
			return right;
		}

		public void setRight(float right) {
			this.right = right;
		}

		public String getName() {
			return name;
		}

	}

}
