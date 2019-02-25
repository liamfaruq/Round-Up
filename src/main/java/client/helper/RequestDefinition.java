package client.helper;

/**
 * Created by Liam on 25/02/2019.
 */
public class RequestDefinition {

    private final static String URL = "https://api-sandbox.starlingbank.com";
    private final static String transactionsReq = URL + "/api/v1/transactions";
    private final static String accountsReq = URL + "/api/v2/accounts/";


    private final static String accountReq = URL + "/api/v2/account/";

    public static String getTransactionsReq() {
        return transactionsReq;
    }

    public static String getAccountsReq() {
        return accountsReq;
    }

    public static String getSavingsGoalCreateReq(String accountId) {
        return accountReq + accountId + "/savings-goals";
    }

    // /api/v2/account/{accountUid}/savings-goals/{savingsGoalUid}/recurring-transfer
    public static String getRecurringTransferCreateReq(String accountId,String savingsGoalId) {
        return getSavingsGoalCreateReq(accountId) + "/" + savingsGoalId + "/recurring-transfer";
    }

    /////api/v2/account/{accountUid}/savings-goals/{savingsGoalUid}/add-money/{transferUid} - Transfer money into a savings goal
    public static String getTransferToSavingsGoalReq(String accountId,String savingsGoalId, String transferId) {
        return getSavingsGoalCreateReq(accountId) + "/" + savingsGoalId + "/add-money/" + transferId;
    }

}
