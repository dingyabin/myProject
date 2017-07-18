package com.dingyabin.mongo;

import com.dingyabin.mongo.model.Movie;
import com.dingyabin.mongo.util.BeanUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Date;

/**
 * Created by MrDing
 * Date: 2017/5/6.
 * Time:3:03
 */
public class MongoTest {


    public static void main(String[] args) {

        MongoDatabase mongoDb = MangoFactory.getMongoDb("MyTest");

        for (String name : mongoDb.listCollectionNames()) {
            System.out.println(name);
        }


        MongoCollection<Document> movie = MangoFactory.getCollection("movie");
        Movie bean=new Movie("a","b",3,new Date());
        bean.setXxxxx("ssssssss");
        movie.insertOne(BeanUtil.bean2Document(bean));

//        MongoCollection<Movie> movie1 = MangoFactory.getCollection("movie", Movie.class);
//        movie1.insertOne(bean);

    }


}
