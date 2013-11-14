package conan.rocks;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A robot that travels around bending time and space looking for nearby locations.  This class is not thread-safe, but
 * it would be easy to synchronize the addLocations() method.
 */
public class Robot implements Runnable {

    public static final int MAX_POINTS = 10;

    private TrafficMonitor trafficMonitor;
    private NearbyLocationFinder nearbyLocationFinder;
    private TrafficInformationReporter reporter;

    private BlockingQueue<Location> queue = new LinkedBlockingQueue<>(MAX_POINTS);
    private String name;

    private boolean shutDownReceived = false;
    private Location currentLocation;

    public Robot(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while (!shutDownReceived) {
                moveAndReport();
            }
            System.out.println(name + " received a SHUTDOWN command and is gracefully shutting down");
        } catch (InterruptedException e) {
            System.out.println(name + " was rudely interrupted and is shutting down in a huff");
        }
    }

    /**
     * Updates this robot's current location and reports on nearby traffic conditions if necessary.
     *
     * @throws InterruptedException if interrupted whilst waiting for a new location
     */
    private void moveAndReport() throws InterruptedException {
        currentLocation = queue.take();
        String nearbyTubeStation =
                nearbyLocationFinder.getNearbyLocation(currentLocation.getLatitude(), currentLocation.getLongitude());

        if(nearbyTubeStation != null) {
            reporter.report(currentLocation, trafficMonitor.getTrafficConditions(nearbyTubeStation));
        }
    }

    /**
     * Adds a Collection of no more than {@link #MAX_POINTS} {@link Location}s to this robot's queue, blocking until
     * space has been made in the queue for all the elements in <code>locations</code>.
     *
     * @param locations the locations the robot should visit; note that if a particular route between the locations is
     *               desired, this should be an ordered Collection of some sort.
     * @throws InterruptedException if the robot is interrupted before all the <code>locations</code> have been added to
     *                              its queue
     */
    public synchronized void addLocations(Collection<Location> locations) throws InterruptedException {
        if (locations == null || locations.size() > MAX_POINTS) {
            throw new IllegalArgumentException("Robot " + name + " can cope with up to " + MAX_POINTS
                    + " locations at a time, but was given: " + locations);
        }

        for (Location location : locations) {
            queue.put(location);
        }
    }

    /**
     * Signals the robot to shutdown at the next available opportunity.  No further locations will be processed beyond
     * the current one.
     */
    public void shutDown() {
        shutDownReceived = true;
    }

    public void setTrafficMonitor(TrafficMonitor trafficMonitor) {
        this.trafficMonitor = trafficMonitor;
    }

    public void setNearbyLocationFinder(NearbyLocationFinder nearbyLocationFinder) {
        this.nearbyLocationFinder = nearbyLocationFinder;
    }
}
