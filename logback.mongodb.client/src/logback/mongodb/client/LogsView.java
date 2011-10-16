package logback.mongodb.client;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class LogsView extends ViewPart {

	private TableViewer logs = null;

	@Override
	public void createPartControl(Composite parent) {
		Composite root = new Composite(parent, SWT.NONE);
		root.setLayout(new FillLayout());
		logs = new TableViewer(root, SWT.VIRTUAL | SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);
		logs.setContentProvider(new LogsContentProvider(logs));
		logs.setLabelProvider(new LogsLabelProvider());
		logs.setItemCount((int) MongoConnection.getDBCollection().count());
	}

	@Override
	public void setFocus() {
		if (logs != null)
			logs.getTable().setFocus();
	}

}
