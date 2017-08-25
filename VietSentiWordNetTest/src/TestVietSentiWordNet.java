import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import vnu.uet.vietsentiwordnet.apis.OpinionFinder;
import vnu.uet.vietsentiwordnet.objects.ResultObject;
import vnu.uet.vietsentiwordnet.objects.SentenceLevel;


public class TestVietSentiWordNet {
	public static void main(String args[]) {
		SentenceLevel sl = OpinionFinder.getInstance().loadModels();
		// do opinion mining at sentenceLevel
		ResultObject res = new ResultObject();
		while (true) {
			System.out.print("Enter a sentence <or press enter to use a default input>:");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				String body = br.readLine();
				if (body.equals("") || body == null)
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
}
