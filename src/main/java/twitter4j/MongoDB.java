package twitter4j;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
public class MongoDB {
    public static MongoCollection<Document> base_datos() {
        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
        };
        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
        MongoClient mongoClient = new MongoClient(connectionString);

        MongoDatabase database = mongoClient.getDatabase("twitter");
        MongoCollection<Document> collection = database.getCollection("newCollection1");
        
        Document doc = new Document("name", "canarias")
                            .append("keywords", new Document("keyword1", "coronavirus")
                                                    .append("keyword2", "test"))
                            .append("description", "Echale un vistazo a esta noticia! ")
                            .append("link", "https://www.eldiario.es/canariasahora/sociedad/canarias-lanza-nuevos-test-rapidos-cuestionada-estrategia-cribados-masivos-detectar-covid-19_1_6242065.html");
        collection.insertOne(doc);
        

        return collection;
        // collection.find(new Document()).forEach(printBlock);
    }
    
}