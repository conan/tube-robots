package conan.rocks;

/**
 * A service that can find a significant location that's nearby to a set of co-ordinates; implementations may define
 * what "nearby" means.
 */
public interface NearbyLocationFinder {

    /**
     * Returns a location that is nearby to the given co-ordinates if one exists, or <code>null</code> if there isn't
     * one.
     *
     * @param latitude the latitude of the point around which to look for a nearby location
     * @param longitude the longitude of the point around whcih to look for a nearby location
     * @return the name of the nearby location, or <code>null</code> if there aren't any locations nearby
     */
    public String getNearbyLocation(double latitude, double longitude);
}
