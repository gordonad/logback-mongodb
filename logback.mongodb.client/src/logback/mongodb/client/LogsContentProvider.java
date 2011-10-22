package logback.mongodb.client;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ILazyContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class LogsContentProvider implements ILazyContentProvider {

	private TableViewer viewer = null;

	private DBCursor dbCursor = null;

	private List<DBObject> seen = new LinkedList<DBObject>();

	protected LogsContentProvider(TableViewer viewer, DBCursor dbCursor) {
		this.viewer = viewer;
		this.dbCursor = dbCursor;
	}

	@Override
	public void updateElement(int index) {
		int i = seen.size();
		while (index >= i) {
			seen.add(i, dbCursor.next());
			i++;
		}
		viewer.replace(seen.get(index), index);
	}

	@Override
	public void dispose() {
		dbCursor.close();
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// nothing to do
	}

}
