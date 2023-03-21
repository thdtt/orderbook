package org.example.model;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class OrderBook {

    /**
     * @param bid_offers
     * Map danh sách các lệnh mua
     * Được khai báo với CTDL TreeMap implement SortedMap và là cây cân bằng
     */
    private Map<Double, Integer> bidOffers = new TreeMap<>(Comparator.reverseOrder());

    /**
     * @param ask_offers
     * Map danh sách các lệnh bán
     * Được khai báo với CTDL TreeMap implement SortedMap và là cây cân bằng
     */
    private Map<Double, Integer> askOffers = new TreeMap<>(Comparator.naturalOrder());

    public Map<Double, Integer> getBidOffers() {
        return this.bidOffers;
    }

    public Map<Double, Integer> getAskOffers() {
        return this.askOffers;
    }

    public int getAskLevel() {
        return askOffers.size();
    }

    public int getBidLevel() {
        return bidOffers.size();
    }

    public int getBidQuantity(double bestPrice) {
        int bidQuantity = 0;
        for (double price : bidOffers.keySet()) {
            if (price > bestPrice) {
                bidQuantity += bidOffers.get(price);
            }
        }

        return bidQuantity;
    }

    public int getBidQuantity() {
        return getBidQuantity(Integer.MIN_VALUE);
    }

    public int getAskQuantity() {
        return getAskQuantity(Integer.MAX_VALUE);
    }

    public int getAskQuantity(double bestPrice) {
        int askQuantity = 0;
        for (double price : askOffers.keySet()) {
            if (price < bestPrice) {
                askQuantity += askOffers.get(price);
            }
        }
        return askQuantity;
    }

}
