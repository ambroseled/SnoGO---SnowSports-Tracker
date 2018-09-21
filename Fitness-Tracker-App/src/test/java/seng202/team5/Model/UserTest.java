package seng202.team5.Model;


import org.junit.Test;
import seng202.team5.DataManipulation.DataBaseController;
import java.util.Date;
import static org.junit.Assert.*;


public class UserTest {


    @Test
    public void testBmiNew() {
        Date date = new Date();
        User user = new User("Test", 12, 1.75, 70, date);
        assertEquals(22.86, user.getBmi(), 0.0);
    }


    @Test
    public void testBmiDataBase() {
        DataBaseController db = new DataBaseController();
        User user = db.getUsers().get(0);
        assertEquals(26.16, user.getBmi(), 0.0);
    }


}