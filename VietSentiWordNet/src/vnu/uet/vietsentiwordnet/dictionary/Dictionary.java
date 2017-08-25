package vnu.uet.vietsentiwordnet.dictionary;

public class Dictionary {
	private String headWord;
	private String wordType;
	private String category;
	private String subCategory;
	private String def;
	private String exam;
	private String categorialMeaning;
	
	public Dictionary() {
		
	}
	
	public Dictionary(String headWord, String wordType, String category, String subCategory, String def, String exam, String categorialMeaning) {
		this.headWord = headWord;
		this.wordType = wordType;
		this.category = category;
		this.subCategory = subCategory;
		this.def = def;
		this.exam = exam;
		this.categorialMeaning = categorialMeaning;
	}
	
	public String getHeadWord() {
		return headWord;
	}
	
	public String getWordType () {
		return wordType;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getSubCategory() {
		return subCategory;
	}
	
	public String getCategorialMeaning() {
		return categorialMeaning;
	}
	
	public String getDef() {
		return def;
	}
	
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setHeadWord(String headWord) {
		this.headWord = headWord;
	}
	public void setWordType(String wordType) {
		this.wordType = wordType;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public void setCategorialMeaning(String categorialMeaning) {
		this.categorialMeaning = categorialMeaning;
	}
	public void setDef(String def) {
		this.def = def;
	}
	
	public String myToString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Entry Details - ");
		sb.append("HeadWord:" + getHeadWord());
		sb.append(", ");
		sb.append("WordType:" + getWordType());
		sb.append(", ");
		sb.append("Category:" + getCategory());
		sb.append(", ");
		sb.append("SubCategory:" + getSubCategory());
		sb.append(", ");
		sb.append("Def:" + getDef());
		sb.append(".");
		
		return sb.toString();
	}
}
