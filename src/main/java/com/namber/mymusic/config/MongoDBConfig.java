package com.namber.mymusic.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDBConfig {
    @Value("${mongo.connectionString}")
    String clientUrl;

    @Value("${mongo.db}")
    String mymDb;

    @Value("${mongo.songCollection}")
    String songCollection;

    @Value("${mongo.userPrefCollection}")
    String userPrefCollection;

    @Bean
    public MongoClient getMongoClient() {
        MongoClientURI clientURI = new MongoClientURI(clientUrl);

        return new MongoClient(clientURI);
    }

    public MongoCollection<Document> getMongoCollection(String collection){
        return getMongoClient().getDatabase(mymDb).getCollection(collection);
    }

    @Bean("songCollection")
    public MongoCollection<Document> getSongCollection(){
        return getMongoCollection(songCollection);
    }

    @Bean("userPrefCollection")
    public MongoCollection<Document> getUserPrefCollection(){
        return getMongoCollection(userPrefCollection);
    }

}
