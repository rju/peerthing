package de.peerthing.simulation.data;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.junit.Before;
import org.junit.Test;

import de.peerthing.scenarioeditor.interchange.ISICommand;

public class UserTaskTest extends MockObjectTestCase {

	Mock mockISICommand;

	List commandList;

	UserTask task;

	@Before
	public void setUp() {
		mockISICommand = mock(ISICommand.class);
		commandList = new ArrayList();
		for (int i = 0; i <= 2; i++) {
			commandList.add(mockISICommand.proxy());
		}

		task = new UserTask(1, commandList);
	}

	@Test
	public void testRepeatCommandSequence() {
		// no Expression, no loop
		assertFalse(task.repeatCommandSequence());

		// Expression -> true; no Loop

		// Expression -> false; no loop

		// No Expression; limited Loop

		// No Expression; endless loop
	}

}
