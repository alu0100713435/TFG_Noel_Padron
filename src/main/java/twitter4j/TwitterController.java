package twitter4j;

import twitter4j.GeoLocation;       // jar found at http://twitter4j.org/en/index.html
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterException;

import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.List;


import java.text.SimpleDateFormat;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.MongoDB;

public class TwitterController {
	private Twitter twitter;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private PrintStream consolePrint;
    private List<Status> statuses;

	public TwitterController(PrintStream console) {
		//MongoDB.mostrar();
		twitter = TwitterFactory.getSingleton();
		consolePrint = console;
        statuses = new ArrayList<Status>();
	}

	public List<Status> query(String query) throws TwitterException {
		QueryResult search = twitter.search(new Query(query));
		List<Status> tweets = search.getTweets();
		return tweets;
	}

	public void printStatus(Status status) {
		System.out.println("----------------------------------------------------------");
		System.out.println(String.format("User [%s]", status.getUser().getScreenName()));
		System.out.println(status.getText());
		System.out.println("Id tweet: " + status.getId());
		System.out.println(sdf.format(status.getCreatedAt()));
		System.out.println(String.format("RT[%d] FAV[%d]", status.getRetweetCount(), status.getFavoriteCount()));
		System.out.println("----------------------------------------------------------");
	}
	
	public void printStatus(List<Status> status) {
		int i=1;
		for (Status tweet : status) {
			System.out.println("Tweet Numero: " + i);
			i++;
			printStatus(tweet);
		}
	}
	
	public void tweetOut(String message) throws TwitterException, IOException
    {
		Status status = twitter.updateStatus(message);
		System.out.println("Tweet enviado con exito con el texto: " + status.getText());
    }
	
	public void reply(StatusUpdate status) throws TwitterException {
		twitter.updateStatus(status);
	}

}