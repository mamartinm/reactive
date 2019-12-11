package com.gft.practices.reactive.model.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.practices.reactive.beans.dto.Entity;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class EntityService extends BaseService<Entity> {

  // @formatter:off
  private static final TypeReference<List<Entity>> TO_VALUE_TYPE_REF = new TypeReference<>() {  };
  // @formatter:on
  private static final String RESULT = "result";
  private static final String RESULTS = "results";

  private ObjectMapper objectMapper;

  @Value("${app.urls.data}")
  private String urlData;

  @Autowired
  public EntityService(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public Flux<Entity> findAll() {
    log.debug("Inicio y llamo a "+urlData);
    var resultWebClient = WebClient.create(urlData).get().retrieve().bodyToMono(Map.class);
    return resultWebClient.map(o -> {
      Object mapResult = ((Map) o.get(RESULT)).get(RESULTS);
      return objectMapper.convertValue(mapResult, TO_VALUE_TYPE_REF);
    }).flatMapMany(Flux::fromIterable);
  }

  public Mono<Page<Entity>> findAllPaginated(Pageable pageable) {
    log.debug("Inicio");
    return findAll()
        .collectList()
        .flatMap(tuple -> {
          Page<Entity> pagination = getPagination(pageable, tuple);
          return Mono.just(pagination);
        });
  }

  public Mono<Entity> findById(String id) {
    log.debug("Inicio");
    return findAll()
        .filter(entity -> entity.getId().equals(id))
        .next();
  }

}
