package com.gft.practices.reactive.webcontroller.resources;

import com.gft.practices.reactive.beans.dto.Entity;
import com.gft.practices.reactive.model.services.EntityService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;


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
  public DeferredResult<ResponseEntity<List<Entity>>> findAll() {
    log.debug("paso");
    return findAllWhenCompleteAsync(service.findAll());
  }

  @GetMapping
  public DeferredResult<ResponseEntity<Page<Entity>>> findAllPaginated(Pageable page) {
    log.debug("paso");
    return findAllPaginatedWhenCompleteAsync(service.findAllPaginated(page));
  }

  @GetMapping("/{id}")
  public DeferredResult<ResponseEntity<Entity>> findById(@PathVariable("id") String id) {
    log.debug("paso");
    return findByIdWhenCompleteAsync(service.findById(id));
  }

}
