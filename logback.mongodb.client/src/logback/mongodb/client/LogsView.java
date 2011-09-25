package logback.mongodb.client;

import java.net.UnknownHostException;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;

public class LogsView extends ViewPart {

	private TableViewer logs = null;
	
	@Override
	public void createPartControl(Composite parent) {

		Composite root = new Composite(parent, SWT.NONE);
		root.setLayout(new FillLayout());
		logs = new TableViewer(root);
		
		try {
			Mongo mongo = new Mongo(new MongoURI("mongodb://localhost"));
			DBCollection dbCollection = mongo.getDB("jboss").getCollection(
					"logs");
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void setFocus() {
	}

}
