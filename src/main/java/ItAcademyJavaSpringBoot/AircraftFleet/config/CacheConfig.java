package ItAcademyJavaSpringBoot.AircraftFleet.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public com.github.benmanes.caffeine.cache.Cache<Object,Object> caffeineCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)  // Expira en 10 min
                .maximumSize(100)  // Máximo 100 elementos
                .build();
    }
}
