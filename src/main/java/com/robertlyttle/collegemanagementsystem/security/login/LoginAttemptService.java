package com.robertlyttle.collegemanagementsystem.security.login;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private final LoadingCache<String, Integer> loginAttemptCache;

    public LoginAttemptService() {
        super();
        loginAttemptCache = CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES)
                .maximumSize(100).build(
                        new CacheLoader<String, Integer>() {
                            @Override
                            public Integer load(String s) throws Exception {
                                return 0;
                            }
                        }
                );
    }

    public void removeUserFromCache(String username) {
        loginAttemptCache.invalidate(username);
    }

    public void addUserToCache(String username) throws ExecutionException {
        int loginAttempts = loginAttemptCache.get(username) + 1;
        loginAttemptCache.put(username, loginAttempts);
    }

    public boolean hasExceededMaxAttempts(String username) throws ExecutionException {
        return loginAttemptCache.get(username) > 5;
    }

}
