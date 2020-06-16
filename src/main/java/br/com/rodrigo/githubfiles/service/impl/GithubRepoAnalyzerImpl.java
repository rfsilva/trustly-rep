package br.com.rodrigo.githubfiles.service.impl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import br.com.rodrigo.githubfiles.enums.FileType;
import br.com.rodrigo.githubfiles.enums.UnitOfMeasurement;
import br.com.rodrigo.githubfiles.service.GithubRepoAnalyzer;
import br.com.rodrigo.githubfiles.service.impl.obj.FileSum;
import br.com.rodrigo.githubfiles.util.FileUtils;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public final class GithubRepoAnalyzerImpl implements GithubRepoAnalyzer {

	private static final String PREFIX = "https://github.com";

	private Map<String, FileSum> contentMap;
	
	public Map<String, FileSum> performAnalysis(String html) {
		contentMap = new HashMap<>();
		return performAnalysis(html, FileType.FOLDER);
	}
				
	private Map<String, FileSum> performAnalysis(String html, FileType type) {
		//1. Gets the document
		Document doc = getDoc(html);
		if (doc != null) {
			if (type == FileType.FILE) {
				performFileAnalysis(doc);
			}
			if (type == FileType.FOLDER) {
				performFolderAnalysis(doc);
			}
		}
		return contentMap;
	}
	
	private Document getDoc(String html) {
		Document doc = null;
		final Integer attempts = 10;
		Integer current = 0;
		do {
			try {
				doc = Jsoup.connect(html).get();
			} catch(HttpStatusException e) {
				log.debug("Timeout on {} attempt", current + 1);
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
			current++;
		} while (doc == null && current < attempts);
		return doc;
	}

	private void performFileAnalysis(Document doc) {
		String fileName = doc.select("strong.final-path").get(0).html();
		String extension = FileUtils.getExtension(fileName);
		
		Elements divElements = doc.select("div.text-mono");
		String result = divElements.get(0).html();
		
		Integer lines = getLines(result);
		Long bytes = getBytes(result);
		increment(extension, lines, bytes);
	}
	
	private Integer getLines(String content) {
		if (content.indexOf("lines") >= 0) {
			return Integer.parseInt(content.substring(0, content.indexOf("lines")).trim());
		}
		return 0;
	}
	
	private Long getBytes(String content) {
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
	
	private void performFolderAnalysis(Document doc) {
		Elements lines = doc.select("table.files").select("tr.js-navigation-item");
		for (Element line : lines) {
			Elements el = line.select("svg");
			if (el != null && !el.isEmpty()) {
				String icon = el.get(0).attributes().get("aria-label");
				String link = PREFIX + line.select("a.js-navigation-open").get(0).attributes().get("href");
				if ("directory".equals(icon)) {
					performAnalysis(link, FileType.FOLDER);
				} else {
					performAnalysis(link, FileType.FILE);
				}
			}
		}
	}
	
	private synchronized void increment(String extension, Integer lines, Long bytes) {
		FileSum content = contentMap.get(extension);
		if (content == null) {
			content = new FileSum(extension, 0L, 0);
		}
		content.setBytes(content.getBytes() + bytes);
		content.setLines(content.getLines() + lines);
		contentMap.put(extension, content);
	}
}
