package introsde.quotelicious.rest.resources;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import introsde.quotelicious.rest.model.Quote;


/***
 * The resource class that implements our service endpoints for the Quote.
 * 
 * @author alan
 *
 */

// @Stateless
// @LocalBean
@Path("/quotelicious/")
public class QuoteResource {
	@Context UriInfo uriInfo;	// allows to insert contextual objects (uriInfo) into the class
	@Context Request request;	// allows to insert contextual objects (request) into the class
	
	// Definition of some useful constants
	final String baseUrl = "http://quotelicious.com/quotes/motivational-quotes/page/";
	final int minIndexPage = 1;
	final int maxIndexPage = 7;
	final int minIndexQuote = 1;
	final int maxIndexQuote = 10;
	
	public QuoteResource() {
		// Empty constructor
	}
	
	/***
	 * A method that returns the whole list of motivational quotes from Quotelicious
	 * @return a list of quotes
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Quote> getQuotesList() {
		String indexUrl = null;
		List<Quote> quotesList = new ArrayList<Quote>();
		
		System.out.println("Retrieved motivational quotes:");
		for (int i=minIndexPage; i<=maxIndexPage; i++) {
			indexUrl = baseUrl + i;
			try {
				String rawHtml = getHtmlCode(indexUrl);
				Document document = Jsoup.parse(rawHtml);
				Element element = document.getElementById("content-quotespage");
				Elements links = element.getElementsByTag("a");
				Quote quote = null;
				
				for (Element link : links) {
					if ((link.parent().className().equals("post odd"))||
							(link.parent().className().equals("post even"))) {
						quote = new Quote();
						quote.setText(link.text());
						quote.setAuthor(link.nextElementSibling().text().substring(2));
						quotesList.add(quote);
						System.out.println("\t" + link.text() + 
								" (" + link.nextElementSibling().text().substring(2) + ")");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return quotesList;
	}

	/***
	 * A method that returns a random motivational quote from Quotelicious
	 * @return a random quote
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/random")
	public Quote getQuote() {
		int randomPage = ThreadLocalRandom.current().nextInt(minIndexPage, maxIndexPage+1);
		int randomQuote = ThreadLocalRandom.current().nextInt(minIndexQuote, maxIndexQuote+1);
		String indexUrl = baseUrl + randomPage;
		int indexQuote = 1;
		Quote quote = null;
		
		System.out.println("Retrieved motivational quote:");
		try {
			String rawHtml = getHtmlCode(indexUrl);
			Document document = Jsoup.parse(rawHtml);
			Element element = document.getElementById("content-quotespage");
			Elements links = element.getElementsByTag("a");
			
			for (Element link : links) {
				if ((link.parent().className().equals("post odd"))||
						(link.parent().className().equals("post even"))) {
					if (indexQuote==randomQuote) {
						quote = new Quote();
						quote.setText(link.text());
						quote.setAuthor(link.nextElementSibling().text().substring(2));
						System.out.println("\t" + link.text() + 
								" (" + link.nextElementSibling().text().substring(2) + ")");
					}
					indexQuote++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return quote;
	}
	
	
	/********************************************************************************
	 * HELPER METHODS USEFUL FOR THE COMPUTATION OF OTHER METHODS IN THIS CLASS		*
	 ********************************************************************************/
	
	/***
	 * An accessory method that reads the HTML code and stores it into a string
	 * @param indexUrl: the page URL to parse
	 * @return a string containing the HTML code of the page
	 */
	public String getHtmlCode(String indexUrl) {
		URL url;
		BufferedReader reader;
		String line = "";
		String rawHtml = "";
		
		try {
			url = new URL(indexUrl);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
			while((line=reader.readLine())!=null) {
				rawHtml += line;
			}
			
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rawHtml;
	}
}