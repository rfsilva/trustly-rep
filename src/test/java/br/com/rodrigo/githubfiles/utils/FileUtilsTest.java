package br.com.rodrigo.githubfiles.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.rodrigo.githubfiles.util.FileUtils;

@RunWith(MockitoJUnitRunner.class)
public class FileUtilsTest {

	@Test
	public void emptyNameTest() {
		String res = FileUtils.getExtension("");
		assertThat(res).isEqualTo("");
	}

	@Test
	public void nullNameTest() {
		String res = FileUtils.getExtension(null);
		assertThat(res).isEqualTo("");
	}

	@Test
	public void noExtensionNameTest() {
		String res = FileUtils.getExtension("gitignore");
		assertThat(res).isEqualTo("gitignore");
	}

	@Test
	public void onlyExtensionNameTest() {
		String res = FileUtils.getExtension(".gitignore");
		assertThat(res).isEqualTo(".gitignore");
	}

	@Test
	public void commonFileNameTest() {
		String res = FileUtils.getExtension("a.gitignore");
		assertThat(res).isEqualTo(".gitignore");
	}
}
