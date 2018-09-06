package seng202.team5.Model;


import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DataAnalyserTest {

    private static ArrayList<Activity> activities;
    private DataAnalyser dataAnalyser = new DataAnalyser();


    /**
     * Setup before all tests. Reads the test activities from the
     * dataAnalysisTests file into the arrayList of activities, activities.
     */
    @BeforeClass
    public static void beforeAll() {
        InputDataParser parser = new InputDataParser();
        activities = parser.parseCSVToActivities("TestFiles/dataAnalysisTests.csv");
    }


    /**
     * Testing the markActive() and checkInactive() functions. This test cases
     * passes an activity where all DataPoints are inactive.
     */
    @Test
    public void testAllInactive() {
        Activity activity = activities.get(0);
        dataAnalyser.analyseActivity(activity);
        // Getting the dataSet out of the activity
        DataSet dataSet = activity.getDataSet();
        // Getting the data points out of the dataSet
        ArrayList<DataPoint> dataPoints = dataSet.getDataPoints();
        int count = 0;
        for (DataPoint point : dataPoints) {
            if (!point.isActive()) {
                count++;
            }
        }
        assertEquals(dataPoints.size(), count);
    }


    /**
     * Testing the markActive() and checkInactive() functions. This test cases
     * passes an activity where all DataPoints are active.
     */
    @Test
    public void testAllActive() {
        Activity activity = activities.get(1);
        dataAnalyser.analyseActivity(activity);
        // Getting the dataSet out of the activity
        DataSet dataSet = activity.getDataSet();
        // Getting the data points out of the dataSet
        ArrayList<DataPoint> dataPoints = dataSet.getDataPoints();
        int count = 0;
        for (DataPoint point : dataPoints) {
            if (point.isActive()) {
                count++;
            }
        }
        assertEquals(dataPoints.size(), count);
    }


    /**
     * Testing the calcAvgHeart() function. This test cases passes
     * an activity where the heart rate remains constant.
     */
    @Test
    public void testAvgHeartAllSame() {
        Activity activity = activities.get(2);
        dataAnalyser.analyseActivity(activity);
        // Getting the dataSet out of the activity
        DataSet dataSet = activity.getDataSet();
        assertEquals(132, dataSet.getAvgHeartRate(), 0.0);
    }


    /**
     * Testing the calcAvgHeart() function. This test cases passes
     * an activity where the heart rate varies.
     */
    @Test
    public void testAvgHeartDiff() {
        Activity activity = activities.get(1);
        dataAnalyser.analyseActivity(activity);
        // Getting the dataSet out of the activity
        double[] rates = {132, 156, 154, 151, 146, 139, 141, 149, 154, 149, 146, 142, 138};
        double average = 0;
        for (int i = 0; i < rates.length; i++){
            average += rates[i];
        }
        average = average / rates.length;
        DataSet dataSet = activity.getDataSet();
        assertEquals(average, dataSet.getAvgHeartRate(), 0.5);
    }


    /**
     * Testing the calcVertical() function. This test case passes
     * an activity where the user is moving upwards the whole time
     * and is therefore inactive and the vertical distance is not recorded.
     */
    @Test
    public void testVerticalUp() {
        Activity activity = activities.get(0);
        dataAnalyser.analyseActivity(activity);
        // Getting the dataSet out of the activity
        DataSet dataSet = activity.getDataSet();
        assertEquals(0, dataSet.getVerticalDistance(), 0);
    }


    /**
     * Testing the calcVertical() function. This test case passes
     * an activity where the user is moving downwards the whole time
     * and is therefore active and the vertical distance calculated.
     */
    @Test
    public void testVerticalDown() {
        Activity activity = activities.get(1);
        dataAnalyser.analyseActivity(activity);
        // Getting the dataSet out of the activity
        DataSet dataSet = activity.getDataSet();
        double vertical = DataAnalyser.roundNum(1802.69 - 1792.66);
        assertEquals(vertical, dataSet.getVerticalDistance(), 0);
    }


    /**
     * Testing te speed calculating functions of the DataAnalyser class.
     * This is done by checking that the correct top speed is found over an
     * activity.
     */
    @Test
    public void testSpeed() {
        Activity activity = activities.get(1);
        dataAnalyser.analyseActivity(activity);
        // Getting the dataSet out of the activity
        DataSet dataSet = activity.getDataSet();
        assertEquals(2.0, dataSet.getTopSpeed(), 0.0);
    }


    /**
     * Testing the calcBMI() function.
     */
    @Test
    public void testBMI() {
        double height = 1.75;
        double weight = 70;
        double bmi = dataAnalyser.calcBMI(height, weight);
        assertEquals(22.86, bmi, 0.0);
    }

}