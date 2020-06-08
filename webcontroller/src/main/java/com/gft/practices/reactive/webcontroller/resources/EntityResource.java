package com.gft.practices.reactive.webcontroller.resources;

import com.gft.practices.reactive.beans.dto.Entity;
import com.gft.practices.reactive.model.services.EntityService;
import com.gft.practices.reactive.webcontroller.exceptions.AppNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(EntityResource.ENTITY)
public class EntityResource extends BaseResource<Entity, EntityService> {

  public static final String ENTITY = "/entity";

  @Autowired
  public EntityResource(final EntityService entityService) {
    super(entityService);
  }

  @GetMapping(value = "/all")
  public ResponseEntity<List<Entity>> findAll() {
    log.debug("paso");
    List<Entity> all = this.service.findAll();
    if (all.isEmpty()) {
      throw new AppNotFoundException();
    } else {
      return ResponseEntity.ok(all);
    }
  }

  @GetMapping
  public ResponseEntity<Page<Entity>> findAllPaginated(Pageable page) {
    log.debug("paso");
    Page<Entity> all = this.service.findAllPaginated(page);
    if (all.isEmpty()) {
      throw new AppNotFoundException();
    } else {
      return ResponseEntity.ok(all);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Entity> findById(@PathVariable("id") String id) {
    log.debug("paso");
    Optional<Entity> one = this.service.findById(id);
    if (one.isEmpty()) {
      throw new AppNotFoundException();
    } else {
      return ResponseEntity.ok(one.get());
    }
  }

}
