package logback.mongodb.client;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

public class LogsView extends ViewPart {

	private TableViewer logs = null;

	@Override
	public void createPartControl(Composite parent) {
		Composite root = new Composite(parent, SWT.NONE);
		root.setLayout(new FillLayout());
		logs = new TableViewer(root, SWT.VIRTUAL | SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);

		Table logsTable = logs.getTable();
		logsTable.setHeaderVisible(true);

		TableColumn column = new TableColumn(logsTable, SWT.NONE);
		column.setText("message");
		column.setWidth(300);
		column.setMoveable(true);

		column = new TableColumn(logsTable, SWT.NONE);
		column.setText("logger");
		column.setWidth(100);
		column.setMoveable(true);

		column = new TableColumn(logsTable, SWT.NONE);
		column.setText("thread");
		column.setWidth(100);
		column.setMoveable(true);

		column = new TableColumn(logsTable, SWT.NONE);
		column.setText("timestamp");
		column.setWidth(100);
		column.setMoveable(true);

		column = new TableColumn(logsTable, SWT.NONE);
		column.setText("level");
		column.setWidth(50);
		column.setMoveable(true);

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
