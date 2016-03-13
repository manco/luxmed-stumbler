package pl.manco.luxmed.config

import java.nio.file.Files

import org.junit.rules.TemporaryFolder
import org.junit.{Rule, Test}

class ConfigLoaderTest {

  private val _folder = new TemporaryFolder

  @Rule
  def folder = _folder

  @Test
  def shouldReturnNoneIfConfigNotCompleted() = {
    //given
    //when
    val result = ConfigLoader.load(Array())

    //then
    assert(result.isEmpty)
  }

  @Test
  def shouldUseCommandLineOverFileConfig() = {
    //given
    val file = folder.newFile("config.file")
    Files.write(
      file.toPath,
        ("luxmed {" + "\n" +
        "user: \"jacek\"  " + "\n" +
        "password: \"jackahaslo\"" + "\n" +
        "}")
        .getBytes
    )

    //when
    val result = ConfigLoader.load(Array("-u", "placek" ,"-p" , "plackahaslo"), Some(file))

    //then
    assert(result.get.getString("luxmed.user").equals("placek"))
    assert(result.get.getString("luxmed.password").equals("plackahaslo"))
  }

  @Test
  def shouldFallbackToFileConfig() = {
    //given
    val file = folder.newFile("config.file")
    Files.write(
      file.toPath,
      ("luxmed {" + "\n" +
        "user: \"jacek\"  " + "\n" +
        "password: \"jackahaslo\"" + "\n" +
        "}")
        .getBytes
    )

    //when
    val result = ConfigLoader.load(Array(), Some(file))

    //then
    assert(result.get.getString("luxmed.user").equals("jacek"))
    assert(result.get.getString("luxmed.password").equals("jackahaslo"))
  }
}
