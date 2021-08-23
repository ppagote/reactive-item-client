package reactivespring.itemclient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactivespring.itemclient.domain.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class ItemClientController {

    WebClient webClient = WebClient.create("http://localhost:8080");

    @GetMapping(value = "/client/retrieve")
    public Flux<Item> getAllItemsUsingRetrieve() {

        return webClient.get()
                .uri("/v1/items")
                .retrieve()
                .bodyToFlux(Item.class)
                .log("Items in client project");
    }

    @GetMapping(value = "/client/exchange")
    public Flux<Item> getAllItemsUsingExchange() {

        return webClient.get()
                .uri("/v1/items")
                .exchangeToFlux(clientResponse ->
                    clientResponse.bodyToFlux(Item.class)
                )
                .log("Items in client project");
    }

    @GetMapping(value = "/client/retrieve/singleItem/{id}")
    public Mono<Item> getOneItemUsingRetrieve(@PathVariable String id) {

        return webClient.get()
                .uri("/v1/items/{id}", id)
                .retrieve()
                .bodyToMono(Item.class)
                .log("One Item in client project");
    }

    @GetMapping(value = "/client/exchange/singleItem/{id}")
    public Mono<Item> getOneItemUsingExchange(@PathVariable String id) {

        return webClient.get()
                .uri("/v1/items/{id}", id)
                .exchangeToMono(clientResponse ->
                        clientResponse.bodyToMono(Item.class)
                )
                .log("One Item in client project");
    }

    @PostMapping(value = "/client/retrieve/addItem")
    public Mono<Item> addOneItemUsingRetrieve(@RequestBody Item item) {

        return webClient.post()
                .uri("/v1/items")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(item), Item.class)
                .retrieve()
                .bodyToMono(Item.class)
                .log("Item created in client project");
    }

    @PostMapping(value = "/client/exchange/addItem")
    public Mono<Item> addOneItemUsingExchange(@RequestBody Item item) {

        return webClient.post()
                .uri("/v1/items")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(item), Item.class)
                .exchangeToMono(clientResponse ->
                        clientResponse.bodyToMono(Item.class)
                )
                .log("Item created in client project");
    }

    @PutMapping(value = "/client/retrieve/updateItem/{id}")
    public Mono<Item> updateOneItemUsingRetrieve(@RequestBody Item item, @PathVariable String id) {

        return webClient.put()
                .uri("/v1/items/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(item), Item.class)
                .retrieve()
                .bodyToMono(Item.class)
                .log("Item updated in client project");
    }

    @PutMapping(value = "/client/exchange/updateItem/{id}")
    public Mono<Item> updateOneItemUsingExchange(@RequestBody Item item, @PathVariable String id) {

        return webClient.put()
                .uri("/v1/items/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(item), Item.class)
                .exchangeToMono(clientResponse ->
                        clientResponse.bodyToMono(Item.class)
                )
                .log("Item updated in client project");
    }

    @DeleteMapping(value = "/client/exchange/deleteItem/{id}")
    public Mono<Void> updateOneItemUsingExchange(@PathVariable String id) {

        return webClient.delete()
                .uri("/v1/items/{id}", id)
                .exchangeToMono(clientResponse ->
                        clientResponse.bodyToMono(Void.class)
                )
                .log("Item deleted in client project");
    }

    @DeleteMapping(value = "/client/retrieve/deleteItem/{id}")
    public Mono<Void> updateOneItemUsingRetrieve(@PathVariable String id) {

        return webClient.delete()
                .uri("/v1/items/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .log("Item deleted in client project");
    }

}
