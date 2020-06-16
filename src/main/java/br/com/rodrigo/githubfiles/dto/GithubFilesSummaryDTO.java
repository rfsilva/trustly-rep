package br.com.rodrigo.githubfiles.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GithubFilesSummaryDTO {

	@JsonProperty("files")
	List<GithubFileDTO> fileDetailsList;
}
