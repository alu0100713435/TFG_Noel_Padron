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
    public static MongoCollection<Document> base_datos(boolean insertar) {
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
        if(insertar == true){
            Document doc = new Document("name", "proservic")
                                .append("keywords", new Document("keyword1", "servicios")
                                                        .append("keyword2", "sociales"))
                                .append("description", "Echale un vistazo a esta noticia! ")
                                .append("link", "http://www.n2bsolutions.com/es/blog/01/");
            collection.insertOne(doc);
        }

        return collection;
        // collection.find(new Document()).forEach(printBlock);
    }
    
}