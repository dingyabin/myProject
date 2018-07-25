package com.dingyabin.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by MrDing
 * Date: 2017/5/6.
 * Time:2:20
 */
public class MangoFactory {

    private static final String host = "192.168.199.182";

    private static final int port = 27017;

    private static MongoClient mongoClient = new MongoClient(host, port);


    public static MongoClient getMongoClient() {
        return mongoClient;
    }


    public static MongoDatabase getMongoDb(String dbName) {
        return mongoClient.getDatabase(dbName);
    }


    public static MongoCollection<Document> getCollection(String collectionName) {
        return getMongoDb("MyTest").getCollection(collectionName);
    }


    public static <T> MongoCollection<T> getCollection(String collectionName, Class<T> clazz) {
        return getMongoDb("MyTest").getCollection(collectionName, clazz);
    }


}
