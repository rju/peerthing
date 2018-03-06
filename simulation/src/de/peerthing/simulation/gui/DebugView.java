package de.peerthing.simulation.gui;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;

import de.peerthing.simulation.interfaces.IEvaluate;
import de.peerthing.simulation.interfaces.IXPathAttribute;
import de.peerthing.simulation.interfaces.IXPathContainer;
import de.peerthing.simulation.interfaces.IXPathObject;

public class DebugView extends ViewPart implements ISelectionChangedListener {
	public final static String ID = "de.peerthing.simulation.DebugView";

	private TreeViewer tree;

	private Text contentText;

	private TableViewer tableViewer;

	/**
	 * The root object currently displayed in the tree viewer.
	 */
	private IXPathObject rootObject;

	@Override
	public void createPartControl(Composite parent) {
		setPartName("Debug");

		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		ScrolledForm form = toolkit.createScrolledForm(parent);
		form.setText(null);

		form.getBody().setLayout(new FormLayout());
		toolkit.paintBordersFor(form.getBody());

		FormData fd = new FormData();
		fd.top = new FormAttachment(2);
		fd.left = new FormAttachment(0, 5);
		fd.right = new FormAttachment(50);
		fd.bottom = new FormAttachment(100, -2);

		tree = new TreeViewer(form.getBody(), SWT.NONE);
		tree.getControl().setLayoutData(fd);
		tree.setContentProvider(new TreeContentProvider());
		tree.setLabelProvider(new TreeLabelProvider());
		tree.addSelectionChangedListener(this);

		/*
		 * Label lbl = toolkit.createLabel(form.getBody(), "Attributes:"); fd =
		 * new FormData(); fd.top = new FormAttachment(0); fd.left = new
		 * FormAttachment(tree.getControl(), 10); lbl.setLayoutData(fd);
		 */

		Table attributesTable = toolkit.createTable(form.getBody(), SWT.NONE);
		fd = new FormData();
		fd.top = new FormAttachment(2);
		fd.left = new FormAttachment(tree.getControl(), 10);
		fd.right = new FormAttachment(100, -5);
		fd.bottom = new FormAttachment(50);
		attributesTable.setLayoutData(fd);

		attributesTable.setLinesVisible(true);
		attributesTable.setHeaderVisible(true);
		TableColumn col1 = new TableColumn(attributesTable, SWT.NONE);
		col1.setText("Name");
		col1.setWidth(100);
		col1 = new TableColumn(attributesTable, SWT.NONE);
		col1.setText("Value");
		col1.setWidth(200);

		tableViewer = new TableViewer(attributesTable);
		tableViewer.setContentProvider(new AttributeTableContentProvider());
		tableViewer.setLabelProvider(new AttributeTableLabelProvider());

		/*
		 * lbl = toolkit.createLabel(form.getBody(), "Content:"); fd = new
		 * FormData(); fd.top = new FormAttachment(attributesTable, 10); fd.left =
		 * new FormAttachment(tree.getControl(), 10); lbl.setLayoutData(fd);
		 */

		contentText = toolkit.createText(form.getBody(), "", SWT.MULTI
				| SWT.V_SCROLL | SWT.WRAP);
		contentText.setEditable(false);

		fd = new FormData();
		fd.top = new FormAttachment(attributesTable, 10);
		fd.left = new FormAttachment(tree.getControl(), 10);
		fd.right = new FormAttachment(100, -5);
		fd.bottom = new FormAttachment(100, -5);
		contentText.setLayoutData(fd);

	}

	class RootElement {
		Object elem;

		public RootElement(Object elem) {
			this.elem = elem;
		}

		public Object getElement() {
			return elem;
		}
	}

	class TreeContentProvider implements ITreeContentProvider {

		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof IXPathContainer) {
				return ((IXPathContainer) parentElement).getChildAxis()
						.toArray();
			}
			if (parentElement instanceof RootElement) {
				return getChildren(((RootElement) parentElement).getElement());
			}

