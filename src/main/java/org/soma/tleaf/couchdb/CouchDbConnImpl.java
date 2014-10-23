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
     * 기존 소스코드 
     */
    @Override
    public CouchDbInstance getCouchDbInstance() throws Exception {
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

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        return dbInstance;
    }
    
    private static final Logger logger = LoggerFactory.getLogger(CouchDbConnImpl.class);
    
    public synchronized CouchDbConnector getCouchDbConnetor(String dbName) throws Exception {
    	
    	if ( !couchDbConnectorHashMap.containsKey(dbName) ) {
    		
    		logger.info("Doesn't have " + dbName + " creating connector");
    		
    		getCouchDbInstance();
        	if ( !couchDbInstance.checkIfDbExists(new DbPath(dbName)) ) couchDbInstance.createDatabase(dbName);
        	couchDbConnectorHashMap.put(dbName, new StdCouchDbConnector(dbName, couchDbInstance) );
        	
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
