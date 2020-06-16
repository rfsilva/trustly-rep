package br.com.rodrigo.githubfiles.lab;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.rodrigo.githubfiles.util.FileUtils;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class GithubRepoAnalyzer {

	private static final String PREFIX = "https://github.com";

	public Map<String, FileSum> analyze(String html, FileType type) {
		return analyze(html, type, new HashMap<>());
	}
				
	public Map<String, FileSum> analyze(String html, FileType type, Map<String, FileSum> map) {
		//1. Gets the document
		Document doc = getDoc(html);
		if (doc != null) {
			if (type == FileType.FILE) {
				analyzeFile(doc, map);
			}
			if (type == FileType.FOLDER) {
				analyzeFolder(doc, map);
			}
		}
		return map;
	}
	
	private Document getDoc(String html) {
		System.out.println(html);
		Document doc = null;
		Integer attempts = 10;
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

	private void analyzeFile(Document doc, Map<String, FileSum> map) {
		String fileName = doc.select("strong.final-path").get(0).html();
		String extension = FileUtils.getExtension(fileName);
		
		Elements divElements = doc.select("div.text-mono");
		String result = divElements.get(0).html();
		System.out.println(result);
		
		//Extract lines and bytes
		Integer lines = 0;
		if (result.indexOf("lines") > 0) {
			lines = Integer.parseInt(result.substring(0, result.indexOf("lines")).trim());
		}

		String bytesBlock = result;
		if (result.indexOf("</span>") > 0) {
			bytesBlock = result.substring(result.indexOf("</span>") + 7).trim();
		}
		String measureUnit = bytesBlock.substring(bytesBlock.lastIndexOf(" ")).trim();
		
		//Bytes
		//KB
		//MB
		Long bytes = 0L;
		
		FileSum fs = map.get(extension);
		if (fs == null) {
			fs = new FileSum();
		}
		fs.setBytes(bytes + fs.getBytes());
		fs.setLines(fs.getLines() + lines);
		map.put(extension, fs);
	}
	
	private void analyzeFolder(Document doc, Map<String, FileSum> map) {

		//2. Check if the doc is a file list descriptor or a single file descriptor
		Elements lines = doc.select("table.files").select("tr.js-navigation-item");
		for (Element line : lines) {
			Elements el = line.select("svg");
			if (el != null && el.size() > 0) {
				String icon = el.get(0).attributes().get("aria-label");
				String link = PREFIX + line.select("a.js-navigation-open").get(0).attributes().get("href");
				if ("directory".equals(icon)) {
					analyze(link, FileType.FOLDER);
				} else {
					analyze(link, FileType.FILE);
				}
			}
		}
	}
}
