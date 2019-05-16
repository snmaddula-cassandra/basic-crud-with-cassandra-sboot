package snmaddula.cassandra.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import snmaddula.cassandra.app.entity.Product;
import snmaddula.cassandra.app.service.ProductService;

/**
 * 
 * @author snmaddula
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public List<Product> getAll() {
    return productService.fetchAll();
  }

  @GetMapping("{id}")
  public Product getById(@PathVariable Long id) {
    return productService.fetchById(id);
  }

  @PostMapping
  public void create(@RequestBody Product product) {
    productService.create(product);
  }

  @DeleteMapping("{id}")
  public void removeById(@PathVariable Long id) {
    productService.remove(id);
  }

  @DeleteMapping
  public void removeAll() {
    productService.removeAll();
  }

}
