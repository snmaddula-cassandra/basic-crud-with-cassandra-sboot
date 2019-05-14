package snmaddula.cassandra.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;

/**
 * 
 * @author snmaddula
 *
 */
@Configuration
public class CassandraContext {

	private String datacenter;
	private String address;
	private String username;
	private String password;
	private String keyspace;
	private Integer port;
	
	@Bean
	public Session cassandraSession() {
		Cluster cluster = Cluster.builder().addContactPoint(address)
				.withPort(port)
				.withLoadBalancingPolicy(new TokenAwarePolicy(DCAwareRoundRobinPolicy.builder().withLocalDc(datacenter).build()))
				.withCredentials(username, password).build();
		return cluster.connect(keyspace);
	}
}
