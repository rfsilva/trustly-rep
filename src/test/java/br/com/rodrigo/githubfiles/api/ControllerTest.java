package br.com.rodrigo.githubfiles.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import br.com.rodrigo.githubfiles.dto.GithubFilesSummaryDTO;
import br.com.rodrigo.githubfiles.dto.GithubRepositoryDTO;
import br.com.rodrigo.githubfiles.service.GithubRepoAnalyzer;
import br.com.rodrigo.githubfiles.service.GithubService;
import br.com.rodrigo.githubfiles.service.impl.GithubRepoAnalyzerImpl;
import br.com.rodrigo.githubfiles.service.impl.GithubServiceImpl;
import br.com.rodrigo.githubfiles.service.impl.obj.FileSum;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

	@Mock
	private GithubRepoAnalyzer githubRepoAnalyzer;
	
	public ControllerTest() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	@Order(1)
	public void performMockTest() {
		Mockito.doReturn(buildSummary()).when(githubRepoAnalyzer).performAnalysis(Mockito.anyString());
		GithubService githubService = new GithubServiceImpl(githubRepoAnalyzer);
		GithubController controller = new GithubController(githubService);
		ResponseEntity<GithubFilesSummaryDTO> result = controller.getSummary(GithubRepositoryDTO.builder().url("any-place").build());
		assertThat(result.getBody().getFileDetailsList().size()).isEqualTo(4);
	}
	
	@Test
	@Order(2)
	public void performRealTest() {
		GithubRepoAnalyzer analyzer = new GithubRepoAnalyzerImpl();
		GithubService githubService = new GithubServiceImpl(analyzer);
		GithubController controller = new GithubController(githubService);
		
		ResponseEntity<GithubFilesSummaryDTO> result = controller.getSummary(GithubRepositoryDTO.builder().url("https://github.com/rfsilva/trustly-rep/tree/develop").build());
		assertThat(result.getBody().getFileDetailsList().size()).isEqualTo(17);
	}
	
	@Test
	@Order(3)
	public void performRealTestWithBadUrl() {
		GithubRepoAnalyzer analyzer = new GithubRepoAnalyzerImpl();
		GithubService githubService = new GithubServiceImpl(analyzer);
		GithubController controller = new GithubController(githubService);
		
		ResponseEntity<GithubFilesSummaryDTO> result = controller.getSummary(GithubRepositoryDTO.builder().url("this is not a valid URL").build());
		assertThat(result.getBody().getErrorCode()).isEqualTo(1);
	}
	
	private Optional<Map<String, FileSum>> buildSummary() {
		Map<String, FileSum> output = new HashMap<>();
		output.put(".java", FileSum.builder().bytes(128423L).lines(222422).build());
		output.put(".xml", FileSum.builder().bytes(428L).lines(115).build());
		output.put(".properties", FileSum.builder().bytes(99L).lines(42).build());
		output.put(".yml", FileSum.builder().bytes(331L).lines(130).build());
		return Optional.of(output);
	}
}
