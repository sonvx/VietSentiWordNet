package vnu.uet.vietsentiwordnet.dictionary;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import vnu.uet.vietsentiwordnet.utils.Ultils;


public class ReScore {
	
	public static void main(String []args) {
		Ultils ul = new Ultils();
		ReScore rs = new ReScore();
		ArrayList<SentimentWord> myListSentiWord = new ArrayList<SentimentWord>();
		try {
			String txt = ul.readFile2("E:/workspace/OpinionMining/AllSenteceOM.txt");
			myListSentiWord = rs.reScore(rs.mySentimenWord(txt));
			for(int i = 0; i < myListSentiWord.size(); i++) {
				//System.out.println(myListSentiWord.get(i).sentiWord + "---"+ myListSentiWord.get(i).posScore + "---" + myListSentiWord.get(i).negScore);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<SentimentWord> reScore(ArrayList<SentimentWord> myListSentiWord ) {
		ArrayList<SentimentWord> myListSentiWordResult = new ArrayList<SentimentWord>();
		
		myListSentiWord.add(new SentimentWord("tốt", "Pos", 0, 0));
		myListSentiWord.add(new SentimentWord("tốt", "Pos", 0, 0));
		myListSentiWord.add(new SentimentWord("tốt", "Pos", 0, 0));
		myListSentiWord.add(new SentimentWord("tốt", "Neg", 0, 0));
		myListSentiWord.add(new SentimentWord("tốt", "Neg", 0, 0));
		myListSentiWord.add(new SentimentWord("tốt", "Neu", 0, 0));		
		
		int size = myListSentiWord.size();
		int []sum = new int[6];
		

		for (int i = 0; i < size; i++) {
			String temp = myListSentiWord.get(i).sentiWord;
			String typeTemp = myListSentiWord.get(i).type;
			for(int k = 0; k!= i && k < size; k++) {
				if(temp.equals(myListSentiWord.get(k).sentiWord)) {
					sum[i]++;
				}
			}
			
		}
		for (int i = 0; i < sum.length; i++)
			System.out.println(sum[i]);
		return myListSentiWordResult;
	}
	
	public String stdWord(String txt) {
		String res = "";
		txt = txt.trim();
		txt = txt.replace("[", "");
		txt = txt.replace("]", "");
		txt = txt.replace("/", "");
		txt = txt.replace("{Pos}", "");
		txt = txt.replace("{Neg}", "");
		txt = txt.replace("{Neu}", "");
		res = txt;
		return res;
	}
	
	public ArrayList<SentimentWord> mySentimenWord(String text) {
		ArrayList<SentimentWord> myListSentiWord = new ArrayList<SentimentWord>();
		ArrayList<String> listWord = new ArrayList<String>();
		listWord = getSentimentWord(text);
		for(int i = 0; i < listWord.size(); i++) {
			if(listWord.get(i).endsWith("{Pos}")) {
				myListSentiWord.add(new SentimentWord(stdWord(listWord.get(i)), "Pos", 0, 0));
			} else if (listWord.get(i).endsWith("{Neg}")) {
				myListSentiWord.add(new SentimentWord(stdWord(listWord.get(i)), "Neg", 0, 0));
			} else {
				myListSentiWord.add(new SentimentWord(stdWord(listWord.get(i)), "Neu", 0, 0));
			}
		}
		return myListSentiWord;
	}
	
	public ArrayList<String> getSentimentWord(String text) {
		ArrayList<String> matchList = new ArrayList<String>();
		try {
			Pattern regex = Pattern.compile("(\\[.+?\\})", Pattern.DOTALL);
			Matcher regexMatcher = regex.matcher(text);
			while (regexMatcher.find()) {
				matchList.add(regexMatcher.group());
			} 
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		return matchList;
	}
}


class SentimentWord {
	String type;
	String sentiWord;
	double posScore;
	double negScore;
	SentimentWord (){
		this.sentiWord = "";
		this.posScore = 0;
		this.negScore = 0;
	}
	SentimentWord (String word, String type_, double posScore_, double negScore_) {
		this.sentiWord = word;
		this.type = type_;
		this.posScore = posScore_;
		this.negScore = negScore_;
	}
}