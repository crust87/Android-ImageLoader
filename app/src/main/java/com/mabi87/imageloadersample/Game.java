package com.mabi87.imageloadersample;

public class Game {
	
	private String title;
	private String content;
	private String imagePath;
	
	public Game(String pTitle, String pContent, String pImagePath) {
		title = pTitle;
		content = pContent;
		imagePath = pImagePath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
