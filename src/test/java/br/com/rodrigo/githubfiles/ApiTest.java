package br.com.rodrigo.githubfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.rodrigo.githubfiles.dto.GithubFilesSummaryDTO;

@SpringBootTest(classes = GithubFilesApp.class)
class ApiTest {

	@Test
	void contextLoads() {
		GithubFilesSummaryDTO dto = new GithubFilesSummaryDTO();
		dto.setFileDetailsList(new ArrayList<>());
		assertThat(dto.getFileDetailsList().size()).isEqualTo(0);
	}
}
