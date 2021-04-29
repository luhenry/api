
package dev.ludovic.api;

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

  public static class Finder extends dev.ludovic.api.Finder<Finder> {

    public ReleaseAsset findById(String owner, String repo, int id) {
      return restTemplate.getForObject("https://api.github.com/repos/{owner}/{repo}/releases/assets/{id}",
                ReleaseAsset.class, Map.of("owner", owner, "repo", repo, "id", id));
    }
  }
}
