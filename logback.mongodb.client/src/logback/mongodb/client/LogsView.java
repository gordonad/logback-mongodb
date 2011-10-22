package logback.mongodb.client;

import java.net.UnknownHostException;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
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
		root.setLayout(new FillLayout());
		logs = new TableViewer(root, SWT.VIRTUAL | SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
		Table logsTable = logs.getTable();
		logsTable.setHeaderVisible(true);
		for (LogProperty logProperty : LogProperty.values()) {
			TableColumn column = new TableColumn(logsTable, SWT.NONE);
			column.setText(logProperty.propertyName());
			column.setWidth(logProperty.columnWidth());
			column.setMoveable(true);
		}
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
				// TODO make this configurable
				Mongo mongo = new Mongo(new MongoURI("mongodb://localhost"));
				dbCollection = mongo.getDB("jboss").getCollection("logs");
			} catch (MongoException mongoException) {
				mongoException.printStackTrace();
			} catch (UnknownHostException unknownHostException) {
				unknownHostException.printStackTrace();
			}
		return dbCollection;
	}

}
