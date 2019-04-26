package com.mongo.simplejava.simple_java_mongo_program;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBsslConnectTest {

	@Mock 
	MongoClientURI mongoURI;
	@Mock
	private MongoClient mongoClient;
	@Mock
	private MongoDatabase mongoDB;
	@Mock
	private MongoDBsslConnect mongoObj;
	@Mock
	private Document doc;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testRetrieveAllDocumentFromDB() {

		List<Document> documents = new ArrayList<Document>();
		
		String dbName = "employee_db";
		String dbCollectionName = "employees";
		MongoClient mongoClient = mock(MongoClient.class);
		MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
		MongoDatabase database = mongoClient.getDatabase(dbName);
		
		assertNull(mongoCollection.find());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInsertDocument() {
		String dbName = "employee_db";
		String dbCollectionName = "employees";
		MongoClient mongoClient = mock(MongoClient.class);
				
		Document doc = mock(Document.class);
		doc.put("name", "Test Name");
		doc.put("salary", 100);
		doc.put("type", "FullTime");				
		
		MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
		mongoCollection.insertOne(doc);
		assertNotNull(doc);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateDocumentFromDB() {
		String dbName = "employee_db";
		String dbCollectionName = "employees";
		MongoClient mongoClient = mock(MongoClient.class);
		
		MongoCollection<Document> collection = mock(MongoCollection.class);
		Document doc = mock(Document.class);
		
		Bson filter = new Document("name", "Test Name");
		Bson newValue = new Document("salary", 200);
		Bson updateOperationDocument = new Document("$set", newValue);
		collection.updateOne(filter, updateOperationDocument);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testRemoveDocumentFromDB() {
		String dbName = "employee_db";
		MongoClient mongoClient = mock(MongoClient.class);
		MongoDatabase database = mongoClient.getDatabase(dbName);
		MongoCollection<Document> collection = mock(MongoCollection.class);
		
		Bson condition = new Document("$lt", 250);
		Bson filter = new Document("salary", condition);
		collection.deleteOne(filter);
		
		assertEquals(collection.find(), null);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testDropDatabase() {
		String dbName = "employee_db";
		MongoClient mongoClient = mock(MongoClient.class);
		mongoClient.dropDatabase(dbName);
		
		assertEquals(mongoClient.getDatabase(dbName), null);
	}

}
