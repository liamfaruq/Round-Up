import client.helper.DateHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Liam on 25/02/2019.
 */
public class DateHelperTest {
    private DateHelper dateHelper;

    @Before
    public void init(){
        dateHelper = new DateHelper("2019-02-24");
    }

    @Test
    public void shouldReturnDateWithCorrectFormat(){
        //Given String in format "yyyy-mm-dd"
        //When Parsing
        Date endDate = dateHelper.getEndDateOfTransactions();

        //Should return correct date
        assertTrue(endDate.toString().contains("Sun Feb 24"));
    }

    @Test
    public void shouldCalculateStartDateCorrectly(){
        //Given endDate
        //When getting start date from end date
        Date startDate = dateHelper.getStartDateOfTransactions();

        //Should return start date 7 days before end date
        assertTrue(startDate.toString().contains("Sun Feb 17"));

    }

}
