package snmaddula.cassandra.app.accessor;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

import snmaddula.cassandra.app.entity.Product;

/**
 * 
 * @author snmaddula
 *
 */
@Accessor
public interface ProductAccessor {

	@Query("SELECT * FROM products")
	public Result<Product> findAll();

	@Query("SELECT * FROM products LIMIT ?")
	public Result<Product> findAllN(int limit);

	@Query("SELECT * FROM products WHERE id = ?")
	public Result<Product> findById(Long id);
}
