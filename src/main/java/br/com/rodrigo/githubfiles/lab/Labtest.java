package br.com.rodrigo.githubfiles.lab;

import java.util.Map;

public class Labtest {

	public static void main(String []args) {
		Map<String, FileSum> result = new GithubRepoAnalyzer().analyze("https://github.com/TheAlgorithms/Java", FileType.FOLDER);
		System.out.println(result);
	}
}
