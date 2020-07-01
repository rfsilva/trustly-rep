package br.com.rodrigo.githubfiles.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import br.com.rodrigo.githubfiles.enums.UnitOfMeasurement;
import br.com.rodrigo.githubfiles.exception.GithubException;
import br.com.rodrigo.githubfiles.message.MessageConstants;
import br.com.rodrigo.githubfiles.message.MessageManager;
import br.com.rodrigo.githubfiles.service.GithubContentManager;
import br.com.rodrigo.githubfiles.util.FileUtils;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class GithubContentManagerImpl implements GithubContentManager {

	private static final String GITHUB_PREFIX = "https://github.com";

	public void checkUrl(String url) {
		try {
			new URL(url).openStream().close();
		} catch (MalformedURLException e) {
			String message = MessageManager.getMessage(MessageConstants.INVALID_URL, url);
			log.error(message, e);
			throw new GithubException(message);
		} catch (IOException e) {
			String message = MessageManager.getMessage(MessageConstants.INVALID_URL, url);
			log.error(message, e);
			throw new GithubException(message);
		}
		
		if (!url.toLowerCase().startsWith(GITHUB_PREFIX)) {
			String message = MessageManager.getMessage(MessageConstants.INVALID_GITHUB_URL, url);
			throw new GithubException(message);
		}
	}
	
	public Document getDocument(String url) {
		Document doc = null;
		final Integer attempts = 20;
		Integer current = 0;
		do {
			try {
				doc = Jsoup.connect(url).get();
			} catch(HttpStatusException e) {
				log.debug("{}, Timeout on {} attempt", url, current + 1);
				try {
					Thread.sleep(2000L);
				} catch (InterruptedException e1) {
					log.error(e1);
				}
			} catch (IOException e) {
				log.error(url, e);
			}
			current++;
		} while (doc == null && current < attempts);
		return doc;
	}

	public String getFileExtension(Document doc, String url) {
		Elements fileElementList = doc.select("strong.final-path");
		if (fileElementList == null || fileElementList.isEmpty()) {
			log.error("Not a valid Github file page");
			throw new GithubException(MessageManager.getMessage(MessageConstants.INVALID_GITHUB_FILE_PAGE, url));
		}
		String fileName = fileElementList.get(0).html();
		return FileUtils.getExtension(fileName);
	}
	
	public String getFileDetails(Document doc) {
		Elements divElements = doc.select("div.text-mono");
		return divElements.get(0).html();
	}
	
	public Integer getLines(String content) {
		if (content.indexOf("lines") >= 0) {
			return Integer.parseInt(content.substring(0, content.indexOf("lines")).trim());
		}
		return 0;
	}
	
	public Long getBytes(String content) {
		String bytesBlock = content;
		if (content.indexOf("</span>") >= 0) {
			bytesBlock = content.substring(content.indexOf("</span>") + 7).trim();
		}
		UnitOfMeasurement unit = UnitOfMeasurement.of(bytesBlock.substring(bytesBlock.lastIndexOf(" ")).trim());
		
		DecimalFormat format = new DecimalFormat("#.###", new DecimalFormatSymbols(Locale.US));
		Number n;
		try {
			n = format.parse(bytesBlock.substring(0, bytesBlock.lastIndexOf(" ")).trim());
		} catch (ParseException e) {
			n = 0L;
		}
		return n.longValue() * unit.getMultiplier();
	}
	
	public Elements getTableContent(Document doc, String url) {
		Elements table = doc.select("div.js-navigation-container");
		if (table == null || table.isEmpty()) {
			log.error("Not a valid Github folder page");
			throw new GithubException(MessageManager.getMessage(MessageConstants.INVALID_GITHUB_FOLDER_PAGE, url));
		}
		return table.select("div.Box-row");
	}
	
	public String getIcon(Element line) {
		Elements el = line.select("svg");
		if (el != null && !el.isEmpty()) {
			return el.get(0).attributes().get("aria-label");
		}
		return "";
	}

	public String getLink(Element line) {
		Elements el = line.select("svg");
		if (el != null && !el.isEmpty()) {
			return GITHUB_PREFIX + line.select("a.js-navigation-open").get(0).attributes().get("href");
		}
		return "";
	}
}
