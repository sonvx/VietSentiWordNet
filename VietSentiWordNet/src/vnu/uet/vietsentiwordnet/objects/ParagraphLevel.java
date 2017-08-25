package vnu.uet.vietsentiwordnet.objects;

import java.io.IOException;

import vnu.uet.vietsentiwordnet.data.DataObject;
import jvntextpro.JVnTextPro;

public class ParagraphLevel {

	
	ParagraphResultObj getScorePragraph(SentenceLevel senlevel, JVnTextPro vnTextPro, DataObject dto) {
		ParagraphResultObj po = new ParagraphResultObj();
		ResultObject resultSent = new ResultObject();
				
		String comment = dto.getComment();
		String []lines = comment.split("<br>");
		
		double paraScore = 0;
		String sentimentSentence = "";
		String paraResutString = "";
		
		
		int lineNumber = 0;
		
		
		for(String line: lines) {
			try {
				line = std(line);
				if (line.length() > 10) {
					lineNumber++;
					double scoreTmp = 0;
					//System.out.println(lineNumber + "---" + line);
					resultSent = senlevel.getScore(vnTextPro, line);
					scoreTmp = resultSent.score;
					paraScore += scoreTmp;
					if(scoreTmp != 0)
						sentimentSentence += line + resultSent.simpletResult + "<br>";
					//paraResutString += resultSent.resultString + "<br>";
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(lineNumber > 0)
			po.score = paraScore/lineNumber;
		
		po.title_cm = dto.getTitleComment();
		po.resultString = paraResutString;
		po.holder = dto.getHolder();
		po.sentimentSentence = sentimentSentence;
		//System.out.println(po.title_cm + "\n" + po.score + "\n" + po.sentimentSentence.replaceAll("<br>", "\n") + "\n" + po.holder);
		return po;
	}
	
	String std(String input) {
		input = input.trim();
		return input.replaceAll("_", " ");
	}
	
}

class ParagraphResultObj {
	double score;
	String title_cm;
	String sentimentSentence;
	String resultString;
	String holder;
	ParagraphResultObj(){
		//do nothing
	}
	ParagraphResultObj(double score_, String title_cm_, String sentenceCm_, String resultString_, String holder_) {
		this.score = score_;
		this.title_cm = title_cm_;
		this.sentimentSentence = sentenceCm_;
		this.resultString = resultString_;
		this.holder = holder_;
	}
}