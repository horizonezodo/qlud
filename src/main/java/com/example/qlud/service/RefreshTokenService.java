package com.example.qlud.service;

import com.example.qlud.exception.TokenRefreshException;
import com.example.qlud.model.RefreshToken;
import com.example.qlud.repo.RefreshTokenRepo;
import com.example.qlud.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    RefreshTokenRepo repo;

    @Value("${jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;


    @Autowired
    private UserRepo userRepo;

    public Optional<RefreshToken> findByToken(String token) {
        return repo.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepo.findById(userId).get());
        refreshToken.setExpiredDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = repo.save(refreshToken);
        //log.info("create refresh token success : " );
        return refreshToken;
    }

    public RefreshToken updateRefreshToken(String token){
        Optional<RefreshToken> opt = repo.findByToken(token);
        if(opt.isPresent()){
            RefreshToken refreshToken = opt.get();
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken = repo.save(refreshToken);
            //log.info("update refresh token success : " );
            return refreshToken;
        }
        return null;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiredDate().compareTo(Instant.now()) < 0) {
            repo.delete(token);
            throw new TokenRefreshException(token.getToken(), "821");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        //log.info("delete refresh token logout success : " );
        return repo.deleteAllByUser(userRepo.findById(userId).get());
    }
}
