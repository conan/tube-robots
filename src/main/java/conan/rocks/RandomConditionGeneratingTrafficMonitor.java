package conan.rocks;

import java.util.Random;

public class RandomConditionGeneratingTrafficMonitor implements TrafficMonitor {

    private Random random = new Random();

    @Override
    public TrafficConditions getTrafficConditions(String station) {
        return TrafficConditions.values()[random.nextInt(3)];
    }
}
