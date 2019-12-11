package com.gft.practices.reactive.webcontroller.async;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static reactor.core.publisher.Mono.when;

import com.gft.practices.reactive.beans.dto.Entity;
import com.gft.practices.reactive.model.services.EntityService;
import com.gft.practices.reactive.webcontroller.resources.EntityResource;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EntityResource.class)
@Slf4j
public class EntityResourceTests {

  @Autowired
  private WebTestClient webTestClient;

  @InjectMocks
  private EntityService entityService;

  @Test
  void findById() {
    String id = "789e5138-ad70-4913-bfb6-fdc50cb3746e";
    Entity entity1 = Entity.builder().id("1").code("1asdf").description("test1").build();
    when(entityService.findById("/rest/api/v1/entity/" + id)).thenReturn(Mono.just(entity1));
    webTestClient.get()
        .uri("/rest/api/v1/entity/" + id)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Entity.class)
        .value(Entity::getId, equalTo(id));
  }

  @Test
  void findAll() {
    webTestClient.get()
        .uri("/rest/api/v1/entity/all")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(List.class)
        .value(List::size, greaterThan(0));
  }

  @Test
  void findAllPaginated() {
    webTestClient.get()
        .uri("/rest/api/v1/entity/?page=1&size=5")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Map.class)
        .value(map -> map.get("size"), is(5));
  }

}
