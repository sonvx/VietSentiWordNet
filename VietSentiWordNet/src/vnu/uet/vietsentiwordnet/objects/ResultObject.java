package vnu.uet.vietsentiwordnet.objects;

public class ResultObject {
	double score;
	String resultString;
	String simpletResult;
	int size;
	public ResultObject() {
		score = 0;
		resultString = "";
		size = 0;
	}
	
	ResultObject(double d, String simpletResult_, String resultString_, int i) {
		this.simpletResult = simpletResult_;
		this.score = d;
		this.resultString = resultString_;
		this.size = i;
	}
	
	public String getSimpleResult() {
		return this.simpletResult;
	}
	
	public String getResultString() {
		return this.resultString;
	}
	
	public double getScore() {
		return this.score;
	}
}