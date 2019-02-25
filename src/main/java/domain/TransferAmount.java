package domain;

/**
 * Created by Liam on 25/02/2019.
 */
public class TransferAmount {

    private String currency;
    private Double amount;

    public TransferAmount(String currency, Double amount){
        this.currency=currency;
        this.amount=amount;
    }

    public String toString(){
        return "{\n" +
                "  \"amount\": {\n" +
                "    \"currency\": \"" + currency + "\",\n" +
                "    \"minorUnits\": " + amount + "\n" +
                "  }\n" +
                "}";
    }
}
