package logback.mongodb.client;

import java.net.UnknownHostException;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;

public class LogsView extends ViewPart {

	private TableViewer logs = null;

	private DBCollection dbCollection = null;

	@Override
	public void createPartControl(Composite parent) {
		Composite root = new Composite(parent, SWT.NONE);
		root.setLayout(new GridLayout(1, true));

		Text searchText = new Text(root, SWT.BORDER | SWT.SEARCH);
		searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.HORIZONTAL_ALIGN_FILL));
		searchText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent keyEvent) {
			}
		});

		logs = new TableViewer(root, SWT.VIRTUAL | SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
		Table logsTable = logs.getTable();
		logsTable.setHeaderVisible(true);
		logsTable.setLinesVisible(true);
		for (LogProperty logProperty : LogProperty.values()) {
			TableColumn column = new TableColumn(logsTable, SWT.NONE);
			column.setText(logProperty.propertyName());
			column.setWidth(logProperty.columnWidth());
			column.setMoveable(true);
		}
		logsTable.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_VERTICAL
				| GridData.VERTICAL_ALIGN_FILL));
		logs.setContentProvider(new LogsContentProvider(logs, getDBCollection()
				.find()));
		logs.setLabelProvider(new LogsLabelProvider());
		logs.setItemCount((int) getDBCollection().count());
	}

	@Override
	public void setFocus() {
		if (logs != null)
			logs.getTable().setFocus();
	}

	protected DBCollection getDBCollection() {
		if (dbCollection == null)
			try {
				// TODO make mongo URI configurable
				Mongo mongo = new Mongo(new MongoURI("mongodb://localhost"));
				dbCollection = mongo.getDB("jboss").getCollection("logs");
			} catch (MongoException | UnknownHostException exception) {
				exception.printStackTrace();
			}
		return dbCollection;
	}

}
