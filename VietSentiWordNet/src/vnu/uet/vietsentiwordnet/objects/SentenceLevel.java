package vnu.uet.vietsentiwordnet.objects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;





import vnu.uet.vietsentiwordnet.dictionary.Synset;
import vnu.uet.vietsentiwordnet.utils.Constants;
import vnu.uet.vietsentiwordnet.utils.Ultils;
import jvntextpro.JVnTextPro;

public class SentenceLevel {
	static ArrayList<Synset> mySynset;
	public static JVnTextPro jvn;
	
	public static void main (String []args) throws IOException {
		Ultils ul = new Ultils();
		jvn = ul.loadModels();
		SentenceLevel sl = new SentenceLevel();
		sl.loadSenLevel(jvn);
        System.out.println(sl.getScore(jvn,"cô gái rất xinh đẹp").simpletResult);
	}
	
	public ResultObject doSenLevel(String s) throws IOException {
		ResultObject res = new ResultObject();
		res = getScore(jvn, s);
		return res;
	}
	
	public void loadSenLevel(JVnTextPro jvn_) throws IOException {
		jvn = jvn_;
		mySynset = new ArrayList<Synset>();
		SentenceLevel sl = new SentenceLevel();
		mySynset = sl.getSynset(new File(Constants.VSWN_DICT));
	}
	
	public ResultObject getScore(JVnTextPro vnTextPro, String s) throws IOException {
		double result = 0;
		Ultils ul = new Ultils();
		ArrayList<String> myWord = new ArrayList<String>();
		ArrayList<String> recordWord = new ArrayList<String>();
		// initWrd();
		//String text = vnTextPro.wordSegment(s);
		String text = ul.ultilsJvn(vnTextPro, s);
		// System.out.println(text);
		StringTokenizer st = new StringTokenizer(text);
		while (st.hasMoreTokens()) {
			myWord.add(st.nextToken());
		}
		int i = 0;
		// for(int i = 0;i<myWord.size();i++)
		while (i < myWord.size()) {
			for (int j = 0; j < mySynset.size(); j++) {
				if (myWord.get(i).equals(mySynset.get(j).term)) {
					double score = mySynset.get(j).getScore();
					recordWord.add(myWord.get(i));
					if (i > 0 && (isNeg(myWord.get(i - 1))))
						score = -score;
					if (i > 0 && SacThai(myWord.get(i - 1)) != 0)
						score = score * SacThai(myWord.get(i - 1));
					if (i < myWord.size() - 1
							&& SacThai(myWord.get(i + 1)) != 0)
						score = score * SacThai(myWord.get(i + 1));
					result += score;
				} else if (i < myWord.size() - 1) {
					String str = myWord.get(i) + " " + myWord.get(i + 1);
					if (str.equals(mySynset.get(j).term)) {
						recordWord.add(str);
						double score = mySynset.get(j).getScore();
						if (i > 0 && (isNeg(myWord.get(i - 1))))
							score = -score;
						if (i > 0 && SacThai(myWord.get(i - 1)) != 0)
							score = score * SacThai(myWord.get(i - 1));
						if (i < myWord.size() - 2
								&& SacThai(myWord.get(i + 2)) != 0)
							score = score * SacThai(myWord.get(i + 2));
						result += score;
						i++;
					}
				}
			}
			i++;
		}
		ResultObject res = new ResultObject();
		String tendency = "";
		String resultString = "";
		String simpleResult = "";
		if (result != 0) {
			if (recordWord.size() > 0)
				result = result / recordWord.size();
			if(result >= 0.25)
				tendency = "Quan điểm của bạn là cực kỳ thích <img src=\"smiles/verylike.gif\" width=\"18\" height=\"18\">";
			else if(result >= 0 && result < 0.25)
				tendency = "Quan điểm của bạn là thích <img src=\"smiles/like.gif\" width=\"18\" height=\"18\">";
			else if(result == 0)
				tendency = "Quan điểm của bạn là: không có quan điểm gì. Trung lập <img src=\"smiles/neu.gif\" width=\"18\" height=\"18\">";
			else if(result < 0 && result >= -0.25)
				tendency = "Quan điểm của bạn là không thích.<img src=\"smiles/sad.gif\" width=\"18\" height=\"18\">";
			else if(result < -0.25)
				tendency = "Quan điểm của bạn là rất không thích.<img src=\"smiles/versad.gif\" width=\"18\" height=\"18\">";
			
			
			// String resultString = s + "\t";
			
			for (int j = 0; j < recordWord.size(); j++)
				resultString += recordWord.get(j) + ",";
			int length = resultString.length();
			resultString = resultString.substring(0, length - 1);
			
			simpleResult = "SentimentWord: " + resultString + "-----SentencScore: " + result;
			resultString = "SentimentWord: " + resultString + "-----SentencScore: " + result + "-----Your tendency: " + tendency;
			
			// System.out.println(resultString);
			res = new ResultObject(result, simpleResult, resultString, recordWord.size());
		} else {
			res = new ResultObject(result, simpleResult, "Xin lỗi. Hệ thống chúng tôi chưa thể đoán được hướng quan điểm câu này <img src=\"smiles/sad.gif\" width=\"18\" height=\"18\">",recordWord.size());
		}
		return res;
	}
	
