/**
 * 
 */
package org.soma.tleaf.esdb;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

import java.io.IOException;

import javax.inject.Inject;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

/**
 * Created with Eclipse IDE
 * Author : RichardJ 
 * Date   : Nov 11, 2014 2:49:54 PM
 * Description : 
 */
@Repository("esDbConn")
public class EsdbConnimple implements EsdbConn {
	@Inject
	private Environment environment;

	/**
	 * need to refactor
	 */
	public void replication(String dbName) {
		XContentBuilder builder = null;
		try {
			 builder = jsonBuilder()
				    .startObject()
				        .field("type", "couchdb")
				        .startObject("couchdb")
				        	.field("host", "localhost")
				        	.field("port", 5984)
				        	.field("db", dbName)
				        .endObject()	
				        .startObject("index")
				        	.field("index", dbName)
				        	.field("type", dbName)
				        	.field("bulk_size", "100")
				        	.field("bulk_timeout", "10ms")
				        .endObject()		
				    .endObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String json = null;
		try {
			json = builder.string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Settings settings = ImmutableSettings
                .settingsBuilder()
                .put("cluster.name","elasticsearch_jangyoungjin")
                .build();
		
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(environment.getProperty("es_domain"),9300));
		
		IndexResponse response = client.prepareIndex("_river", dbName, "_meta")
                .setSource(json)
                .execute()
                .actionGet();
		
	}
	
}
