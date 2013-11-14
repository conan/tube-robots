package conan.rocks;

/**
 * A service that is capable of outputting the prevailing traffic conditions at a particular location in some way.
 */
public interface TrafficInformationReporter {

    /**
     * Reports the traffic <code>conditions</code> at a particular <code>location</code>.
     *
     * @param location the {@link Location} at which to report
     * @param conditions the prevailing {@link TrafficConditions} to be reported
     */
    public void report(Location location, TrafficConditions conditions);
}
