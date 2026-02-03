package com.buensabor.restaurantapp.service.impl;

import java.util.Set;
import java.util.HashSet;

import org.springframework.stereotype.Service;

import com.buensabor.restaurantapp.service.TokenBlacklistService;

@Service
public class TokenBlacklistServiceImpl implements TokenBlacklistService{

    private Set<String> blacklistedTokens = new HashSet<>();

	@Override
	public void blacklistToken(String token) {
		// TODO Auto-generated method stub
		blacklistedTokens.add(token);
	}

	@Override
	public boolean isBlacklisted(String token) {
		// TODO Auto-generated method stub
		return blacklistedTokens.contains(token);
    }

	@Override
	public void removeBlacklistedToken(String token) {
		// TODO Auto-generated method stub
		blacklistedTokens.remove(token);
    }

}
