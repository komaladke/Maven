package com.mongo.simplejava.simple_java_mongo_program;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBsslConnect {

	public static void main(String[] args) {

		MongoDBsslConnect mongoObj = new MongoDBsslConnect();
				
		MongoClient mongoClient = mongoObj.getConnection();
				
		//Retrieve Documents from Mongodb
		String dbName = "employee_db";
		String dbCollectionName = "employees";
		System.out.println("List of all documents from databse : "+dbName);
		mongoObj.retrieveAllDocumentFromDB(mongoClient, dbName, dbCollectionName);
		
		// Insert Document in Database
		System.out.println("Insert document into databse : "+dbName);
		mongoObj.insertDocument(mongoClient, dbName, dbCollectionName);
		
		//Retrieve Documents from Mongodb
		System.out.println("List of all documents from databse after insertion : "+dbName);
		mongoObj.retrieveAllDocumentFromDB(mongoClient, dbName, dbCollectionName);
		
		//Update Documents from Mongodb
		System.out.println("Update document froms databse : "+dbName);
		Bson filter = new Document("name", "Test Name");
		mongoObj.updateDocumentFromDB(mongoClient, dbName, dbCollectionName, filter);
		
		//Retrieve Documents from Mongodb
		System.out.println("List of all documents from databse after updates : "+dbName);
		mongoObj.retrieveAllDocumentFromDB(mongoClient, dbName, dbCollectionName);
		
		//Remove Document In Mongodb
		System.out.println("Remove document from databse : "+dbName);
		mongoObj.removeDocumentFromDB(mongoClient, dbName, dbCollectionName);
		
		//Retrieve Documents from Mongodb
		System.out.println("List of all documents from databse after remove : "+dbName);
		mongoObj.retrieveAllDocumentFromDB(mongoClient, dbName, dbCollectionName);
		
		// Drop Database 
		System.out.println("Drop databse : "+dbName);
		mongoObj.dropDatabase(mongoClient, dbName);
		
		mongoClient.close();
	}
	
	public MongoClient getConnection() {
		System.setProperty ("javax.net.ssl.keyStore","C:\\Users\\A651550\\eclipse-workspace\\simple-java-mongo-program\\src\\main\\resources\\MongoClientKeyCert.jks");
		System.setProperty ("javax.net.ssl.keyStorePassword","password");
		
		MongoClientOptions.Builder builder = MongoClientOptions.builder();
		builder.sslEnabled(true).build();
		builder.sslInvalidHostNameAllowed(true).build();	
		
		MongoClientURI mongoURI = new MongoClientURI("mongodb://mdsp-core-mgdb-vm1:24893,mdsp-core-mgdb-vm2:24893,mdsp-core-mgdb-vm3:24893/mydb?replicaSet=repset1&ssl=true&sslInvalidHostnamesAllowed=true&socketTimeoutMS=60000&maxPoolSize=10&authMechanism=MONGODB-X509&authSource=$external&ext.ssl.certFile=C:\\\\mongo\\\\superadm.pem&ext.ssl.rootCA=C:\\\\mongo\\\\mongoCA.crt&appName=simpleJavaApp");
		MongoClient mongoClient = new MongoClient(mongoURI);
		
		return mongoClient;
	}

	public void retrieveAllDocumentFromDB(MongoClient mongoClient, String dbName, String dbCollectionName) {
		MongoDatabase database = mongoClient.getDatabase(dbName);
		MongoCollection<Document> collection = database.getCollection(dbCollectionName);

		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());
		for(Document document : documents){
			System.out.println(document);
        }
	}
	
	public void insertDocument(MongoClient mongoClient, String dbName, String dbCollectionName) {
		MongoDatabase database = mongoClient.getDatabase(dbName);
		MongoCollection<Document> collection = database.getCollection(dbCollectionName);
 
		Document doc = new Document();
		doc.put("name", "Test Name");
		doc.put("salary", 100);
		doc.put("type", "FullTime");
 
		Document address = new Document();
		address.put("area", "Camp");
		address.put("city", "Pune");
 
		doc.put("address", address);
 
		collection.insertOne(doc);
	}
	
	public void updateDocumentFromDB(MongoClient mongoClient, String dbName, String dbCollectionName, Bson filter) {
		MongoDatabase database = mongoClient.getDatabase(dbName);
		MongoCollection<Document> collection = database.getCollection(dbCollectionName);
		
		Bson newValue = new Document("salary", 200);
		Bson updateOperationDocument = new Document("$set", newValue);
		collection.updateOne(filter, updateOperationDocument);
	}
	
	public void removeDocumentFromDB(MongoClient mongoClient, String dbName, String dbCollectionName) {
		MongoDatabase database = mongoClient.getDatabase(dbName);
		MongoCollection<Document> collection = database.getCollection(dbCollectionName);
		
		Bson condition = new Document("$lt", 250);
		Bson filter = new Document("salary", condition);
		collection.deleteOne(filter);
	}

	public void dropDatabase(MongoClient mongoClient, String dbName) {
		mongoClient.dropDatabase(dbName);
	}
}