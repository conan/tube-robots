package conan.rocks;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

public class RandomConditionGeneratingTrafficMonitorTest {

    private RandomConditionGeneratingTrafficMonitor monitor;

    @Before
    public void createMonitor() {
        monitor = new RandomConditionGeneratingTrafficMonitor();
    }

    /**
     * Generates 1000 random traffic conditions and tests that all the possible values are included at least once.  It's
     * possible that one of them will be missing, but it's so unlikely as not to matter.
     */
    @Test
    public void trafficMonitorShouldReturnRandomValues() {
        List<TrafficConditions> conditions = new ArrayList<>();
        for(int i=0; i<1000; i++) {
            conditions.add(monitor.getTrafficConditions("test"));
        }
        assertThat(conditions, hasItems(TrafficConditions.values()));
    }
}
