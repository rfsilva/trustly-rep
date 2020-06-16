package br.com.rodrigo.githubfiles.service.impl.obj;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class FileSum {

	private String extension;
	private Integer numFiles;
	private Long bytes;
	private Integer lines;
	
	public FileSum(String extension, Integer numFiles, Long bytes, Integer lines) {
		this.extension = extension;
		this.bytes = bytes;
		this.lines = lines;
		this.numFiles = numFiles;
	}
}
