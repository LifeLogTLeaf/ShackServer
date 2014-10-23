package org.soma.tleaf.couchdb;

import java.net.MalformedURLException;
import java.util.HashMap;

import javax.inject.Inject;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.DbPath;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

@Repository("couchDbConn")
public class CouchDbConnImpl implements CouchDbConn {
	/* properties file을 사용할때 쓴다. */
	@Inject
	private Environment environment;

	private CouchDbInstance couchDbInstance = null;
	private HashMap<String, CouchDbConnector> couchDbConnectorHashMap = new HashMap<String, CouchDbConnector>();

	/**
	 * @author susu
	 * Date Oct 22, 2014 4:34:47 PM
	 * @return Existing Instance if made before, new Instance if first calling
	 * @throws Exception
	 */
	@Override
	public synchronized CouchDbInstance getCouchDbInstance() throws Exception {

		if ( couchDbInstance == null ) {
			couchDbInstance = createCouchDbInstance ();
		}

		return couchDbInstance;
	}

	private CouchDbInstance createCouchDbInstance () throws Exception {

		StdHttpClient httpClient = null;
		try {

			httpClient = (StdHttpClient) new StdHttpClient.Builder()
			.username(environment.getProperty("id"))
			.password(environment.getProperty("password"))
			.url(environment.getProperty("url"))
			.connectionTimeout(100000)
			.build();
		} catch (MalformedURLException e) {
			throw new Exception("Invalid URL" + environment.getProperty("url"));
		}

		return new StdCouchDbInstance( httpClient );

	}

	private static final Logger logger = LoggerFactory.getLogger(CouchDbConnImpl.class);

	/**
	 * @author susu
	 * Date Oct 22, 2014 4:34:47 PM
	 * @return Existing Connector if made before, new Connector if first calling
	 * @throws Exception
	 */
	@Override
	public synchronized CouchDbConnector getCouchDbConnetor(String dbName) throws Exception {

		if ( !couchDbConnectorHashMap.containsKey(dbName) ) {

			logger.info("Doesn't have " + dbName + " creating connector");

			getCouchDbInstance();
			
			CouchDbConnector couchDbConnector = new StdCouchDbConnector(dbName, couchDbInstance);
			couchDbConnector.createDatabaseIfNotExists();
			
			couchDbConnectorHashMap.put(dbName, couchDbConnector );

			return couchDbConnectorHashMap.get(dbName);

		}

		logger.info("Already have " + dbName + " returning existing CouchDbConnector");

		return couchDbConnectorHashMap.get(dbName);

	}

	@Override
	public String toString() {
		return "CouchDbConnImpl [url=" + environment.getProperty("url") + ", id=" + environment.getProperty("id") + ", password="
				+ environment.getProperty("password") + "]";
	}

}
