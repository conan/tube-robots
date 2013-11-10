package conan.rocks;

public interface TrafficMonitor {

    /**
     * Get the current traffic conditions at a particular tube station.
     *
     * @param station the station to get traffic conditions for
     * @return the current {@link TrafficConditions} at the specified <code>station</code>
     */
    public TrafficConditions getTrafficConditions(String station);
}
