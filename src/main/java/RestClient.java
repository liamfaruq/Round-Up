import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.xml.ws.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

/**
 * Created by Liam on 17/02/2019.
 */

public class RestClient {

    private final static String URL = "https://api-sandbox.starlingbank.com";
    private final Logger logger = LoggerFactory.getLogger(RestClient.class);

    private String REQUEST_TRANSACTIONS = URL + "/api/v1/transactions";
    private String REQUEST_ACCOUNTS = URL + "/api/v2/accounts";

    private String REQUEST_ACCOUNT = URL + "/api/v2/account/";

    private String REQUEST_SAVINGS_GOAL_CREATE;
    private String REQUEST_TRANSFER;
    private String ACCOUNT_ID;
    private String ACCESS_TOKEN;

    private String SAVINGS_GOAL_ID;
    private String TRANSFER_ID;

    private Date endDate;
    private Date startDate;

    private RoundUpCalc roundUpCalc;

    private ResponseEntity<String> responseEntity;
    private RestTemplate restTemplate;

    private double savingsGoalValue;
    private ObjectMapper objectMapper;
    private JsonNode responseJson;
    HttpEntity<String> entity;



    public RestClient(String dateStr, String accessToken){

        objectMapper = new ObjectMapper();
        restTemplate = new RestTemplate();
        entity = new HttpEntity<String>(getHeaders());


        SAVINGS_GOAL_ID= "";
        TRANSFER_ID="";
        ACCESS_TOKEN=" Bearer " + accessToken;
        savingsGoalValue = 0;

        REQUEST_SAVINGS_GOAL_CREATE = URL + REQUEST_ACCOUNT + ACCOUNT_ID + "/savings-goals/";
        REQUEST_TRANSFER = REQUEST_SAVINGS_GOAL_CREATE + SAVINGS_GOAL_ID + "/add-money/" + TRANSFER_ID;

        //ACCOUNT_ID = getAccountUID();



        try {
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-mm-dd");
            endDate=simpleDateFormat.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endDate);
            calendar.add(Calendar.DATE , -7);
            startDate= calendar.getTime();
        } catch (ParseException e) {
            logger.error("please ensure to enter the date in the format of yyyy-mm-dd");
            e.printStackTrace();
        }
    }


    //default to the first AccountUID
    private String getAccountUID () {
        executeRequest(REQUEST_ACCOUNTS,HttpMethod.GET);
        return responseJson.get("accounts").get(0).get("accountUid").toString();
    }

    private Date getDateFromStr(String dateStr){
        Date dateParsed = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd"); //"2019-02-17T18:54:30.509Z"

        try {
            dateParsed=simpleDateFormat.parse(dateStr.replaceAll("T.*","").replace("\"",""));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateParsed;
    }

    private boolean isOutBoundAndWithinWeek(JsonNode transaction){
        Date transactionDate = getDateFromStr(transaction.get("created").toString());
        return transactionDate.getTime() < endDate.getTime()
                && transactionDate.getTime() > startDate.getTime()
                && transaction.get("direction").equals("OUTBOUND");
    }

    private List<Double> getTransactionRoundUpListFromResponse(){

        List<Double> transactionRoundUpList = new ArrayList<>();
            JsonNode transactionArrJson =  responseJson.get("_embedded").get("transactions");

            for (JsonNode transaction : transactionArrJson){
                if (isOutBoundAndWithinWeek(transaction)){
                    transactionRoundUpList.add(Double.parseDouble(transaction.get("amount").toString()));
                }
            }

        return transactionRoundUpList;
    }



    private MultiValueMap<String, String> getHeaders(){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Accept", "application/json");
        headers.add("Authorization", ACCESS_TOKEN);
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

        return headers;
    }


    private void executeRequest(String requestStr, HttpMethod httpMethod){
        try {
            URI uri = new URI(requestStr);
            logger.info("invoking request: " + requestStr);
            ResponseEntity<String> response = restTemplate.exchange(uri, httpMethod, entity, String.class);
            responseJson = objectMapper.readTree(response.getBody());
            logger.info("returning: " + response.getBody());

        } catch (URISyntaxException e) {
            logger.error("Ensure to enter a valid token");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    1. GET /api/v1/transactions - Returns transactions within a period for the customer
    public void getAllTxnsAWeekFromDate() throws URISyntaxException {

        logger.debug("Reading all transactions for given week: " + startDate.toString() + " to " + endDate.toString() + " under Account: " +  ACCOUNT_ID);
        executeRequest(REQUEST_TRANSACTIONS,HttpMethod.GET);

        List<Double> transactionRoundUpList = getTransactionRoundUpListFromResponse();

        savingsGoalValue += RoundUpCalc.getRoundNumber(transactionRoundUpList);

        logger.info("SavingsGoal is now: " + savingsGoalValue );

    }

//    2. PUT /api/v2/account/{accountUid}/savings-goals - Create a savings goal
    public void createASavingsGoal(){
        logger.debug("Creating Savings Goal: " + SAVINGS_GOAL_ID.hashCode());
        logger.info("invoking: " + REQUEST_SAVINGS_GOAL_CREATE);
        restTemplate.put(REQUEST_SAVINGS_GOAL_CREATE, String.class);
        //restTemplate.getForEntity("", String.class);
    }

//    3. PUT /api/v2/account/{accountUid}/savings-goals/{savingsGoalUid}/add-money/{transferUid} - Transfer money into a savings goal
    public void transferToSavingsGoal(){
        logger.debug("Transfering " + "<amount>" + " to savingsGoal: " + SAVINGS_GOAL_ID + " under transfer id: " + TRANSFER_ID);
        //restTemplate.put(REQUEST_TRANSFER, String.class);
        restTemplate.getForEntity("", String.class);
    }

    public HttpStatus getHttpStatus(){
        return responseEntity.getStatusCode();
    }


}
