package de.peerthing.visualization.queryeditor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

/**
 * Configuration for the TextViewer used in the query editor.
 * It mainly uses SQLRuleScanner internally.
 * 
 * @author Michael Gottschalk
 *
 */
public class SQLSourceViewerConfig extends SourceViewerConfiguration {
	private SQLRuleScanner scanner;

	protected SQLRuleScanner getTagScanner() {
		if (scanner == null) {
			scanner = new SQLRuleScanner();
		}
		return scanner;
	}

	/**
	 * Define reconciler for SQL text editor
	 */
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();
		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getTagScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
		return reconciler;
	}

}