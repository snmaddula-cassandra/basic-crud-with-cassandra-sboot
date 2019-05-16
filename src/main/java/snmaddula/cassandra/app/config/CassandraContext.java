package snmaddula.cassandra.app.config;

import static org.springframework.util.StringUtils.hasText;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import static com.datastax.driver.core.ConsistencyLevel.*;
import com.datastax.driver.core.QueryOptions;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import com.datastax.driver.mapping.MappingManager;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author snmaddula
 *
 */
@Slf4j
@Setter
@Configuration
@ConfigurationProperties("cassandra")
public class CassandraContext {

	private String datacenter;
	private String[] contactPoints;
	private String username;
	private String password;
	private String keyspace;
	private Integer port;
	
	
	@Bean
	public Session cassandraSession() {
		Builder builder = Cluster.builder()
				.withoutJMXReporting() // SOLVES: java.lang.ClassNotFoundException: com.codahale.metrics.JmxReporter
				.addContactPoints(contactPoints).withPort(port);
		
		if(!hasText(username) || !hasText(password)){
			log.warn("Credentials not supplied: Connecting without credentials");
		} else {
			builder.withCredentials(username, password);
		}
		
		if(hasText(datacenter)){
			builder.withLoadBalancingPolicy(new TokenAwarePolicy(DCAwareRoundRobinPolicy.builder().withLocalDc(datacenter).build()));
		}
		
		builder.withQueryOptions(new QueryOptions() {{
			setConsistencyLevel(LOCAL_QUORUM);
		}});
		
		return builder.build().connect(keyspace);
	}
	
	@Bean
	public MappingManager mappingManager(Session session) {
		return new MappingManager(session);
	}
}
