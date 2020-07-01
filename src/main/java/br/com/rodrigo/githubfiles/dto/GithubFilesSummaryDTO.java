package br.com.rodrigo.githubfiles.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
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

	@JsonProperty("result")
	@ApiModelProperty(notes = "Result of analysis, grouped by file extension")
	private List<GithubFileDTO> fileDetailsList;
	
	@ApiModelProperty(notes = "Result of processing: 0 - Success; Other - error, see errorDescription for details")
	private Integer errorCode;
	
	@ApiModelProperty(notes = "Details of error reported")
	private String description;
}