			return null;
		}

		public Object getParent(Object element) {
			if (element instanceof IXPathObject) {
				return ((IXPathObject) element).getParent();
			}

			return null;
		}

		public boolean hasChildren(Object element) {
			if (element instanceof IXPathContainer) {
				return ((IXPathContainer) element).getChildAxis().size() > 0;
			}
			if (element instanceof RootElement) {
				return true;
			}

			return false;
		}

		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Object[]) {
				return new Object[] { ((Object[]) inputElement)[0] };
			} else if (inputElement instanceof List) {
				return ((List) inputElement).toArray();
			} else {
				return new Object[] { inputElement };
			}
			// return null;
			// return new Object[] {new RootElement(inputElement)};
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	class TreeLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			if (element == null) {
				return "";
			}
			if (element instanceof IXPathObject) {
				IXPathObject obj = (IXPathObject) element;
				if (obj.getElementName() != null) {
					return obj.getElementName() + " ["
							+ obj.getClass().getSimpleName() + "]";
				}
			}            
			if (element instanceof IXPathAttribute) {
				IXPathAttribute attr = (IXPathAttribute) element;
				return attr.getAttributeStringValue();
			}

			return element.toString();
		}

	}

	class AttributeTableContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof IXPathObject) {
				IXPathObject obj = (IXPathObject) inputElement;
				return obj.getAttributeAxisList().toArray();
			}
			return null;
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	class AttributeTableLabelProvider implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof IXPathAttribute) {
				IXPathAttribute attr = (IXPathAttribute) element;
				if (columnIndex == 0) {
					return attr.getAttributeName();
				} else {
					return attr.getAttributeStringValue();
				}
			}
			return null;
		}

		public void addListener(ILabelProviderListener listener) {
		}

		public void dispose() {
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
		}

	}

	public void setObjectToDebug(IXPathObject object) {
		// Store old selection...
		TreeLabelProvider labelProvider = new TreeLabelProvider();
		String oldSelText = null;

		if (tree.getSelection() != null) {
			IStructuredSelection oldSel = (IStructuredSelection) tree
					.getSelection();
			if (oldSel != null) {
				oldSelText = labelProvider.getText(oldSel.getFirstElement());
			}
		}

		rootObject = object;
		tree.setInput(new Object[] { object });
		tree.refresh();
		tree.expandToLevel(3);

		// Try to match old selection...
		boolean matchedSelection = false;
		if (oldSelText != null) {
			for (IXPathObject obj : object.getChildAxis()) {
				if (labelProvider.getText(obj).equals(oldSelText)) {
					matchedSelection = true;
					tree.setSelection(new StructuredSelection(obj));
					break;
				}
			}
		}

		if (!matchedSelection) {
			// if no old selection could be matched, select
			// the root object
			tree.setSelection(new StructuredSelection(object), true);
		}
	}

	/**
	 * Refreshes the viewer with the currently selected root object. Tries to
	 * retain the old selection.
	 * 
	 */
	public void refreshCurrentlyDisplayedObject() {
		ISelection oldSel = tree.getSelection();

		tree.setInput(new Object[] { rootObject });
		tree.refresh();
		tree.expandToLevel(3);
		tree.setSelection(new StructuredSelection(rootObject), true);
		// If the old selection still exists after the update,
		// select that object.
		tree.setSelection(oldSel, true);
	}

	/**
	 * Evaluates the given expression with the currently displayed root object
	 * as the context node and displays the results in the tree. If there is an
	 * error in the expression, a message box is displayed.
	 * 
	 * @param expr
	 */
	public void evaluateXPathExpression(String expr) {
		if (rootObject instanceof IEvaluate) {
			Object res = null;
			try {
				res = ((IEvaluate) rootObject).evaluate(expr);
			} catch (RuntimeException e) {
				MessageDialog.openError(getSite().getShell(),
						"Error in XPath expression", e.getMessage());
			}
			if (res != null) {
				contentText.setText("");
				tableViewer.setInput(null);

				tree.setInput(res);
				tree.refresh();
			}
		}
	}

	@Override
	public void setFocus() {
		tree.getControl().setFocus();
	}

	public void selectionChanged(SelectionChangedEvent event) {
		Object selObj = ((IStructuredSelection) event.getSelection())
				.getFirstElement();
		if (selObj instanceof RootElement) {
			selObj = ((RootElement) selObj).getElement();
		}
		if (selObj instanceof IXPathObject) {
			tableViewer.setInput(selObj);
			tableViewer.refresh();

			IXPathObject obj = (IXPathObject) selObj;

			if (obj.getElementStringValue() != null) {
				contentText.setText(obj.getElementStringValue());
			} else {
				contentText.setText("");
			}
		}
	}

}
