import client.RestClient;

/**
 * Created by Liam on 17/02/2019.
 */
public class RoundUpMain {

    //Access Token
    //GSqSW88bD03avDLKul9slPDlSUVx47gpXfegSUsqgSTw3soeyhNYaPktRxZ4RzlJ

    //args

    public static void main (String args[]){

        // Return all transactions within the week before given date
        //PARAM 1: dateStr in form yyyy-MM-dd
        //PARAM 2: Access Token
        RestClient restClient = new RestClient("2019-02-25","i5SobgTKbAeahnGxovCUw9mSVC38VEnj4XsStiTkVnsxB7cK87G4faZ1AkGnUCWu");
        restClient.getAllTxnsAWeekFromDate();

        //PARAM 3: enter the Savings Goal amount
        restClient.createASavingsGoal(500);
        restClient.transferToSavingsGoalToRecentSavingsGoal();

    }


}
