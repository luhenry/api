package dev.ludovic.netlib.website;

import java.util.concurrent.atomic.AtomicLong;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
}
