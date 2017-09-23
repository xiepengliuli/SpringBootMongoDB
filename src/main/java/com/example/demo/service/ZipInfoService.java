package com.example.demo.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.example.demo.bean.mongo.InventoryItem;
import com.example.demo.bean.mongo.Product;
import com.example.demo.bean.mongo.StateStats;
import com.example.demo.bean.mongo.ZipInfo;
import com.example.demo.bean.mongo.ZipInfoStats;
import com.example.demo.bean.pojo.InventoryItemProjection;
import com.example.demo.dao.mongo.InventoryItemDao;
import com.example.demo.dao.mongo.ProductDao;
import com.example.demo.dao.mongo.ZipInfoDao;

@Service
public class ZipInfoService {

	@Autowired
	private ZipInfoDao zipInfoDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private MongoOperations mongoOperations;
	@Autowired
	private InventoryItemDao inventoryItemDao;

	public Object init() {
		initProduct();
		initZipInfoStats();
		initInventoryItem();
		return "初始化OK_data1/data2/data3";
	}

	public Object data1() {
		return zipInfoDao.findAll();
	}

	public Object data2() {
		return productDao.findAll();
	}

	public Object data3() {
		return inventoryItemDao.findAll();
	}

	public Object aggregation1() {
		return zipInfoDao.findAll();
	}

