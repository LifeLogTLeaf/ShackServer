/**
 * 
 */
package org.soma.tleaf.repository;

import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.soma.tleaf.domain.RawData;
import org.soma.tleaf.domain.SimpleRawData;

/**
 * Created with Eclipse IDE
 * Author : RichardJ 
 * Date   : Oct 30, 2014 8:20:08 PM
 * Description : 
 * 도메인 데이터이름으로 디자인뷰 이름이 만들어진다.
 * 밑에 View 어노테이션으로 만들어지는 뷰 네임은 도메인 이른 하위의 뷰이름으로 간다.
 */

public class SimpleRawDataRepository extends CouchDbRepositorySupport<SimpleRawData> {
	public SimpleRawDataRepository(CouchDbConnector db) {
		super(SimpleRawData.class, db);
		initStandardDesignDocument();
	}

	@Override
	@View(name = "all", map = "function(doc) { if (doc.time) emit( doc.time, doc )}")
	public List<SimpleRawData> getAll() {
		// TODO Auto-generated method stub
		//ViewQuery q = createQuery("all").descending(true).startKey("2014-10-30T20:02:48+09:09").endKey("2014-10-30T20:02:48+09:07");
		ViewQuery q = createQuery("all").startKey("2014-10-30T20:02:48+09:00").endKey("2014-10-30T20:02:48+09:20");
		
		return db.queryView(q, SimpleRawData.class);
	}
	
	
	@View(name = "app", map = "function(doc) { if (doc.app_id) {  emit( [doc.app_id, doc.time] , doc ); } }")
	public List<SimpleRawData> getLogByAppId() {
		ComplexKey start = ComplexKey.of("tiary", "2014-10-30T20:02:48+09:00");
		ComplexKey end = ComplexKey.of("tiary", "2014-10-30T20:02:48+09:20");
		
		ViewQuery q = createQuery("app").startKey(end).endKey(start).descending(true);
		
		return db.queryView(q, SimpleRawData.class);
	}
	
	

}
