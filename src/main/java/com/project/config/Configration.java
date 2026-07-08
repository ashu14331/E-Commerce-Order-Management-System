package com.project.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Configration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate =  new RedisTemplate<>();
        GenericJackson2JsonRedisSerializer jsonSerializer = GenericJackson2JsonRedisSerializer.builder()
                .build();
        //Connection factory
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //key serialiser
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // Value serialiser
        redisTemplate.setValueSerializer(jsonSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jsonSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

//    @Bean
//    public Bucket bucket() {
//        return Bucket.builder()
//                .addLimit(
//                        Bandwidth.builder()
//                                .capacity(5)
//                                .refillGreedy(5, Duration.ofMinutes(1))
//                                .build()
//                )
//                .build();
//    }

}
