package logback.mongodb.client;

public enum LogProperty {

	MESSAGE(300), LOGGER(100), THREAD(100), TIMESTAMP(100), LEVEL(50);

	private int columnWidth;

	private LogProperty(int columnWidth) {
		this.columnWidth = columnWidth;
	}

	public int columnWidth() {
		return columnWidth;
	}

	public String propertyName() {
		return super.name().toLowerCase();
	}

}
