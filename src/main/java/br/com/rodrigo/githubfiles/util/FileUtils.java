package br.com.rodrigo.githubfiles.util;

public final class FileUtils {
	
	private FileUtils() {
	}

	public static String getExtension(String name) {
		if (name == null) {
			return "";
		}
		if (name.lastIndexOf(".") >= 0) {
			return name.substring(name.lastIndexOf(".")).toLowerCase();
		}
		return name.toLowerCase();
	}
}
