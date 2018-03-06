package de.peerthing.systembehavioureditor.interchange;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.systembehavioureditor.model.IAction;
import de.peerthing.systembehavioureditor.model.ICondition;
import de.peerthing.systembehavioureditor.model.IContentContainer;
import de.peerthing.systembehavioureditor.model.ITransitionContent;

abstract class AIContentContainer implements IAIContentContainer {
	List<IAITransitionContent> content = new ArrayList<IAITransitionContent>();

	/**
	 * Extracts the content objects from an IContentContainer.
	 *
	 */
	protected AIContentContainer(IContentContainer container) {
		for (ITransitionContent con : container.getContents()) {
			if (con instanceof IAction) {
				content.add(new AIAction(((IAction) con), this));
			} else if (con instanceof ICondition) {
				content.add(new AICondition(((ICondition) con), this));
			}
		}
	}

	public List<IAITransitionContent> getContents() {
		return content;
	}

}
