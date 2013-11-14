package conan.rocks;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Robot implements Runnable {

    public static final int MAX_POINTS = 10;

    private TrafficMonitor trafficMonitor;
    private NearbyLocationFinder nearbyLocationFinder;

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
            reportTrafficInformation(currentLocation, trafficMonitor.getTrafficConditions(nearbyTubeStation));
        }
    }

    private void reportTrafficInformation(Location location, TrafficConditions conditions) {
        // TODO format the traffic conditions and print them out
    }

    /**
     * Adds a Collection of no more than {@link #MAX_POINTS} points (represented by {@link Location Point2D.Location}
     * instances) to this robot's queue, blocking until space has been made in the queue for all the elements in
     * <code>points</code>.  This method is <code>synchronized</code> to ensure that no interleaving of points can occur
     * if multiple threads are attempting to dispatch data to this robot simultaneously; if you are concerned about this
     * happening and need to guarantee a longer route, then you'll need a bigger robot.
     *
     * @param points the locations the robot should visit; note that if a particular route between the points is
     *               desired, this should be an ordered Collection of some sort.
     * @throws InterruptedException if the robot is interrupted before all the <code>points</code> have been added to
     *                              its queue
     */
    public synchronized void addPoints(Collection<Location> points) throws InterruptedException {
        if (points == null || points.size() > MAX_POINTS) {
            throw new IllegalArgumentException("Robot " + name + " can cope with up to " + MAX_POINTS
                    + " points at a time, but was given: " + points);
        }

        for (Location point : points) {
            queue.put(point);
        }
    }

    /**
     * Signals the robot to shutdown at the next available opportunity.
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
