package domain;

import client.helper.DateHelper;

/**
 * Created by Liam on 25/02/2019.
 */
public class RecurringTransfer {

    private String startDate;
    private Integer frequency;

    private String amountCurrency;
    private Double amount;

    public RecurringTransfer(String amountCurrency, Double amount){
        this.startDate = DateHelper.getTodaysDateStr();
        this.frequency =0;
        this.amountCurrency=amountCurrency;
        this.amount=amount;
    }

    public String toString(){
        return "{\n" +
                "  \"recurrenceRule\": {\n" +
                "  \t\"startDate\": \"" + startDate + "\",\n" +
                "  \t\"frequency\" : " + frequency + "\n" +
                "  },\n" +
                "  \n" +
                "  \"amount\": {\n" +
                "    \"currency\": \"" + amountCurrency+ "\",\n" +
                "    \"minorUnits\": " + amount+"\n" +
                "  }\n" +
                "}";
    }

}
