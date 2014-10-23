package org.soma.tleaf.couchdb;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.soma.tleaf.exception.DatabaseConnectionException;

public interface CouchDbConn {

    public abstract CouchDbInstance getCouchDbInstance() throws DatabaseConnectionException;
    public abstract CouchDbConnector getCouchDbConnetor(String dbName)
            throws DatabaseConnectionException;
}