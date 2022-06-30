package com.germeny.binance;

public class Common {
    private String binanceUrl = "https://api.binance.com/api/v3/ticker/24hr?symbol=BTCBUSD";

    private static Common instance = new Common();

    public static Common getInstance()
    {
        return instance;
    }

    public String getBinanceUrl() {
        return binanceUrl;
    }
}
