package com.connectme.fwk.config.cache;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.DefaultKeyGenerator;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig implements CachingConfigurer {
	
	@Bean(destroyMethod = "shutdown")
	public net.sf.ehcache.CacheManager ehCacheManager() {
		CacheConfiguration cacheConfiguration = new CacheConfiguration();
		cacheConfiguration.setName("interestifycache");
		cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
		cacheConfiguration.setMaxEntriesLocalHeap(1);
		cacheConfiguration.setTimeToLiveSeconds(2*24*60*60); // 2 days
		cacheConfiguration.setEternal(true);
		
		PersistenceConfiguration persistenceConfiguration = new PersistenceConfiguration();
		persistenceConfiguration.strategy(Strategy.LOCALTEMPSWAP);
		cacheConfiguration.addPersistence(persistenceConfiguration);
		
		net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
		config.addCache(cacheConfiguration);
		DiskStoreConfiguration diskStoreConfiguration = new DiskStoreConfiguration();
		//diskStoreConfiguration.setPath("d:/tmp/int/");
		
		config.addDiskStore(diskStoreConfiguration);
		 

		return net.sf.ehcache.CacheManager.newInstance(config);
	}

	@Bean
	@Override
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheManager());
	}

	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new DefaultKeyGenerator();
	}
}