package com.mpesapush.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpesapush.model.Config;

@Repository
	public interface ConfigRepo extends JpaRepository<Config,Long > {
		
		
	@Query(
			  value = "SELECT c.id, c.tenant,c.initiator_name,c.security_credential,c.command_id,c.shortcode,c.queue_timeout_url,c.resultURL,c.occassion,c.remarks,c.appkey,c.appsecret from b2c_config c WHERE c.tenant = :tenant",
			  nativeQuery = true)
	Config findByTenant(@Param("tenant") String tenant);
	

}
