package dev.ludovic.netlib.website;

import java.util.concurrent.atomic.AtomicLong;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import dev.ludovic.netlib.website.models.*;

@RestController
public class ReleaseController {

  private RestTemplate restTemplate;

  public ReleaseController(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  @GetMapping("/releases")
  @CrossOrigin(origins = "*")
  public Iterable<Release> releases() {
    return new Release.Finder()
                .restTemplate(restTemplate)
                .all();
  }

  @GetMapping("/releases/assets/{asset_id}")
  @CrossOrigin(origins = "*")
  public ReleaseAsset releasesAssets(@PathVariable int asset_id) {
    return new ReleaseAsset.Finder()
                .restTemplate(restTemplate)
                .findById(asset_id);
  }

  @GetMapping(
    value = "/releases/assets/{asset_id}",
    produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  @CrossOrigin(origins = "*")
  public byte[] releasesAssetsContent(@PathVariable int asset_id) {
    var asset = new ReleaseAsset.Finder()
                    .restTemplate(restTemplate)
                    .findById(asset_id);

    return restTemplate.execute(asset.getBrowser_download_url(), HttpMethod.GET, null, response -> {
      return StreamUtils.copyToByteArray(response.getBody());
    });
  }
}
