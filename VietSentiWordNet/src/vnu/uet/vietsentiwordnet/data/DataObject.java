package vnu.uet.vietsentiwordnet.data;

public class DataObject {
	public int id;
	public String title_news;
	String body_news;
	public String title_comment;
	public String comment;
	public String holder;

	DataObject() {

	}

	DataObject(int id_, String title_news, String body_news, String title_cm, String cm,String holder) {
		this.id = id_;
		this.title_news = title_news;
		this.title_comment = title_cm;
		this.body_news = body_news;
		this.comment = cm;
		this.holder = holder;
	}
	
	void setId(int id_) {
		this.id = id_;
	}
	
	void setTitle(String t) {
		this.title_news = t;
	}
	
	void setBodyNews(String t) {
		this.body_news = t;
	}

	void setTitle_cm(String t) {
		this.title_comment = t;
	}

	void setComment(String cm) {
		this.comment = cm;
	}

	void setHolder(String t) {
		this.holder = t;
	}
	public int getId(){
		return this.id;
	}
	
	public String getTitle_news(){
		return this.title_news;
	}
	
	public String getBodyNews() {
		return this.getBodyNews();
	}
	
	public String getTitleComment() {
		return this.title_comment;
	}
	
	public String getComment() {
		return this.comment;
	}
	public String getHolder() {
		return this.holder;
	}
}
