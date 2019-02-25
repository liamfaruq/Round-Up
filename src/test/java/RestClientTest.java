import client.RestClient;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.net.URISyntaxException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
/**
 * Created by Liam on 17/02/2019.
 */
public class RestClientTest {

    private RestClient restClient;

    @Before
    public void init(){
        restClient = new RestClient("2019-02-19","iPjJzLk7pnrtdGdfDqF3D7bzE9FG3LaYNYNrtDa4xpzRZFJnfxkkCE2hk9j5Gt5r");
    }

    @Test
    public void shouldRequestSuccesfullyForGetAllTxnsAWeekFromDate(){

        //When getting all transactions from date
        restClient.getAllTxnsAWeekFromDate();

        //Then response is ok
        assertThat(restClient.getResponse(),equalTo(HttpStatus.OK));
    }

    @Test
    public void shouldRequestSuccesfullyForCreateASavingsGoal(){

        //When creating savings goal with £500
        restClient.createASavingsGoal(500);

        //Then response is ok
        assertThat(restClient.getResponse(),equalTo(HttpStatus.OK));

    }

    @Test
    public void shouldRequestSuccesfullyForTransferToSavingsGoal(){

        //When transferring savings goal ( round-up accumulation defaulted to 0)
        restClient.transferToSavingsGoalToRecentSavingsGoal();

        //Then response is ok
        assertThat(restClient.getResponse(),equalTo(HttpStatus.OK));
    }

    @Test
    public void shouldCreateSavingsGoalAndTransferAllTxnsAWeekFromDate(){

        //Given an example client corresponding to new user token
        // Sarah Connor, customer id : 1a484bb7-837b-4178-b055-0b44e0f83d0f
        restClient = new RestClient("2019-02-25","i5SobgTKbAeahnGxovCUw9mSVC38VEnj4XsStiTkVnsxB7cK87G4faZ1AkGnUCWu");

        //When rounding all transactions within week and transferring to new savings goal
        restClient.getAllTxnsAWeekFromDate();
        restClient.createASavingsGoal(500);
        restClient.transferToSavingsGoalToRecentSavingsGoal();

        //Then account id, savings goal id, transfer id are not null
        assertThat(restClient.getACCOUNT_ID(), Matchers.notNullValue());
        assertThat(restClient.getSAVINGS_GOAL_ID(), Matchers.notNullValue());
        assertThat(restClient.getTRANSFER_ID(), Matchers.notNullValue());

        //Then savings Goal amount > 1
        assertThat(restClient.getSavingsGoalValue(), Matchers.greaterThan(1.));
    }



}
