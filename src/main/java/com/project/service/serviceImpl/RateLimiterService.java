package com.project.service.serviceImpl;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    private final Map<String, Bucket> buckets =
            new ConcurrentHashMap<>();

    public Bucket resolveBucket(String ip) {
        return buckets.computeIfAbsent(ip, k -> {
            Bandwidth limit = Bandwidth.builder()
                    .capacity(5)
                    .refillGreedy(5, Duration.ofMinutes(1))
                    .build();
            return Bucket.builder()
                    .addLimit(limit)
                    .build();
        });
    }
}
