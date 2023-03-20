import org.example.business.Trade;
import org.example.model.OrderBook;
import org.example.model.Side;
import org.example.utils.RandomDouble;
import org.example.utils.RandomInt;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class StressTest {

    static RandomDouble randomDouble = new RandomDouble();
    static RandomInt randomInt = new RandomInt();

    static {
        randomDouble.initialize();
        randomInt.initialize();
    }

    @Test
    public void stress() {
        OrderBook engine = new OrderBook();
        long n1 = System.currentTimeMillis();
        int ITERATIONS = 10000000;
        System.out.println("Running for " + ITERATIONS + " iterations.");
        for (int i = 0; i < ITERATIONS; i++) {
            if (i % 100000 == 0) {
                System.out.println(i + " orders sent");
            }

            if (randomDouble.nextDouble() > 50) {
//                long start = System.nanoTime();
                double price = randomDouble.nextDouble();
                int qty = randomInt.nextInt();
                Trade.trade(Side.ASK, engine, price, qty);
//                long end = System.nanoTime();
//                System.out.println("ASK" + "\t\t\t" + formatCol(formatSignificant((end-start)/1000d, 5), price + "", qty + ""));
            } else {
//                long start = System.nanoTime();
                double price = randomDouble.nextDouble();
                int qty = randomInt.nextInt();
                Trade.trade(Side.BID, engine, price, qty);
//                long end = System.nanoTime();
//                System.out.println("BID" + "\t\t\t" + formatCol(formatSignificant((end-start)/1000d, 5), price + "", qty + ""));
            }

        }

        long elapsedTimeMillis = System.currentTimeMillis() - n1;
        long elapsedTimeMicros = elapsedTimeMillis * 1000;

        System.out.println(((double) elapsedTimeMicros) / ITERATIONS + " us on average.");
        System.out.println("Best bid offer: \n" + Trade.getBBO(engine).toString());
        Trade.reset(engine);

    }

    public static String formatCol(String... s) {
        return String.format( "%7sus \t\t\t %7s \t\t\t %7s", s);
    }

    public static String formatSignificant(double value, int significant)
    {
        MathContext mathContext = new MathContext(significant, RoundingMode.DOWN);
        BigDecimal bigDecimal = new BigDecimal(value, mathContext);
        return bigDecimal.toPlainString();
    }

}
