package be.rottenrei.android.choregraph.test;

import java.util.ArrayList;
import java.util.List;

import be.rottenrei.android.choregraph.mock.MockChore;
import be.rottenrei.android.choregraph.model.Chore;
import be.rottenrei.android.choregraph.widget.ChoreGraphLayouter;
import be.rottenrei.android.choregraph.widget.ChoreGraphLayouter.Bar;
import be.rottenrei.android.choregraph.widget.ChoreGraphLayouter.Graph;
import be.rottenrei.android.lib.test.base.UnitTestBase;

public class ChoreGraphLayouterTest extends UnitTestBase {

	private static final float BASELINE = 60f;
	private Graph graph;
	private List<Bar> bars;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		List<Chore> chores = new ArrayList<Chore>(3);
		chores.add(new MockChore(-1));
		chores.add(new MockChore(0));
		chores.add(new MockChore(2));
		ChoreGraphLayouter layouter = new ChoreGraphLayouter(chores);
		layouter.layout(7, 90);
		graph = layouter.getGraph();
		bars = graph.getBars();
	}

	public void testBaseline() {
		assertEquals(BASELINE, graph.getBaseline());
	}

	public void testLeftRight() {
		assertEquals(1f, bars.get(0).getLeft());
		assertEquals(2f, bars.get(0).getRight());

		assertEquals(3f, bars.get(1).getLeft());
		assertEquals(4f, bars.get(1).getRight());

		assertEquals(5f, bars.get(2).getLeft());
		assertEquals(6f, bars.get(2).getRight());
	}

	public void testTopBottom() {
		assertEquals(BASELINE, bars.get(0).getBottom());
		assertEquals(90f, bars.get(0).getTop());

		assertEquals(BASELINE, bars.get(1).getBottom());
		assertEquals(BASELINE, bars.get(1).getTop());

		assertEquals(0f, bars.get(2).getBottom());
		assertEquals(BASELINE, bars.get(2).getTop());
	}

}
