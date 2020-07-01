package br.com.rodrigo.githubfiles.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import br.com.rodrigo.githubfiles.dto.GithubFilesSummaryDTO;
import br.com.rodrigo.githubfiles.dto.GithubRepositoryDTO;
import br.com.rodrigo.githubfiles.exception.GithubException;
import br.com.rodrigo.githubfiles.service.GithubContentManager;
import br.com.rodrigo.githubfiles.service.GithubRepoAnalyzerService;
import br.com.rodrigo.githubfiles.service.impl.GithubContentManagerImpl;
import br.com.rodrigo.githubfiles.service.impl.GithubRepoAnalyzerServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

	@Mock
	private GithubContentManager contentManager;
	
	public ControllerTest() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	@Order(1)
	public void performRealTest() {
		GithubContentManager analyzer = new GithubContentManagerImpl();
		GithubRepoAnalyzerService githubService = new GithubRepoAnalyzerServiceImpl(analyzer);
		GithubController controller = new GithubController(githubService);
		
		ResponseEntity<GithubFilesSummaryDTO> result = controller.getSummary(GithubRepositoryDTO.builder().url("https://github.com/rfsilva/trustly-rep/tree/develop").build());
		assertThat(result.getBody().getFileDetailsList().size()).isEqualTo(17);
	}
	
	@Test
	@Order(2)
	public void performRealTestWithBadUrl() {
		GithubContentManager analyzer = new GithubContentManagerImpl();
		GithubRepoAnalyzerService githubService = new GithubRepoAnalyzerServiceImpl(analyzer);
		GithubController controller = new GithubController(githubService);
		
		try {
			ResponseEntity<GithubFilesSummaryDTO> result = controller.getSummary(GithubRepositoryDTO.builder().url("this is not a valid URL").build());
			assertThat(result.getBody().getErrorCode()).isEqualTo(400);
		} catch (GithubException e) {
			System.out.println(e.getMessage());
		}
	}
}
