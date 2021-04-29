
package dev.ludovic.netlib.website;

public final class ReleaseAsset {

  private String browser_download_url;
  private String name;
  private String content_type;

  private ReleaseAsset() {}

  public String getBrowser_download_url() {
    return browser_download_url;
  }

  public String getName() {
    return name;
  }

  public String getContent_type() {
    return content_type;
  }
}
