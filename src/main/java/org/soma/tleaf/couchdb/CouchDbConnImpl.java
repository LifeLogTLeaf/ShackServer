package org.soma.tleaf.couchdb;

import java.net.MalformedURLException;

import javax.inject.Inject;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

@Repository("couchDbConn")
public class CouchDbConnImpl implements CouchDbConn {
    /* properties file을 사용할때 쓴다. */
    @Inject
    private Environment environment;

    /* 예외처리부분 조사. */
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


    @Override
    public CouchDbConnector getCouchDbConnetor(String dbName) throws Exception {
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
        CouchDbConnector db = new StdCouchDbConnector(dbName, dbInstance);
        return db;
    }

    @Override
    public String toString() {
        return "CouchDbConnImpl [url=" + environment.getProperty("url") + ", id=" + environment.getProperty("id") + ", password="
                + environment.getProperty("password") + "]";
    }

}
