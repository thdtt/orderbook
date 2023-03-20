package org.example.business;

import org.example.model.BestBidOffer;
import org.example.model.OrderBook;
import org.example.model.Side;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Trade {
    public static void trade(Side side, OrderBook order, double price, int quantity) {
        if (side == Side.ASK) {
            Set<Double> ask_prices = order.getAskOffers().keySet();
            List<Double> ask_prices_list = new ArrayList<>(ask_prices);
            for (double ask_price : ask_prices_list) {
                if (quantity > 0 && price >= ask_price) {
                    int ask_quantity = order.getAskOffers().get(ask_price);
                    if (quantity >= ask_quantity) {
                        quantity = quantity - ask_quantity;
                        removeAskOrder(ask_price, ask_quantity, order);
                    } else {
                        removeAskOrder(ask_price, quantity, order);
                        quantity = 0;
                    }
                    if (quantity == 0) {
                        break;
                    }
                }
            }
            if (quantity > 0) {
                addBidRestingOrder(price, quantity, order);
            }
        } else {
            Set<Double> bid_prices = order.getBidOffers().keySet();
            List<Double> bid_prices_list = new ArrayList<>(bid_prices);
            for (double bid_price : bid_prices_list) {
                if (quantity > 0 && price <= bid_price) {
                    int bid_quantity = order.getBidOffers().get(bid_price);
                    if (quantity >= bid_quantity) {
                        quantity = quantity - bid_quantity;
                        removeBidOrder(bid_price, bid_quantity, order);
                    } else {
                        removeBidOrder(bid_price, quantity, order);
                        quantity = 0;
                    }
                    if (quantity == 0) {
                        break;
                    }
                }

            }
            if (quantity > 0) {
                addAskRestingOffer(price, quantity, order);
            }
        }
    }

    synchronized static void removeAskOrder(double price, int quantity, OrderBook order) {
        int lastQuantity = order.getAskOffers().get(price);
        if (lastQuantity == quantity) {
            order.getAskOffers().remove(price);
        } else {
            order.getAskOffers().put(price, lastQuantity - quantity);
        }
    }

    synchronized static void addAskRestingOffer(double price, int quantity, OrderBook orderBook) {
        orderBook.getAskOffers().put(price, quantity);
    }

    synchronized static void removeBidOrder(double price, int quantity, OrderBook orderBook) {
        int lastQuantity = orderBook.getBidOffers().get(price);
        if (lastQuantity == quantity) {
            orderBook.getBidOffers().remove(price);
        } else {
            orderBook.getBidOffers().put(price, lastQuantity - quantity);
        }
    }

    synchronized static void addBidRestingOrder(double price, int quantity, OrderBook orderBook) {
        orderBook.getBidOffers().put(price, quantity);
    }

    public static void reset(OrderBook orderBook) {
        System.out.println("size ask = " + orderBook.getAskOffers().size());
        System.out.println("size bid = " + orderBook.getBidOffers().size());
        orderBook.getAskOffers().clear();
        orderBook.getBidOffers().clear();
    }

    public static BestBidOffer getBBO(OrderBook orderBook) {
        BestBidOffer bbo = new BestBidOffer();
        double bid_price = 0.0;
        double bid_quantity = 0.0;
        double ask_price = 0.0;
        double ask_quantity = 0.0;
        for (double price : orderBook.getBidOffers().keySet()) {
            bbo.bid_price = price;
            bbo.bid_quantity = orderBook.getBidOffers().get(price);
            break;
        }
        for (double price : orderBook.getAskOffers().keySet()) {
            bbo.ask_price = price;
            bbo.ask_quantity = orderBook.getAskOffers().get(price);
            break;
        }
        return bbo;
    }

}
