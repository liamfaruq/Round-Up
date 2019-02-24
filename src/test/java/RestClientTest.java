import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;

/**
 * Created by Liam on 17/02/2019.
 */
public class RestClientTest {

    private RestClient restClient;

    @Before
    public void init(){
        restClient = new RestClient("2019-02-19","fFGZMzpcnbSLv44wfsizlu1We0ov3Xuxj9n8w65Wogyhy788a2KsXDDJhSj0ANij");
    }

    @Test
    public void shouldRequestSuccesfullyForGetAllTxnsAWeekFromDate(){
        try {
            restClient.getAllTxnsAWeekFromDate();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldRequestSuccesfullyForCreateASavingsGoal(){

        restClient.createASavingsGoal();

    }

    @Test
    public void shouldRequestSuccesfullyForTransferToSavingsGoal(){
        restClient.transferToSavingsGoal();
    }



}