	public Object aggregation2() {

		TypedAggregation<ZipInfo> aggregation = newAggregation(ZipInfo.class,
				group("state", "city")//
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
		// 下面的更通用些,只不过上边是用的对象的思想
		// AggregationResults<ZipInfoStats> result =
		// mongoOperations.aggregate(aggregation, ZipInfoStats.class);
		ZipInfoStats firstZipInfoStats = result.getMappedResults().get(0);

		return result.getMappedResults();
	}

	public Object aggregation3() {

		TypedAggregation<ZipInfo> agg = newAggregation(ZipInfo.class, group("state").sum("population").as("totalPop"),
				sort(Direction.ASC, previousOperation(), "totalPop"), match(Criteria.where("totalPop").gte(100)));

		AggregationResults<StateStats> result = mongoOperations.aggregate(agg, StateStats.class);
		// List<StateStats> stateStatsList = result.getMappedResults();
		// AggregationResults<Map> result = mongoOperations.aggregate(agg,
		// Map.class);
		// List<Map> stateStatsList = result.getMappedResults();

		return result.getMappedResults();
	}

	public Object aggregation4() {
		TypedAggregation<Product> agg = newAggregation(Product.class, //
				project("name", "netPrice")//
						.and("netPrice").plus(1).as("netPricePlus1")//
						.and("netPrice").minus(1).as("netPriceMinus1")//
						.and("netPrice").multiply(1.19).as("grossPrice")//
						.and("netPrice").divide(2).as("netPriceDiv2")//
						.and("spaceUnits").mod(2).as("spaceUnitsMod2")//
		);

		// document是Map,直接用map表示也行
		AggregationResults<Document> result = mongoOperations.aggregate(agg, Document.class);
		List<Document> resultList = result.getMappedResults();

		return resultList;
	}

	public Object aggregation5() {
		TypedAggregation<Product> agg = newAggregation(Product.class, //
				project("name", "netPrice")//
						.andExpression("netPrice + 1").as("netPricePlus1")//
						.andExpression("netPrice - 1").as("netPriceMinus1")//
						.andExpression("netPrice / 2").as("netPriceDiv2")//
						.andExpression("netPrice * 1.19").as("grossPrice")//
						.andExpression("spaceUnits % 2").as("spaceUnitsMod2")//
						.andExpression("(netPrice * 0.8  + 1.2) * 1.19").as("grossPriceIncludingDiscountAndCharge")//
		);

		AggregationResults<Document> result = mongoOperations.aggregate(agg, Document.class);
		List<Document> resultList = result.getMappedResults();

		return resultList;
	}

	public Object aggregation6() {

		double shippingCosts = 1.2;

		TypedAggregation<Product> agg = newAggregation(Product.class, project("name", "netPrice")//
				// 屬性的复杂计算【(1-spaceUnits)中的spaceUnits是表中的属性】
				.andExpression("(netPrice * (1-spaceUnits)  + [0]) * (1+spaceUnits)", shippingCosts).as("salesPrice")//
		);

		AggregationResults<Document> result = mongoOperations.aggregate(agg, Document.class);
		List<Document> resultList = result.getMappedResults();

		return resultList;
	}

	public Object aggregation7() {

		TypedAggregation<InventoryItem> agg = newAggregation(InventoryItem.class,
				project("item").and("discount")// 只投影两个字段
						.applyCondition(ConditionalOperators.Cond.newBuilder().when(Criteria.where("qty").gte(12))//
								.then(30)//
								.otherwise(20))//
						// .and(ifNull("description",
						// "Unspecified")).as("description")//
						.and(ConditionalOperators.ifNull("$description").then("id")));

		AggregationResults<InventoryItemProjection> result = mongoOperations.aggregate(agg, "inventoryItem",
				InventoryItemProjection.class);
		List<InventoryItemProjection> stateStatsList = result.getMappedResults();

		return stateStatsList;                                                         
	}

	/**
	 * 初始化库存商品信息
	 */
	private void initInventoryItem() {

		inventoryItemDao.deleteAll();

		ArrayList<InventoryItem> arrayList = new ArrayList<InventoryItem>();

		for (int i = 0; i < 5; i++) {
			InventoryItem inventoryItem = new InventoryItem();
			inventoryItem.setId(i + 1);// 如果主键是int类型，则需要手动维护主键，否则会报错
			inventoryItem.setItem("苹果" + (i + 1));
			if (i == 0) {
				inventoryItem.setDescription(null);
			}else if (i == 1) {
				inventoryItem.setDescription("");
			}else{
				inventoryItem.setDescription("苹果" + (i + 1)+"苹果" + (i + 1)+"苹果" + (i + 1)+"苹果" + (i + 1));
			}
			inventoryItem.setQty((i + 1) * new Random().nextInt(20));
			arrayList.add(inventoryItem);
		}

		inventoryItemDao.saveAll(arrayList);
	}

	private void initProduct() {
		productDao.deleteAll();
		ArrayList<Product> arrayList = new ArrayList<Product>();
		for (int i = 0; i < 5; i++) {
			Product product = new Product();
			product.setName("小米Node" + (i + 1));
			product.setNetPrice(3600 * (new Random().nextDouble()));
			product.setSpaceUnits(new Random().nextInt(100));
			arrayList.add(product);
		}
		productDao.saveAll(arrayList);
	}

	private void initZipInfoStats() {

		zipInfoDao.deleteAll();

		ArrayList<ZipInfo> arrayList = new ArrayList<ZipInfo>();
		for (int i = 0; i < 5; i++) {
			int tempint = i + 1;
			ZipInfo zipInfo = new ZipInfo();
			zipInfo.setCity("北京" + tempint);
			zipInfo.setLocation(new double[] { 20 + tempint, 30 + tempint });
			zipInfo.setPopulation(new Random().nextInt(1000));
			zipInfo.setState("一线城市");
			arrayList.add(zipInfo);
		}
		for (int i = 0; i < 2; i++) {
			int tempint = i + 1;
			ZipInfo zipInfo = new ZipInfo();
			zipInfo.setCity("北京" + tempint);
			zipInfo.setLocation(new double[] { 20 + tempint, 30 + tempint });
			zipInfo.setPopulation(new Random().nextInt(1000));
			zipInfo.setState("一线城市");
			arrayList.add(zipInfo);
		}
		for (int i = 0; i < 5; i++) {
			int tempint = i + 1;
			ZipInfo zipInfo = new ZipInfo();
			zipInfo.setCity("山东" + tempint);
			zipInfo.setLocation(new double[] { 20 + tempint, 30 + tempint });
			zipInfo.setPopulation(new Random().nextInt(100));
			zipInfo.setState("二线城市");
			arrayList.add(zipInfo);

		}
		zipInfoDao.saveAll(arrayList);
	}

}
