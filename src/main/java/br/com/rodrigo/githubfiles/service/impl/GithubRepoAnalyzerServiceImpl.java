package br.com.rodrigo.githubfiles.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodrigo.githubfiles.dto.GithubFilesSummaryDTO;
import br.com.rodrigo.githubfiles.dto.converter.Converters;
import br.com.rodrigo.githubfiles.enums.FileType;
import br.com.rodrigo.githubfiles.exception.GithubException;
import br.com.rodrigo.githubfiles.message.MessageConstants;
import br.com.rodrigo.githubfiles.message.MessageManager;
import br.com.rodrigo.githubfiles.service.GithubContentManager;
import br.com.rodrigo.githubfiles.service.GithubRepoAnalyzerService;
import br.com.rodrigo.githubfiles.service.impl.obj.FileSum;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public final class GithubRepoAnalyzerServiceImpl implements GithubRepoAnalyzerService {

	private Map<String, FileSum> contentMap;	
	
	private GithubContentManager validator;
	
	@Autowired
	public GithubRepoAnalyzerServiceImpl(GithubContentManager validator) {
		this.validator = validator;
	}
	
	public GithubFilesSummaryDTO performAnalysis(String html) {
		validator.checkUrl(html);
		contentMap = new ConcurrentHashMap<>();
		return Converters.convert(performAnalysis(html, FileType.FOLDER));
	}
				
	private Map<String, FileSum> performAnalysis(String url, FileType type) {
		//1. Get the document
		Document doc = validator.getDocument(url);
		if (doc != null) {
			switch (type) {
			case FILE:
				performFileAnalysis(doc, url);
				break;
			case FOLDER:
				performFolderAnalysis(doc, url);
				break;
			default:
				break; 
			}
		} else {
			log.error("Invalid content: {}", url);
			throw new GithubException(MessageManager.getMessage(MessageConstants.INVALID_GITHUB_CONTENT, url));
		}
		return contentMap;
	}

	private void performFileAnalysis(Document doc, String url) {
		String result = validator.getFileDetails(doc);
		Integer lines = validator.getLines(result);
		Long bytes = validator.getBytes(result);
		increment(validator.getFileExtension(doc, url), lines, bytes);
	}
	
	private void performFolderAnalysis(Document doc, String url) {
		Elements lines = validator.getTableContent(doc, url);
		for (Element line : lines) {
			String icon = validator.getIcon(line);
			String link = validator.getLink(line);
			if ("".equals(link)) {
				continue;
			}
			
			if ("directory".equalsIgnoreCase(icon)) {
				performAnalysis(link, FileType.FOLDER);
			} else {
				performAnalysis(link, FileType.FILE);
			}
		}
	}
	
	private synchronized void increment(String extension, Integer lines, Long bytes) {
		FileSum content = contentMap.get(extension);
		if (content == null) {
			content = new FileSum(extension, 0, 0L, 0);
		}
		content.setNumFiles(content.getNumFiles() + 1);
		content.setBytes(content.getBytes() + bytes);
		content.setLines(content.getLines() + lines);
		contentMap.put(extension, content);
	}
}
