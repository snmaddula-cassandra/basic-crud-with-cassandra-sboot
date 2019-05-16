package snmaddula.cassandra.app.repo;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

import snmaddula.cassandra.app.accessor.ProductAccessor;
import snmaddula.cassandra.app.entity.Product;

/**
 * 
 * @author snmaddula
 *
 */
@Repository
public class ProductRepo {

	@Autowired
	private MappingManager mappingManager;

	@Autowired
	private Session cassandraSession;

	private Mapper<Product> mapper;
	private ProductAccessor accessor;

	@PostConstruct
	public void init() {
		mapper = mappingManager.mapper(Product.class);
		accessor = mappingManager.createAccessor(ProductAccessor.class);
	}

	public void save(Product product) {
		mapper.save(product);
	}

	public void save(List<Product> products) {
		final BatchStatement batchSaveProducts = new BatchStatement();
		products.forEach(product -> batchSaveProducts.add(mapper.saveQuery(product)));
		cassandraSession.execute(batchSaveProducts);
	}

	public List<Product> findAll() {
		return accessor.findAll().all();
	}

	public List<Product> findAllN(int limit) {
		return accessor.findAllN(limit).all();
	}

	public Product findById(Long id) {
		Result<Product> productResult = accessor.findById(id);
		if(productResult != null && !productResult.isExhausted())
			return productResult.one();
		return null;
	}

	public void deleteById(Long id) {
		mapper.delete(accessor.findById(id));
	}

	public void deleteAllInBatch() {
		cassandraSession.executeAsync("delete from products");
	}

}
