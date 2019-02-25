package client.helper;

import client.RestClient;
import client.StarlingRequest;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Liam on 25/02/2019.
 */
public class StarlingRequestHelper{

    private StarlingRequest request;
    private final Logger logger = LoggerFactory.getLogger(StarlingRequestHelper.class);

    public StarlingRequestHelper(StarlingRequest request){
        this.request = request;
    }


    private boolean isOutBoundAndWithinWeek(JsonNode transaction, Date endDate, Date startDate){
        Date transactionDate = DateHelper.getDateFromStrResponse(transaction.get("created").toString());
        return transactionDate.getTime() < endDate.getTime()
                && transactionDate.getTime() > startDate.getTime()
                && transaction.get("direction").toString().contains("OUTBOUND");
    }

    //default to the first AccountUID
    public String getDefaultAccountUID (String requestStr) {
        request.executeGet(requestStr);
        return request.getResponseJson().get("accounts").get(0).get("accountUid").toString().replaceAll("^\"|\"$", "");
    }

    //default to the first SavingsGoalUID
    public String getDefaultSavingsGoalUID (String requestStr) {
        request.executeGet(requestStr);
        String uid = "";
        try{
            uid=request.getResponseJson().get("savingsGoalList").get(0).get("savingsGoalUid").toString().replaceAll("^\"|\"$", "");
        }
        catch(NullPointerException ne){
            logger.error("No Savings goals created therefore setting to null, ensure to create one");
        }

        return uid;
    }

    //get newly Created savings Goal Id
    public String getNewSavingsGoalUIDFromJson(JsonNode jsonNode){
        return jsonNode.get("savingsGoalUid").toString().replaceAll("^\"|\"$", "");
    }

    public String getNewTransferId(JsonNode jsonNode){
        return jsonNode.get("transferUid").toString().replaceAll("^\"|\"$", "");
    }


    public List<Double> getTransactionRoundUpListFromResponse(Date endDate, Date startDate, JsonNode responseJson){
        List<Double> transactionRoundUpList = new ArrayList<>();
        JsonNode transactionArrJson =  responseJson.get("_embedded").get("transactions");

        for (JsonNode transaction : transactionArrJson){
            if (isOutBoundAndWithinWeek(transaction,endDate,startDate)){
                transactionRoundUpList.add(Double.parseDouble(transaction.get("amount").toString()));
            }
        }
        return transactionRoundUpList;
    }

    private void createARecurringTransfer(){
    }

}
