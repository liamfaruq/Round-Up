import client.helper.RoundUpCalcHelper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by Liam on 17/02/2019.
 */
public class RoundUpCalcTest {

    @Test
    public void shouldRoundNumberUpToTwoDP(){
        //Given an example of more than 2 decimal places below 0.005
        List<Double> listOfSpending = new ArrayList<>();
        listOfSpending.add(5.753);

        //When rounding
        double roundNum = RoundUpCalcHelper.getRoundNumber(listOfSpending);

        //Then should round up
        assertThat(roundNum, equalTo(0.25));
    }

    @Test
    public void shouldRoundNubmerDownToTwoDP(){
        //Given an example of more than 2 decimal places above 0.005
        List<Double> listOfSpending = new ArrayList<>();
        listOfSpending.add(5.757);

        //When rounding
        double roundNum = RoundUpCalcHelper.getRoundNumber(listOfSpending);

        //Then should round down
        assertThat(roundNum, equalTo(0.24));
    }

    @Test
    public void shouldRoundNumberFromGivenExample(){
        //Given an example of spendings £4.35, £5.20 and £0.87
        List<Double> listOfSpending = new ArrayList<>();
        listOfSpending.add(4.35);
        listOfSpending.add(5.20);
        listOfSpending.add(0.87);

        //When rounding
        double roundNum = RoundUpCalcHelper.getRoundNumber(listOfSpending);

        //Then should return correct value
        assertThat(roundNum, equalTo(1.58));
    }

    @Test
    public void shouldRoundNumberFromGivenNegativeExample(){
        //Given an example of spendings £4.35, £5.20 and £0.87
        List<Double> listOfSpending = new ArrayList<>();
        listOfSpending.add(-4.35);
        listOfSpending.add(-5.20);
        listOfSpending.add(-0.87);

        //When rounding
        double roundNum = RoundUpCalcHelper.getRoundNumber(listOfSpending);

        //Then should return correct value
        assertThat(roundNum, equalTo(1.58));
    }

    @Test
    public void shouldShowZeroForZeroSpending() {
        //Given an example of spendings of all zero
        List<Double> listOfSpending = new ArrayList<>();
        listOfSpending.add(0.);
        listOfSpending.add(0.);
        listOfSpending.add(0.);

        //When rounding
        double roundNum = RoundUpCalcHelper.getRoundNumber(listOfSpending);

        //Then
        assertThat(roundNum, equalTo(0.0));

    }





}
