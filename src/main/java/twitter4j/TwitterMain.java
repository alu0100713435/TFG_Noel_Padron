package twitter4j;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import twitter4j.TwitterController;
import twitter4j.QueryReader;

import twitter4j.Status;
import twitter4j.TwitterException;

import twitter4j.Freeling4;

import edu.upc.Jfreeling.*;

public class TwitterMain {
	
	private static PrintStream consolePrint;
	
	public static void main(String[] args) throws IOException, TwitterException {

		Freeling4.mostrar_freeling();

		/*try {
            // gets Twitter instance with default credentials
			Twitter twitter = new TwitterFactory().getInstance();
			TwitterController twittear = new TwitterController(consolePrint); //Objeto de la clase TwitterController
            User user = twitter.verifyCredentials();
            List<Status> statuses = twitter.getHomeTimeline();
            System.out.println("Showing @" + user.getScreenName() + "'s home timeline.");
            for (Status status : statuses) {
                //System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
			}
			twittear.printStatus(statuses); //Muestra todos los tweets que ha almacenado del TimeLine con el formato que tiene en la definicion de la funcion
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }*/

		
		/* TwitterController twittear = new TwitterController(consolePrint); //Objeto de la clase TwitterController
		String message = "Prueba Twitter4J 2.0"; //Texto del tweet
		long inReplyToStatusId; //Variable en la que ira el id del tweet a responder
		String command = QueryReader.readLine("Query in twitter (quit for exit)->>"); //Palabra a analizar
		while (!command.equals("quit")) {			
			List<Status> result = twittear.query(command); //Funcion de la clase TwitterController para almacenar los tweets que coincidan
			for (Status tweet : result) {				
				inReplyToStatusId = tweet.getId();
				message = message + " @" + tweet.getUser().getScreenName();
				System.out.println(tweet.getUser().getScreenName());
				//StatusUpdate stat= new StatusUpdate(message);
				//stat.setInReplyToStatusId(inReplyToStatusId);
				//twittear.reply(stat);
			}
			twittear.printStatus(result);
			//twittear.tweetOut(message);
			command = QueryReader.readLine("Query in twitter: ");
		} */
		
	}

}
