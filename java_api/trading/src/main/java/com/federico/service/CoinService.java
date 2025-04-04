package com.federico.service;

import java.util.List;

import com.federico.model.Coin;

public interface CoinService {

    List<Coin> getCoinList(int page) throws Exception;

    String getMarketChart(String coinId, int days) throws Exception;

    String getCoinDetails(String coinId) throws Exception;

    Coin findById(String id) throws Exception;

    String searchCoin(String keyword) throws Exception;

    String getTop50CoinByMarketCapRank() throws Exception;

    String getTreadingCoins() throws Exception;
    
}
