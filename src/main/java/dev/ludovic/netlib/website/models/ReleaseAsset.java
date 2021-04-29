
package dev.ludovic.netlib.website;

import java.util.Map;

public final class ReleaseAsset {

  private int id;
  private String browser_download_url;
  private String name;
  private String content_type;

  private ReleaseAsset() {}

  public int getId() {
    return id;
  }

  public String getBrowser_download_url() {
    return browser_download_url;
  }

  public String getName() {
    return name;
  }

  public String getContent_type() {
    return content_type;
  }

  public static class Finder extends dev.ludovic.netlib.website.Finder<Finder> {

    public ReleaseAsset findById(int id) {
      return restTemplate.getForObject("https://api.github.com/repos/luhenry/netlib/releases/assets/{id}", ReleaseAsset.class, Map.of("id", id));
    }
  }
}
