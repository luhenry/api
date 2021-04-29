
package dev.ludovic.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class Release {

  private String tag_name;
  private ReleaseAsset[] assets;

  private Release() {}

  public String getTag_name() {
    return tag_name;
  }

  public Iterable<ReleaseAsset> getAssets() {
    return Arrays.asList(assets);
  }

  public static class Finder extends dev.ludovic.api.Finder<Finder> {

    public Iterable<Release> all(String owner, String repo) {
      assert restTemplate != null;
      var releases = new ArrayList<Release>();
      for (int page = 1;; page++) {
        var result = restTemplate.getForObject("https://api.github.com/repos/{owner}/{repo}/releases?page={page}&per_page=10",
                        Release[].class, Map.of("owner", owner, "repo", repo, "page", page));
        if (result.length == 0) {
          break;
        }
        for (int i = 0; i < result.length; i++) {
          releases.add(result[i]);
        }
      }
      return releases;
    }
  }
}
