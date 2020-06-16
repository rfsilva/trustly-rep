package br.com.rodrigo.githubfiles.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodrigo.githubfiles.dto.GithubFilesSummaryDTO;
import br.com.rodrigo.githubfiles.service.GithubService;

@RestController
@RequestMapping("/github")
public class GithubController {

	private GithubService githubService;
	
	@Autowired
	public GithubController(GithubService githubService) {
		this.githubService = githubService;
	}
	
	@GetMapping("/{repository}")
	public ResponseEntity<GithubFilesSummaryDTO> getSummary(@PathVariable String repository) {
		GithubFilesSummaryDTO result = githubService.getGithubSummary(repository);
		return ResponseEntity.ok(result);
	}
}
