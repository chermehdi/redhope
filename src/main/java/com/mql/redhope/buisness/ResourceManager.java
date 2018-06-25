package com.mql.redhope.buisness;

import com.mql.redhope.buisness.qualifiers.UserProperties;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.UUID;
import javax.inject.Inject;

/**
 * @author mehdithe
 */
public class ResourceManager {

  @Inject
  @UserProperties("assets-config.properties")
  Properties fileProperties;

  /**
   * saves the user image to the default resources directory specified in the
   * assets-config.properties file
   *
   * @param img byte array representing the image's content
   * @param fileName the original file name of the image
   * @param extension the extension of the file image, generally resolved from MIME type
   * @return a String representing the url to the newly created img, or the default avatar
   */
  public String saveUserResource(byte[] img, String fileName, String extension) {
    String RESOURCES_ROOT = fileProperties.getProperty("user.directory");
    File ROOT_DIR = createDirectory(RESOURCES_ROOT);
    String fileHash = UUID.randomUUID().toString().replaceAll("\\-", "");
    String fileRealName = fileName + fileHash + "." + extension;
    try (DataOutputStream dos = new DataOutputStream(
        new FileOutputStream(
            Paths.get(ROOT_DIR.getAbsolutePath(), fileRealName)
                .toString()))) {
      dos.write(img);
      return "/static/images/" + fileRealName;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ""; // return default avatar path
  }

  private File createDirectory(String path) {
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
    return file;
  }
}
