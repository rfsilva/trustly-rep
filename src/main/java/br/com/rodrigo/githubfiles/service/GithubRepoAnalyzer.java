package br.com.rodrigo.githubfiles.service;

import java.util.Map;

import br.com.rodrigo.githubfiles.service.impl.obj.FileSum;

public interface GithubRepoAnalyzer  {

	public Map<String, FileSum> performAnalysis(String githubRepo);
}
