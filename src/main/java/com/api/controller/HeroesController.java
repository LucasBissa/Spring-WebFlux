package com.api.controller;

import static com.api.constants.HeroesConstant.HEROES_ENDPOINT_LOCAL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.document.Heroes;
import com.api.service.HeroesService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(HEROES_ENDPOINT_LOCAL)
@Slf4j
public class HeroesController {
	
  @Autowired	
  private HeroesService heroesService;

//  private static final org.slf4j.Logger log =
//    org.slf4j.LoggerFactory.getLogger(HeroesController.class);

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Flux<Heroes> getAllItems() {
    log.info("requesting the list off all heroes");
    return heroesService.findAll();
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Heroes>> findByIdHero(@PathVariable String id) {
    log.info("Requesting the hero with id {}", id);
    return heroesService.findByIdHero(id)
      .map((item) -> new ResponseEntity<>(item, HttpStatus.OK))
      .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Heroes> createHero(@RequestBody Heroes heroes) {
    log.info("A new Hero was Created");
    return heroesService.save(heroes);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public Mono<HttpStatus> deletebyIDHero(@PathVariable String id) {
    heroesService.deletebyIDHero(id);
    log.info("Deleting the hero with id {}", id);
    return Mono.just(HttpStatus.NOT_FOUND);
  }
}
