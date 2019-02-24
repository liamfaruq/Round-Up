/**
 * Created by Liam on 17/02/2019.
 */
public class RoundUpMain {

    //Access Token
    //GSqSW88bD03avDLKul9slPDlSUVx47gpXfegSUsqgSTw3soeyhNYaPktRxZ4RzlJ


//    To make this work, the key API endpoints you will need from our public API are:
//    1. GET /api/v1/transactions - Returns transactions within a period for the customer
//    2. PUT /api/v2/account/{accountUid}/savings-goals - Create a savings goal
//    3. PUT /api/v2/account/{accountUid}/savings-goals/{savingsGoalUid}/add-money/{transferUid} - Transfer money into a savings goal

    public static void main (String args[]){

        // Return all transactions within the week before given date
        RestClient restClient = new RestClient("","");

        // Create a savings goal
        restClient.createASavingsGoal();

        //Transfer money into a savings goal
        restClient.transferToSavingsGoal();




    }


}
