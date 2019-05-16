package snmaddula.cassandra.app.entity;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import lombok.Data;

/**
 * 
 * @author snmaddula
 *
 */
@Data
@Table(name = "products")
public class Product {

	@PartitionKey(0)
	private Long id;
	
	@ClusteringColumn(0)
	private String title;
	
	private String description;
	private Double price;

}
