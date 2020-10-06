package twitter4j;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.junit.Test;

import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.FindIterable;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import static org.junit.Assert.assertEquals;

import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;

import twitter4j.TwitterController;
import twitter4j.QueryReader;

import twitter4j.Status;
import twitter4j.TwitterException;

import twitter4j.Freeling4;

import edu.upc.Jfreeling.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class TwitterMain {
	
	private static PrintStream consolePrint;
	
	public static void main(String[] args) throws IOException, TwitterException {

		// MongoDB.base_datos();
		
		System.out.println("==================================================");
		System.out.println("Conexión a la base de datos: ");
		MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
        MongoClient mongoClient = new MongoClient(connectionString);
		
        MongoDatabase database = mongoClient.getDatabase("twitter");
		MongoCollection<Document> collection = database.getCollection("newCollection1");
		
		String contenido = ""; // El texto de cada Tweet
		String response_bd = ""; // La respuesta de la base de datos
		String message = ""; // El contenido del tweet de la respuesta
		ArrayList<String> response_freeling = new ArrayList<String>(); // Lista de palabras de freeling
		long inReplyToStatusId; // ID del tweet a responder

		try {
			// gets Twitter instance with default credentials
			Twitter twitter = new TwitterFactory().getInstance();
			TwitterController twittear = new TwitterController(consolePrint); //Objeto de la clase TwitterController
            User user = twitter.verifyCredentials();
            List<Status> statuses = twitter.getHomeTimeline();
            for (Status status : statuses) {
				System.out.println("==================================================");
				System.out.println("Tweet: ");
				twittear.printStatus(status);
				System.out.println("==================================================");
				contenido = status.getText();
				response_freeling = analisis_freeling(contenido);
				for(String palabra : response_freeling){
					if(palabra != ""){
						response_bd = consulta_base_datos(palabra, collection);
						if(response_bd != ""){
							message = response_bd + " @" + status.getUser().getScreenName();
							System.out.println("==================================================");
							System.out.println("Respuesta al tweet: ");
							System.out.println(message);
							System.out.println("==================================================");
							inReplyToStatusId = status.getId();
							
							StatusUpdate stat= new StatusUpdate(message);
							stat.setInReplyToStatusId(inReplyToStatusId);
							twittear.reply(stat);
						}
						else{
							System.out.println("No hay coincidencias con la palabra: '" + palabra + "' en la base de datos.");
						}
					}
				}
				System.out.println("==================================================");
			}
			//twittear.printStatus(statuses); //Muestra todos los tweets que ha almacenado del TimeLine con el formato que tiene en la definicion de la funcion
        } catch (TwitterException te) {
			te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
		}
		
		// prueba_twitter();
		
	}

	public static ArrayList<String> analisis_freeling(String contenido) throws IOException{
		String ruta = "analizar.txt";
		File file = new File(ruta);
		// Si el archivo no existe es creado
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(contenido);
		bw.close();
			
		Process proc = Runtime.getRuntime().exec("./freeling.sh");
		
		BufferedReader reader =  
		new BufferedReader(new InputStreamReader(proc.getInputStream()));
		
		String line = "";
		ArrayList<String> analisis = new ArrayList<String>();
		while((line = reader.readLine()) != null) {
			analisis.add(line);
		}
		ArrayList<String> palabras = new ArrayList<String>();

		for(int i=0 ; i<analisis.size(); i++){
			String[] parts = analisis.get(i).split(" ");
			for(int j=0; j<parts.length-1; j++){
				if(parts[j].charAt(0) == 'N' && (j-1) > 0){
					if((i == 0) && parts[j-1].equals("rt")){
						System.out.println("No se puede analizar un tweet de un retweet.");
						return palabras;
					}
					else{
						palabras.add(parts[j-1]);
						System.out.println("Palabra a analizar: '" + parts[j-1] + "'.");
					}
				}
			}
		}
		analisis.remove((analisis.size()-1));
		return palabras;
	}

	public static String consulta_base_datos(String name, MongoCollection<Document> collection){

		FindIterable<Document> resultDocument = collection.find(new Document("name", name));
		String respuesta = "";
		if (resultDocument.first() != null){
			respuesta = resultDocument.first().getString("description") + resultDocument.first().getString("link");
		}
		return respuesta;
	}

	public static void prueba_twitter() throws TwitterException, IOException {
		TwitterController twittear = new TwitterController(consolePrint); //Objeto de la clase TwitterController
		String message = "Prueba"; //Texto del tweet
		long inReplyToStatusId; //Variable en la que ira el id del tweet a responder
		String command = QueryReader.readLine("Query in twitter (quit for exit)->>"); //Palabra a analizar
		while (!command.equals("quit")) {			
			List<Status> result = twittear.query(command); //Funcion de la clase TwitterController para almacenar los tweets que coincidan
			for (Status tweet : result) {				
				inReplyToStatusId = tweet.getId();
				message = message + " @" + tweet.getUser().getScreenName();
				System.out.println(tweet.getUser().getScreenName());
				StatusUpdate stat= new StatusUpdate(message);
				stat.setInReplyToStatusId(inReplyToStatusId);
				twittear.reply(stat);
			}
			//twittear.printStatus(result);
			//twittear.tweetOut(message);
			command = QueryReader.readLine("Query in twitter: ");
		}
	}

}
