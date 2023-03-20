package org.example.model;

import com.google.gson.Gson;

public class BestBidOffer {

    public double bid_price = 0.0;
    public double bid_quantity = 0.0;
    public double ask_price = 0.0;
    public double ask_quantity = 0.0;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
