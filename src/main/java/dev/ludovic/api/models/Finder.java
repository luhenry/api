
package dev.ludovic.api;

import org.springframework.web.client.RestTemplate;

public class Finder<F> {

  protected RestTemplate restTemplate;

  public Finder() {}

  public F restTemplate(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    return (F)this;
  }
}
