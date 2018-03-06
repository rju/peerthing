package de.peerthing.simulation.data;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.junit.Before;
import org.junit.Test;

import de.peerthing.scenarioeditor.interchange.ISICommand;

public class UserStackElementTest extends MockObjectTestCase {

	UserStackElement use;

	Mock mockISICommand;

	List commandList;

	@Before
	public void setUp() {
		mockISICommand = mock(ISICommand.class);
		commandList = new ArrayList<ISICommand>();
		for (int i = 0; i <= 2; i++) {
			commandList.add(mockISICommand.proxy());
		}

		use = new UserStackElement(commandList, 0, null);
	}

	@Test
	public void testGetNextCommand() {
		// Commandlist contains three elements. The next method call returns one
		// element.
		// Position = 0.
		assertNotNull(use.getNextCommand());

		// set position to 1 and call getNextCommand again.
		use.setNextCommand(1);
		assertNotNull(use.getNextCommand());

		// set position to last element and call getNextCommand.
		use.setNextCommand(commandList.size() - 1);
		assertNotNull(use.getNextCommand());

		// Set position to lastElement+1
		use.setNextCommand(commandList.size());
		assertNull(use.getNextCommand());
	}
}
