package com.gft.practices.reactive.webcontroller.resources;

import com.gft.practices.reactive.beans.dto.Entity;
import com.gft.practices.reactive.model.services.EntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@RestController
@RequestMapping(EntityResource.ENTITY)
public class EntityResource {

  public static final String ENTITY = "/entity";
  private EntityService entityService;

  @Autowired
  public EntityResource(final EntityService entityService) {
    this.entityService = entityService;
  }

  @GetMapping(value = "/all")
  public Flux<Entity> findAll() {
    log.debug("paso");
    return entityService.findAll();
  }

  @GetMapping
  public Mono<ResponseEntity<Page<Entity>>> findAllPaginated(@RequestParam int page, @RequestParam int size) {
    log.debug("paso");
    Pageable pageable = PageRequest.of(page, size);
    return entityService.findAllPaginated(pageable)
        .map(ResponseEntity::ok);
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Entity>> findById(@PathVariable("id") String id) {
    log.debug("paso");
    return entityService.findById(id)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping("exception/{id}")
  public Mono<Entity> throwException(@PathVariable("id") String id) {
    log.debug("paso");
    throw new IllegalArgumentException("peta " + id);
    //return entityService.findById(id);
  }
}
