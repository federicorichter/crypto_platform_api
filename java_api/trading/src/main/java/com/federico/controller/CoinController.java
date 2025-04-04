package com.federico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.federico.model.Coin;
import com.federico.service.CoinService;

@RestController
@RequestMapping("/coins")
public class CoinController {
    
    @Autowired
    private CoinService coinService;
    
    private ObjectMapper objectMapper;

    ResponseEntity<List<Coin>> getCoinList(@RequestParam("page")int page) throws Exception{
        List<Coin> coins = coinService.getCoinList(page);

        return new ResponseEntity<>(coins, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{coinId}/chart")
    ResponseEntity<JsonNode> getMarketChar(
            @PathVariable String coinId,
            @RequestParam("days") int days
        ) throws Exception{

        String res = coinService.getMarketChart(coinId, days);

        JsonNode jsonNode = objectMapper.readTree(res);
        return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    ResponseEntity<JsonNode> searchCoin(
            @RequestParam("q") String keyword
        ) throws Exception{

        String coin = coinService.searchCoin(keyword);

        JsonNode jsonNode = objectMapper.readTree(coin);

        return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
    }

    @GetMapping("/top50")
    ResponseEntity<JsonNode> getTop50CoinByMarketCapRank(
            @RequestParam("q") String keyword
        ) throws Exception{

        String coin = coinService.getTop50CoinByMarketCapRank();

        JsonNode jsonNode = objectMapper.readTree(coin);

        return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
    }

    @GetMapping("/trading")
    ResponseEntity<JsonNode> getTradingCoin() throws Exception{

        String coin = coinService.getTreadingCoins();

        JsonNode jsonNode = objectMapper.readTree(coin);

        return ResponseEntity.ok(jsonNode);
    }

    @GetMapping("/details/{coinId}")
    ResponseEntity<JsonNode> getCoinDetails(
            @PathVariable String coinId
        ) throws Exception{

        String coin = coinService.searchCoin(coinId);

        JsonNode jsonNode = objectMapper.readTree(coin);

        return ResponseEntity.ok(jsonNode);
    }
}
