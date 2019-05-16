package snmaddula.cassandra.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import snmaddula.cassandra.app.entity.Product;
import snmaddula.cassandra.app.repo.ProductRepo;

/**
 * 	
 * @author snmaddula
 *
 */
@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepo productRepo;

  public List<Product> fetchAll() {
    return productRepo.findAll();
  }

  public Product fetchById(Long id) {
    return productRepo.findById(id);
  }

  public void create(Product product) {
    productRepo.save(product);
  }

  public void remove(Long id) {
    productRepo.deleteById(id);
  }

  public void removeAll() {
    productRepo.deleteAllInBatch();
  }

}