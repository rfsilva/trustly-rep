package br.com.rodrigo.githubfiles.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface GithubContentManager {

	public void checkUrl(String url);
	
	public Document getDocument(String html);
	
	public String getFileExtension(Document doc, String url);
	
	public String getFileDetails(Document doc);
	
	public Integer getLines(String content);
	
	public Long getBytes(String content);
	
	public Elements getTableContent(Document doc, String url);
	
	public String getIcon(Element line);
	
	public String getLink(Element line);
}
