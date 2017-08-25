package vnu.uet.vietsentiwordnet.data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import vnu.uet.vietsentiwordnet.utils.Ultils;

public class SentenceOpi {
	
	public static void main (String []args) throws FileNotFoundException, Exception {
		SentenceOpi so = new SentenceOpi();
		so.doGetSentece("F:/DU_LIEU/DICTIONARY/ReScore/All","F:/DU_LIEU/DICTIONARY/ReScore/AllSentece2.txt");
	}
	
	public void doGetSentece(String inputFolder, String outputFile) throws FileNotFoundException, Exception {
		Ultils ul = new Ultils();
		String []files = ul.readFolder(inputFolder);
		String result = "", neuSen = "", posSen = "", negSen = "";
		int pos = 0, neg = 0, neu = 0;
		for (String tmp : files) {
			System.out.println(tmp);
			String file = inputFolder + "/" + tmp;

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			String line;

			while ((line = reader.readLine()) != null) {
				if (line.endsWith("{Pos}")) {
					posSen += line + "\n";
					pos++;
				}
				if(line.endsWith("{Neg}")) {
					negSen += line + "\n";
					neg++;
				}
				if(line.endsWith("{Neu}")) {
					 neuSen += line + "\n";
					 neu++;
				}
			}
		}
		System.out.println("Pos: " + pos + "     Neg: " + neg + "    Neu:" + neu);
		//result += posSen + "\n" + negSen + "\n" + neuSen;
		
		//ul.write(result, outputFile);
	}
	
	public String getSentece(String text) {
		String ResultString = null;
		try {
			Pattern regex = Pattern.compile("(.+?\\./\\{[\\w]*\\})");
			Matcher regexMatcher = regex.matcher(text);
			if (regexMatcher.find()) {
				ResultString += regexMatcher.group(1) + "\n";
			} 
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		return ResultString;
	}
	
}
