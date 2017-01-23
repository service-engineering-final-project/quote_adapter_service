package introsde.quotelicious.rest.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The JAVA class for the "quote" model.
 * 
 * @author alan
 *
 */


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement			// make it the root element

// The content order in the generated schema type
@XmlType(propOrder={"text","author"})

public class Quote implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/********************************************************************************
	 * DEFINITION OF ALL THE PRIVATE ATTRIBUTES OF THE CLASS						*
	 ********************************************************************************/
	
	@XmlElement private String text;
	@XmlElement private String author;
	
	
	/**
	 * A method that returns the text of the quote.
	 * @return text: the text of the quote
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * A method that returns the author of the quote.
	 * @return author: the author of the quote
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * A method that sets the text of the quote.
	 * @param text: the text of the quote
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * A method that returns the author of the quote.
	 * @param author: the author of the quote
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
}