	public static double SacThai(String s) throws IOException{
        File f = new File(Constants.SACTHAI_DICT);
        FileInputStream inputStream = new FileInputStream(f);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf8");
        BufferedReader mybuffer = new BufferedReader(inputStreamReader);
        String temp;
        mybuffer.readLine();
        while((temp=mybuffer.readLine())!=null){
            StringTokenizer st = new StringTokenizer(temp);
            //System.out.println(st.nextToken());
            if(st.nextToken().equals(s)) return Double.parseDouble(st.nextToken());
        }
        return 0;
    }
	
	public static boolean isNeg(String s) throws IOException{
        File f = new File(Constants.NEGATIVE_DICT);
        FileInputStream inputStream = new FileInputStream(f);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf8");
        BufferedReader mybuffer = new BufferedReader(inputStreamReader);
        String temp;
        while((temp=mybuffer.readLine())!=null){
            if(temp.equals(s)) {
            	mybuffer.close();
            	return true;
            }
        }
        mybuffer.close();
        return false;
    }
	
	public ArrayList<vnu.uet.vietsentiwordnet.dictionary.Synset> getSynset(File f) throws IOException {
		ArrayList<vnu.uet.vietsentiwordnet.dictionary.Synset> mySynset = new ArrayList<vnu.uet.vietsentiwordnet.dictionary.Synset>();
		FileInputStream inputStream = new FileInputStream(f);
		InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream, "utf8");
		BufferedReader mybuffer = new BufferedReader(inputStreamReader);
		String temp;
		int line = 1;
		mybuffer.readLine();
		while ((temp = mybuffer.readLine()) != null) {
			line++;
			StringTokenizer st = new StringTokenizer(temp, "\t");
			st.nextToken();
			st.nextToken();

			double Pos = Double.parseDouble(st.nextToken());
			double Neg = Double.parseDouble(st.nextToken());

			String term = st.nextToken();
			char[] myChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
			for (int i = 0; i < 10; i++)
				term = term.replace(myChar[i], '#');
			StringTokenizer st2 = new StringTokenizer(term, "##");
			while (st2.hasMoreTokens()) {
				String temp2 = st2.nextToken();
				// System.out.println(temp2);
				Synset tempsynset = new Synset(normalize(temp2), Neg, Pos, line);
				mySynset.add(tempsynset);
			}
		}
		return mySynset;
	}
	public static String normalize(String s) {
		int i = 0;
		while (s.charAt(i) == ' ')
			i++;
		return s.substring(i);
	}
}
