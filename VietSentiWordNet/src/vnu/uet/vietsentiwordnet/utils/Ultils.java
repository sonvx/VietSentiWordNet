package vnu.uet.vietsentiwordnet.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jvntextpro.JVnTextPro;

public class Ultils {
	
	public JVnTextPro loadModels() {
		JVnTextPro vnTextPro = new JVnTextPro();
		vnTextPro.initSenSegmenter("models/jvnsensegmenter");
		vnTextPro.initSenTokenization();
		vnTextPro.initSegmenter("models/jvnsegmenter");
		//vnTextPro.initPosTagger("models/jvnpostag/maxent");
		return vnTextPro;
	}
	
	public String ultilsSentoken(JVnTextPro jvn, String input) {
		String output = input;
		output = jvn.convertor.convert(output);
		output = jvn.senSegment(output);
		output = jvn.senTokenize(output);
		return output;
	}
	
	public String ultilsJvn(JVnTextPro vnTextPro, String input) {
		String output = input;
		output = vnTextPro.convertor.convert(output);
		output = vnTextPro.senSegment(output);
		output = vnTextPro.senTokenize(output);
		output = vnTextPro.wordSegment(output);
		//output = vnTextPro.posTagging(output);
		return output;
	}
	
	
	//Feature: Standard font. Example: Convert 
	public String standardFont(String input) {
		Pattern regex = Pattern.compile("&\\#([\\d]*);",Pattern.CASE_INSENSITIVE|Pattern.UNICODE_CASE|Pattern.COMMENTS);
		Matcher regexMatcher = regex.matcher(input);
		while (regexMatcher.find()) {
			String tmp = regexMatcher.group();
			String w = tmp;
			int num = Integer.parseInt(w.replace("&#", "").replace(";", ""));
			String word = Character.toString((char) num);
			input = input.replace(tmp, word);
		}
		input = input.trim();
		
		return input;
	}
	
	
	// Feature: Write a String to file
	// Input: a string want to write into file.
	// Output: file output
	public void write(String inputString, String outputPath)
			throws IOException, FileNotFoundException {
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outputPath), "UTF-8"));
		output.write(inputString);
		output.close();
	}

	//Feature: get all file in folder and subfolder.
	//Input: String link to folder
	//Output: List name of all file in folder (include file in subfolder)
	public String[] readAllFileFolder(String folderPathName) {
		String listFile[] = null;
		File filenames = new File(folderPathName);
		if (!filenames.isDirectory()) {
			System.out.println(folderPathName);
			return listFile;
		}
		String filelists[] = filenames.list();
		for (int i = 0; i < filelists.length; i++) {
			readAllFileFolder(folderPathName + "/" + filelists[i]);
		}
		return listFile;
	}
	
	
	public void creatNewFolder(String folderName) {
		File f = new File(folderName);
		if (f.exists() == false) {
			f.mkdirs();
		}
	}
	
	// Feature: get name of all file in a folder
	// Input: the path of folder want to read
	// Output: list String[] name of file in folder
	public String[] readFolder(String folderPathName) {
		String directory[] = null;
		
		File name = new File(folderPathName);
		if (name.exists()) {
			if (name.isDirectory()) {
				directory = name.list();
				System.out.println("\n\nDirectory contents:\n");
			}
		}
		else {
			System.out.printf("%s %s", folderPathName, "does not exist.");
		}
		return directory;
	}
	
	// Feature: read file into String
	// Input: the path of file want to read
	// Output: String contain file content
	public String readFile2 (String pathFile) throws Exception, FileNotFoundException {
		String txt = new String();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(pathFile), "UTF-8"));
		String line;
		
		while ((line = reader.readLine()) != null){				
			txt += line + "\n";
		}
		return txt;
	}
	
	// Feature: read file into String
	// Input: the path of file want to read
	// Output: String contain file content
	public String readFile (String pathFile) throws Exception, FileNotFoundException {
		String txt = new String();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(pathFile), "UTF-8"));
		String line;
		
		while ((line = reader.readLine()) != null){				
			txt += line + "<br>";
		}
		return txt;
	}
	
	static String[] lowerCharMap = { "áàảãạ", "a", "âấầẩẫậ", "a", "ăắằẳẵặ",
			"a", "đ", "d", "éèẻẽẹ", "e", "êếềểễệ", "e", "íìỉĩị", "i", "óòỏõọ",
			"o", "ôốồổỗộ", "o", "ơớờởỡợ", "o", "úùủũụ", "u", "ưứừửữự", "u",
			"ýỳỷỹỵ", "y"

	};

	public static String removeNoise(String data) {
		String ret = data;

		for (int i = 0; i < lowerCharMap.length; i += 2) {
			ret = ret.replaceAll("[" + lowerCharMap[i] + "]",
					lowerCharMap[i + 1]);
			ret = ret.replaceAll("[" + lowerCharMap[i].toUpperCase() + "]",
					lowerCharMap[i + 1].toUpperCase());
		}

		return ret;
	}
}
