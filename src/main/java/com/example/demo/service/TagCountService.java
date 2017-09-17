package com.example.demo.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import com.example.demo.bean.mongo.PTagCount;
import com.example.demo.bean.mongo.TagCount;
import com.example.demo.dao.mongo.TagCountDao;

@Service
public class TagCountService {

	@Autowired
	private TagCountDao tagCountDao;
	@Autowired
	private MongoOperations mongoOperations;

	public Object aggregation1() {
		List<TagCount> findAll = tagCountDao.findAll();
		if (findAll.size() <= 0) {
			initData();
			findAll = tagCountDao.findAll();
		}

		return findAll;
	}

	public Object aggregation2() {

		Aggregation agg = newAggregation(//
				project("tag"), //
				unwind("tag"), //
				group("tag").count().as("count"), //
				project("count").and("tag").previousOperation(), //
				sort(Direction.DESC, "count")//
		);

		AggregationResults<PTagCount> results = mongoOperations.aggregate(agg, "tagCount", PTagCount.class);
		List<PTagCount> tagCount = results.getMappedResults();

		return tagCount;
	}

	
	public Object aggregation3() {
		
		Aggregation agg = newAggregation(//
				project("tag"), //
				unwind("tag"), //
				group("tag").count().as("count"), //
				project("count").and("tag").previousOperation(), //
				sort(Direction.DESC, "count")//
				);
		
		AggregationResults<PTagCount> results = mongoOperations.aggregate(agg, "tagCount", PTagCount.class);
		List<PTagCount> tagCount = results.getMappedResults();
		
		return tagCount;
	}

	private void initData() {
		ArrayList<TagCount> arrayList = new ArrayList<TagCount>();
		TagCount tagCount1 = new TagCount();
		tagCount1.setId("1");
		tagCount1.setTag(Arrays.asList("java", "C#"));
		tagCount1.setName("java编程思想1");
		tagCount1.setN(2);

		TagCount tagCount2 = new TagCount();
		tagCount2.setId("2");
		tagCount2.setTag(Arrays.asList("C#"));
		tagCount2.setName("C#编程思想1");
		tagCount2.setN(3);

		TagCount tagCount3 = new TagCount();
		tagCount3.setId("3");
		tagCount3.setName("java编程思想2");
		tagCount3.setTag(Arrays.asList("java", "PHP"));
		tagCount3.setN(5);

		TagCount tagCount4 = new TagCount();
		tagCount4.setId("4");
		tagCount4.setTag(Arrays.asList("C#"));
		tagCount4.setName("C#编程思想1");
		tagCount4.setN(6);

		TagCount tagCount5 = new TagCount();
		tagCount5.setId("5");
		tagCount5.setTag(Arrays.asList("java", "PHP"));
		tagCount5.setName("java编程思想3");
		tagCount5.setN(5);

		TagCount tagCount6 = new TagCount();
		tagCount6.setId("6");
		tagCount6.setTag(Arrays.asList("java"));
		tagCount6.setName("java编程思想4");
		tagCount6.setN(6);

		arrayList.add(tagCount1);
		arrayList.add(tagCount2);
		arrayList.add(tagCount3);
		arrayList.add(tagCount4);
		arrayList.add(tagCount5);
		arrayList.add(tagCount6);

		tagCountDao.saveAll(arrayList);

	}

}
