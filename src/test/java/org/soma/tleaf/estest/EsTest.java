/**
 * 
 */
package org.soma.tleaf.estest;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with Eclipse IDE
 * Author : RichardJ 
 * Date   : Nov 10, 2014 2:52:13 PM
 * Description : 
 */
public class EsTest {
	static Logger logger = LoggerFactory.getLogger(EsTest.class);

	//@Test
	public void riverTest(){
		XContentBuilder builder = null;
		try {
			 builder = jsonBuilder()
				    .startObject()
				        .field("type", "couchdb")
				        .startObject("couchdb")
				        	.field("host", "localhost")
				        	.field("port", 5984)
				        	.field("db", "test")
				        .endObject()	
				        .startObject("index")
				        	.field("index", "my_db")
				        	.field("type", "my_db")
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
		
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost",9300));
		
		IndexResponse response = client.prepareIndex("_river", "my_db", "_meta")
                .setSource(json)
                .execute()
                .actionGet();
	}
	
	//@Test
	public void esQueryTest(){
		Settings settings = ImmutableSettings
                .settingsBuilder()
                .put("cluster.name","elasticsearch_jangyoungjin")
                .build();
		
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost",9300));
		
		
		QueryBuilder qb = QueryBuilders.matchPhraseQuery("title", "ab");
		SearchResponse response = client.prepareSearch("user_e756171d1eb520baecff8c1d1b008cb4")
			    .setQuery(qb)
			    .execute()
			    .actionGet();
			SearchHit[] results = response.getHits().getHits();
			logger.info(""+results.length);    //prints out the id of the document
				
			for (SearchHit hit : results) {
			  logger.info(hit.getId());    //prints out the id of the document
			  Map<String,Object> result = hit.getSource();   //the retrieved document
			  logger.info(""+result.toString());
			}
	}
	
	
	
	
	
	
	
	
	
	
	
}
