package client;

import client.helper.DateHelper;
import client.helper.RequestDefinition;
import client.helper.RoundUpCalcHelper;
import client.helper.StarlingRequestHelper;
import domain.RecurringTransfer;
import domain.SavingsGoal;
import domain.TransferAmount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import java.util.*;

/**
 * Created by Liam on 17/02/2019.
 */

public class RestClient {

    private final Logger logger = LoggerFactory.getLogger(RestClient.class);

    private String ACCOUNT_ID;
    private String SAVINGS_GOAL_ID;
    private String TRANSFER_ID;
    private Date endDate;
    private Date startDate;

    private double savingsGoalValue;
    private StarlingRequest request;
    private StarlingRequestHelper requestHelper;

    public RestClient(String dateStr, String accessToken){

        TRANSFER_ID="";
        savingsGoalValue = 1;       //AMOUNT_must be greater than or equal to 0.01
        request = new StarlingRequest(" Bearer " + accessToken);
        requestHelper = new StarlingRequestHelper(request);

        DateHelper dateHelper = new DateHelper(dateStr);

        endDate = dateHelper.getEndDateOfTransactions();
        startDate = dateHelper.getStartDateOfTransactions();

        ACCOUNT_ID = requestHelper.getDefaultAccountUID(RequestDefinition.getAccountsReq());
        SAVINGS_GOAL_ID=   requestHelper.getDefaultSavingsGoalUID(RequestDefinition.getSavingsGoalCreateReq(ACCOUNT_ID));
    }


//    1. GET /api/v1/transactions - Returns transactions within a period for the customer
    public void getAllTxnsAWeekFromDate(){
        logger.debug("Reading all transactions for given week: " + startDate.toString() + " to " + endDate.toString() + " under Account: " +  ACCOUNT_ID);
        request.executeGet(RequestDefinition.getTransactionsReq());
        List<Double> transactionRoundUpList = requestHelper.getTransactionRoundUpListFromResponse(endDate,startDate,request.getResponseJson());
        logger.debug("Outbound transaction amounts: " + transactionRoundUpList.toString());
        savingsGoalValue += RoundUpCalcHelper.getRoundNumber(transactionRoundUpList);

        logger.info("domain.SavingsGoal is now: " + savingsGoalValue );
    }

//    2. PUT /api/v2/account/{accountUid}/savings-goals - Create a savings goal
    public void createASavingsGoal(int goal){
        String name = "SGUID" + new Random().nextInt(100000000);

        logger.debug("Creating Savings Goal : " + name);

        SavingsGoal savingsGoal = new SavingsGoal("GBP","GBP",name,goal);
        request.executePut(RequestDefinition.getSavingsGoalCreateReq(ACCOUNT_ID),savingsGoal.toString());
        SAVINGS_GOAL_ID = requestHelper.getNewSavingsGoalUIDFromJson(request.getResponseJson());

    }

//    3. PUT /api/v2/account/{accountUid}/savings-goals/{savingsGoalUid}/add-money/{transferUid} - Transfer money into a savings goal
    public void transferToSavingsGoalToRecentSavingsGoal(){


        RecurringTransfer recurringTransfer = new RecurringTransfer("GBP",1.);

        //create transfer
        request.executePut(RequestDefinition.getRecurringTransferCreateReq(ACCOUNT_ID,SAVINGS_GOAL_ID),recurringTransfer.toString());
        TRANSFER_ID = requestHelper.getNewTransferId(request.getResponseJson());

        //transfer the money
        TransferAmount transferAmount = new TransferAmount("GBP",savingsGoalValue);
        request.executePut(RequestDefinition.getTransferToSavingsGoalReq(ACCOUNT_ID,SAVINGS_GOAL_ID,TRANSFER_ID),transferAmount.toString());

        logger.debug("Transferred " + savingsGoalValue + " to savingsGoal: " + SAVINGS_GOAL_ID + " under transfer id: " + TRANSFER_ID);
    }

    public HttpStatus getResponse(){
        return request.getHttpStatus();
    }

    public String getACCOUNT_ID() {
        return ACCOUNT_ID;
    }

    public String getSAVINGS_GOAL_ID() {
        return SAVINGS_GOAL_ID;
    }

    public String getTRANSFER_ID() {
        return TRANSFER_ID;
    }

    public double getSavingsGoalValue() {
        return savingsGoalValue;
    }

}
