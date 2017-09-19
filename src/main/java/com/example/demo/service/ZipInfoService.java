package com.example.demo.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.example.demo.bean.mongo.StateStats;
import com.example.demo.bean.mongo.ZipInfo;
import com.example.demo.bean.mongo.ZipInfoStats;
import com.example.demo.dao.mongo.ZipInfoDao;

@Service
public class ZipInfoService {

	@Autowired
	private ZipInfoDao zipInfoDao;
	@Autowired
	private MongoOperations mongoOperations;

	public Object init() {
		initZipInfoStats();
		return zipInfoDao.findAll();
	}
	public Object aggregation1() {
		return zipInfoDao.findAll();
	}
	public Object aggregation2() {

		TypedAggregation<ZipInfo> aggregation = newAggregation(ZipInfo.class, group("state", "city")//
				.sum("population").as("pop"), //
				sort(Direction.ASC, "pop", "state", "city"), //
				group("state")//
						.last("city").as("biggestCity")//
						.last("pop").as("biggestPop")//
						.first("city").as("smallestCity")//
						.first("pop").as("smallestPop"), //
				project()//
						.and("state").previousOperation()//
						.and("biggestCity")//
						.nested(bind("name", "biggestCity").and("population", "biggestPop"))//
						.and("smallestCity")//
						.nested(bind("name", "smallestCity").and("population", "smallestPop")), //
				sort(Direction.ASC, "state")//
		);

		AggregationResults<ZipInfoStats> result = mongoOperations.aggregate(aggregation, ZipInfoStats.class);
		//下面的更通用些,只不过上边是用的对象的思想
		//		AggregationResults<ZipInfoStats> result = mongoOperations.aggregate(aggregation, ZipInfoStats.class);
		ZipInfoStats firstZipInfoStats = result.getMappedResults().get(0);
		
		return result.getMappedResults();
	}
	public Object aggregation3() {
		
		TypedAggregation<ZipInfo> agg = newAggregation(ZipInfo.class,
			    group("state").sum("population").as("totalPop"),
			    sort(Direction.ASC, previousOperation(), "totalPop"),
			    match(Criteria.where("totalPop").gte(100))
			);

			AggregationResults<StateStats> result = mongoOperations.aggregate(agg, StateStats.class);
//		List<StateStats> stateStatsList = result.getMappedResults();
//			AggregationResults<Map> result = mongoOperations.aggregate(agg, Map.class);
//			List<Map> stateStatsList = result.getMappedResults();
		
		return result.getMappedResults();
	}

	private void initZipInfoStats() {
		
		zipInfoDao.deleteAll();
		
		ArrayList<ZipInfo> arrayList = new ArrayList<ZipInfo>();
		for (int i = 0; i < 5; i++) {
			int tempint=i+1;
			ZipInfo zipInfo = new ZipInfo();
			zipInfo.setCity("北京"+tempint);
			zipInfo.setLocation(new double[] {20+tempint,30+tempint});
			zipInfo.setPopulation(new Random().nextInt(1000));
			zipInfo.setState("一线城市");
			arrayList.add(zipInfo);
		}
		for (int i = 0; i < 2; i++) {
			int tempint=i+1;
			ZipInfo zipInfo = new ZipInfo();
			zipInfo.setCity("北京"+tempint);
			zipInfo.setLocation(new double[] {20+tempint,30+tempint});
			zipInfo.setPopulation(new Random().nextInt(1000));
			zipInfo.setState("一线城市");
			arrayList.add(zipInfo);
		}
		for (int i = 0; i < 5; i++) {
			int tempint=i+1;
			ZipInfo zipInfo = new ZipInfo();
			zipInfo.setCity("山东"+tempint);
			zipInfo.setLocation(new double[] {20+tempint,30+tempint});
			zipInfo.setPopulation(new Random().nextInt(100));
			zipInfo.setState("二线城市");
			arrayList.add(zipInfo);
			
		}
		zipInfoDao.saveAll(arrayList);
	}

}
