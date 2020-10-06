package twitter4j;

import static org.junit.Assert.*;

import java.io.IOException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.junit.Test;

import edu.upc.Jfreeling.Document;

public class TwitterMainTest {

	@Test
	public void test_mongo() throws IOException {

		MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
        MongoClient mongoClient = new MongoClient(connectionString);
        MongoDatabase database = mongoClient.getDatabase("twitter");
		MongoCollection<org.bson.Document> collection = database.getCollection("newCollection1");
		String prueba_mongo = TwitterMain.consulta_base_datos("gato", collection);
		assertEquals("Echale un vistazo a esta noticia! https://okdiario.com/mascotas/creo-que-gato-no-quiere-que-puedo-hacer-6069713", prueba_mongo);
	}

	@Test
	public void test_freeling() throws IOException {

		/* String prueba1 = TwitterMain.analisis_freeling("tenerife");
		String prueba2 = "tenerife";
		assertEquals(prueba1, prueba2); */
	}

}