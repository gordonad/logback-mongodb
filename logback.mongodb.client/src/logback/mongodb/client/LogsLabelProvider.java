package logback.mongodb.client;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mongodb.DBObject;

public class LogsLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof DBObject) {
			DBObject log = (DBObject) element;
			switch (columnIndex) {
			case 0:
				return log.get(LogProperty.MESSAGE.propertyName()).toString();
			case 1:
				return log.get(LogProperty.LOGGER.propertyName()).toString();
			case 2:
				return log.get(LogProperty.THREAD.propertyName()).toString();
			case 3:
				return log.get(LogProperty.TIMESTAMP.propertyName()).toString();
			case 4:
				return log.get(LogProperty.LEVEL.propertyName()).toString();
			default:
				break;
			}
		}
		return getText(element);
	}

}
