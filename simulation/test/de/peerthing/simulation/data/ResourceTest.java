package de.peerthing.simulation.data;

import java.util.ArrayList;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.junit.Before;
import org.junit.Test;

import de.peerthing.scenarioeditor.interchange.ISIResourceCategory;
import de.peerthing.simulation.interfaces.DataFactory;
import de.peerthing.simulation.interfaces.IResourceDefinition;
import de.peerthing.simulation.interfaces.ISegment;

import junit.framework.JUnit4TestAdapter;

public class ResourceTest extends MockObjectTestCase {

	ISegment seg1, seg2, seg3, seg4, seg5, seg6, seg7;

	ArrayList<String> qualityList;

	Resource res;

	Mock mockISIResourceCategory;

	Mock mockSeg1;

	@Before
	public void setUp() {
		qualityList = new ArrayList<String>();
		qualityList.add("bad");
		qualityList.add("normal");
		qualityList.add("good");

		// mockSeg1 = mock(ISegment.class);
		// mockSeg1.expects( once()).method("Segment").with( eq(0), eq(10),
		// eq("bad"));

		// seg1 = (ISegment)mockSeg1.proxy();

		seg1 = new Segment(0, 10, "bad");
		seg2 = new Segment(10, 15, "bad");
		seg3 = new Segment(15, 30, "normal");
		seg4 = new Segment(20, 25, "good");
		seg5 = new Segment(25, 35, "bad");
		seg6 = new Segment(20, 30, "bad");
		seg7 = new Segment(11, 19, "bad");

		// TODO Cast Fehler korrigieren
		mockISIResourceCategory = mock(ISIResourceCategory.class);
		IResourceDefinition rDef = DataFactory.createResourceDefinition(1, 1,
				1, 1, (ISIResourceCategory) mockISIResourceCategory.proxy());

		res = new Resource(rDef.getId());
		res.setQuality(qualityList);

		// fill Elementlist in Resource
		res.insertSegment(seg5);
	}

	@Test
	public void testInsertSegmentEqualQuality() {
		// make sure Resource is not NULL
		assertNotNull(res);

		// make sure elementlist contains only one element
		assertTrue(1 == res.getChildAxis().size());

		/** ******************* */
		// Case 1: segment.end < current.start | equal quality
		// insert one segment
		res.insertSegment(seg1);
		assertEquals(2, res.getChildAxis().size());

// The methode was changed. This test is not needed anymore
		// make sure that seg1 lies before seg5
		//assertSame(res.getChildAxis().get(1), seg1);

		// crosscheck
		assertNotSame(res.getChildAxis().get(0), seg2);
		assertSame(res.getChildAxis().get(0), seg5);

		// remove added segment
		// do not operate on the list
		// res.getChildAxis().remove(0);
		res.removeElement(seg1);
		// make sure Segment1 was removed
//		assertNotSame(res.getChildAxis().get(1), seg1);
		assertSame(1, res.getChildAxis().size());

		/** ******************* */
		// Case 2: segement.end = current.start; Segments should become one
		// insert segment
		res.insertSegment(seg6);

		// Segments should become one
		assertTrue(res.getChildAxis().size() == 1);
		// the new Segment has start 20 and end 35
		assertTrue(((ISegment) res.getChildAxis().get(0)).getStart() == 20
				&& ((ISegment) res.getChildAxis().get(0)).getEnd() == 35);

		// crosscheck
		assertFalse(((ISegment) res.getChildAxis().get(0)).getStart() == 20
				&& ((ISegment) res.getChildAxis().get(0)).getEnd() == 25);

		/** ****************** */

		// Case 3: segment.start > current.end; Should compare with the
		// following Segment
		// Note: elementlist contains now one Segment(20-35, "bad"), to test the
		// following
		// case an other Segment must be inserted.
		res.insertSegment(seg1);
		
		//Method was changed. If the inserted segment doesn't overlap an existing segment, the inserted segment will be appended to the 
		//list of segments.
		assertSame(res.getChildAxis().get(1), seg1);
		// make sure there are two segments in the list.
		assertTrue(res.getChildAxis().size() == 2);

		// insert segment
		res.insertSegment(seg7);
		// make sure elementlist contains three elements
		assertTrue(res.getChildAxis().size() == 3);

		//see above
		// the inserted segment will be appended to the end of the list.
		assertSame(res.getChildAxis().get(2), seg7);

		
		/** ******************* */

		
		// Case 4: segment.start < current.end && segment.end > current.end; Should expand the containing
		// segment
		// insert Segment
		res.insertSegment(seg2);

		//there still should be three elements
		assertSame(3, res.getChildAxis().size());
		
		//This segemnt should expand the existing seg7
		assertSame(res.getChildAxis().get(2), seg2 );
		
		// make sure the first segment starts at 10 and ends now at 19 not at 15.
		assertTrue(((ISegment) res.getChildAxis().get(2)).getEnd() == 19
				&& ((ISegment) res.getChildAxis().get(2)).getStart() == 10);


		// Case 5: Segment to insert lies between the start and end of the
		// current segment; Should
		// nothing happen.
		// insert seg1 again.
		// res.insertSegment(seg1);
		// make sure, the first Segment in the list starts still at 0 and ends
		// at 15.
		// assertTrue(((ISegment)res.getElementList().get(0)).getEnd() == 15 &&
		// ((ISegment)res.getElementList().get(0)).getStart() == 0);
	}


	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(ResourceTest.class);
	}
}
