package com.buensabor.restaurantapp.service;

public interface TokenBlacklistService {
	
	
    public void blacklistToken(String token);

    public boolean isBlacklisted(String token);

    public void removeBlacklistedToken(String token);
}
