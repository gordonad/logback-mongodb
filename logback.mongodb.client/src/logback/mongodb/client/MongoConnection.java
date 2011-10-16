package logback.mongodb.client;

import java.net.UnknownHostException;

import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;

public class MongoConnection {

	private static DBCollection dbCollection = null;

	protected static final DBCollection getDBCollection() {
		if (dbCollection == null)
			try {
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
