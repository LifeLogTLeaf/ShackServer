package org.soma.tleaf.couchdb;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;

public interface CouchDbConn {

    public abstract CouchDbInstance getCouchDbInstance() throws Exception;
    public abstract CouchDbConnector getCouchDbConnetor(String dbName)
            throws Exception;
}