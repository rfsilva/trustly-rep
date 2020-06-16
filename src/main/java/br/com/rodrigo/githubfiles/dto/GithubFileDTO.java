package br.com.rodrigo.githubfiles.dto;

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
public class GithubFileDTO {
	
	@ApiModelProperty(notes = "File extension")
	private String extension;
	
	@ApiModelProperty(notes = "Total lines")
	private Integer lines;
	
	@ApiModelProperty(notes = "Number of files with referred extension")
	private Integer numFiles;

	@ApiModelProperty(notes = "Total bytes")
	private Long bytes;
}
