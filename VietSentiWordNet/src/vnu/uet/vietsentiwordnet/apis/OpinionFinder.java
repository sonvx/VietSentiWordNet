package vnu.uet.vietsentiwordnet.apis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

import jvntextpro.JVnTextPro;
import vnu.uet.vietsentiwordnet.objects.ResultObject;
import vnu.uet.vietsentiwordnet.objects.SentenceLevel;
import vnu.uet.vietsentiwordnet.utils.Ultils;

public class OpinionFinder {
	private static OpinionFinder myInst;

	public static OpinionFinder getInstance() {
		if (myInst == null)
			myInst = new OpinionFinder();
		return myInst;
	}

	public SentenceLevel loadModels() {
		SentenceLevel sl = new SentenceLevel();
		Ultils ul = new Ultils();
		JVnTextPro jvn = new JVnTextPro();
		jvn = ul.loadModels();
		try {
			sl.loadSenLevel(jvn);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return sl;
	}

	public ResultObject findOpinion(String sentence) {
		SentenceLevel sl = loadModels();

		// do opinion mining at sentenceLevel
		ResultObject res = new ResultObject();
		try {
			res = sl.doSenLevel(sentence);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return res;
	}

	private static void test() {
		SentenceLevel sl = OpinionFinder.getInstance().loadModels();
		// do opinion mining at sentenceLevel
		ResultObject res = new ResultObject();
		while (true) {
			System.out.print("Enter a sentence:");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				String body = br.readLine();
				if (body == null)
					body = "Tôi yêu Hà Nội nhiều lắm";
				System.out.println(body);
				res = sl.doSenLevel(body);
				// get score
				double Score = res.getScore();
				// get sentiment string
				String Sentiment = res.getResultString();
				System.out.println("Score: " + Score + ", Sentiment: "
						+ Sentiment);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		test();
	}

}
