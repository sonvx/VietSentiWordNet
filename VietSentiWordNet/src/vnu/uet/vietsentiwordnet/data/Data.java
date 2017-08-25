package vnu.uet.vietsentiwordnet.data;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import vnu.uet.vietsentiwordnet.utils.Ultils;


public class Data {
	
	public static void main(String []args) {
		Data d = new Data();
		try {
			ArrayList<DataObject> dto = new ArrayList<DataObject>();
			dto = d.myDataObject("Data/test");
			for(int i = 0; i < dto.size(); i++) {
				System.out.println(dto.get(i).id);
				System.out.println(dto.get(i).title_news);
				System.out.println(dto.get(i).title_comment);
				System.out.println(dto.get(i).comment);
				System.out.println(dto.get(i).holder);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public ArrayList<DataObject> myDataObject(String inputFolder) throws FileNotFoundException, Exception {
		Ultils ul = new Ultils();
		ArrayList<DataObject> myData = new ArrayList<DataObject>();
		
		ArrayList<String> titles_cm = new ArrayList<String>();
		ArrayList<String> comment = new ArrayList<String>();
		ArrayList<String> holder = new ArrayList<String>();
		String [] files = ul.readFolder(inputFolder);
		int id = 0;
		for (String file : files) {
			String fileInput = inputFolder + "/" + file;
			String txt = ul.readFile(fileInput);
			String title_news = title(txt);
			String body_news = body(txt);
			titles_cm = title_cm(txt);
			comment = body_cm(txt);
			holder = holder(txt);
			int size = min(titles_cm.size(), comment.size(), holder.size());
			for (int i = 0; i < size; i++) {
				DataObject dto = new DataObject();
				dto.id = id;
				dto.setTitle(title_news);
				dto.setBodyNews(body_news);
				dto.setTitle_cm(titles_cm.get(i));
				dto.setComment(comment.get(i));
				dto.setHolder(holder.get(i));
				myData.add(dto);
				/*System.out.println(i + titles_cm.get(i));
				System.out.println(i + comment.get(i));
				System.out.println(i + holder.get(i));
				*/
			}
			id++;
		}
		return myData;
	}
	
	public int min(int a, int b, int c) {
		if (a <= b)
			return (a <= c ? a : c);
		else {

			if (a <= c)
				return (a <= b ? a : b);
			else
				return (b <= c ? b : c);
		}
	}
	
	String body(String input) {
		String ResultString = null;
		try {
			Pattern regex = Pattern.compile("<body_news>(.*?)</body_news>", Pattern.DOTALL);
			Matcher regexMatcher = regex.matcher(input);
			if (regexMatcher.find()) {
				ResultString = regexMatcher.group(1);
			} 
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		return ResultString;
	}
	
	String title(String input) {
		String ResultString = null;
		try {
			Pattern regex = Pattern.compile("<title>(.*?)</title>");
			Matcher regexMatcher = regex.matcher(input);
			if (regexMatcher.find()) {
				ResultString = regexMatcher.group(1);
			} 
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		return ResultString;
	}
	
	ArrayList<String> holder(String input) {
		ArrayList<String> matchList = new ArrayList<String>();
		try {
			Pattern regex = Pattern.compile("<holder>(.*?)</holder>", Pattern.DOTALL);
			Matcher regexMatcher = regex.matcher(input);
			while (regexMatcher.find()) {
				matchList.add(regexMatcher.group(1));
			} 
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		return matchList;
	}
	
	ArrayList<String> title_cm(String input) {
		ArrayList<String> matchList = new ArrayList<String>();
		try {
			Pattern regex = Pattern.compile("<title_cm>(.*?)</title_cm>", Pattern.DOTALL);
			Matcher regexMatcher = regex.matcher(input);
			while (regexMatcher.find()) {
				matchList.add(regexMatcher.group(1));
			} 
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		return matchList;
	}
	
	ArrayList<String> body_cm(String input) {
		ArrayList<String> matchList = new ArrayList<String>();

		try {
			Pattern regex = Pattern.compile("<body_cm>(.*?)</body_cm>",
					Pattern.DOTALL);
			Matcher regexMatcher = regex.matcher(input);
			while (regexMatcher.find()) {
				matchList.add(regexMatcher.group(1));
			}
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}

		return matchList;
	}
	public void reStData(String folderInput, String folderOutput) throws FileNotFoundException, Exception {
		Ultils ul = new Ultils();
		String files[] = ul.readFolder(folderInput);
		for(String file: files) {
			String fileTemp = folderInput + "/" + file;
			String txt = ul.readFile(fileTemp);
			//System.out.println(txt);
			if(txt.contains("<br>	</body_cm><br>	<body_cm><br>"))
				txt = txt.replaceAll("<br>	</body_cm><br>	<body_cm><br>", "<br>");
			txt = txt.replaceAll("<br>", "\n");
			ul.write(txt, folderOutput + "/" + file);
		}
	}
}
