package br.com.rodrigo.githubfiles.service;

import java.util.Map;
import java.util.Optional;

import br.com.rodrigo.githubfiles.service.impl.obj.FileSum;

public interface GithubRepoAnalyzer  {

	public Optional<Map<String, FileSum>> performAnalysis(String githubRepo);
}
