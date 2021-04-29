package dev.ludovic.api;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import dev.ludovic.api.models.*;

@RestController
public class ReleaseController {

  private RestTemplate restTemplate;

  public ReleaseController(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  @GetMapping("/{owner}/{repo}/releases")
  @CrossOrigin(origins = "*")
  public Iterable<Release> releases(@PathVariable String owner, @PathVariable String repo) {
    return new Release.Finder()
                .restTemplate(restTemplate)
                .all(owner, repo);
  }

  @GetMapping("/{owner}/{repo}/releases/assets/{asset_id}")
  @CrossOrigin(origins = "*")
  public ReleaseAsset releasesAssets(
      @PathVariable String owner, @PathVariable String repo, @PathVariable int asset_id) {
    return new ReleaseAsset.Finder()
                .restTemplate(restTemplate)
                .findById(owner, repo, asset_id);
  }

  @GetMapping(
    value = "/{owner}/{repo}/releases/assets/{asset_id}",
    produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  @CrossOrigin(origins = "*")
  public ResponseEntity<Void> releasesDownloadAsset(
      @PathVariable String owner, @PathVariable String repo, @PathVariable int asset_id) {
    var asset = new ReleaseAsset.Finder()
                      .restTemplate(restTemplate)
                      .findById(owner, repo, asset_id);

    return ResponseEntity.status(HttpStatus.FOUND).location(
            URI.create(asset.getBrowser_download_url().replaceFirst("^http(s)?://github.com", "")))
              .build();
  }

  @GetMapping(
    value = "/{owner}/{repo}/releases/download/{release}/{asset}",
    produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  @CrossOrigin(origins = "*")
  public ResponseEntity<byte[]> releasesDownloadAsset(
      @PathVariable String owner, @PathVariable String repo, @PathVariable String release, @PathVariable String asset) {
    var body = restTemplate.execute("https://github.com/{owner}/{repo}/releases/download/{release}/{asset}",
                                    HttpMethod.GET, null,
                                    response -> { return StreamUtils.copyToByteArray(response.getBody()); },
                                    Map.of("release", release, "asset", asset));

    return ResponseEntity.ok().cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS)).body(body);
  }
}
