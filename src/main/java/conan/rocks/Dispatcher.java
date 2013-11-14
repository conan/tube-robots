package conan.rocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dispatcher {

    private NearbyLocationFinder nearbyLocationFinder;
    private TrafficMonitor trafficMonitor;

    private ExecutorService executor = Executors.newFixedThreadPool(2);

    public void run(String... names) {

        // TODO read CSV files
        // TODO create list of Location objects to dispatch
        // TODO create list of tube station locations
        // TODO make location finder and traffic monitor objects and inject them into robots

        Collection<Robot> robots = new ArrayList<>();

        for (String name : names) {
            Robot robot = new Robot(name);
            robot.setNearbyLocationFinder(nearbyLocationFinder);
            robot.setTrafficMonitor(trafficMonitor);
            robots.add(robot);
        }




    }
}
