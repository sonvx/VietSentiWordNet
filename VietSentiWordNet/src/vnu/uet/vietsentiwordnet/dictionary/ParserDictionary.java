package vnu.uet.vietsentiwordnet.dictionary;

import org.xml.sax.helpers.DefaultHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class ParserDictionary extends DefaultHandler {
	List myEntry;
	private String tempval;
	private Dictionary tempDict;
	
	public static void main (String []args) {
		ParserDictionary par = new ParserDictionary();
		par.runExample();
	}	
	
	public ParserDictionary(){
		myEntry = new ArrayList();
	}
	
	public void runExample() {
		parseDocument();
		printData();
	}
	
	
	private void parseDocument() {
		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
			sp.parse("F:/DU_LIEU/DICTIONARY/vcl_xml/a.xml", this);
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}
	
	private void printData(){
		
		System.out.println("No of Dictionay Entry '" + myEntry.size() + "'.");
		
		Iterator it = myEntry.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
	}
	
}
