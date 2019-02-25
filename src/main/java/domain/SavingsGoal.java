package domain;

import java.io.Serializable;

/**
 * Created by Liam on 24/02/2019.
 */
public class SavingsGoal implements Serializable{

//    {
//        "currency" : "GBP",
//            "name" : "savingsGoal2",
//            "target": {
//        "currency": "GBP",
//                "minorUnits": 11223344
//    }
//    }

    private Target target;
    private String currency;
    private String name;

    public SavingsGoal(String currency, String targetCurrency, String name, Integer targetMinorUnits){
        this.target = new Target(targetCurrency,targetMinorUnits);
        this.currency=currency;
        this.name=name;
    }

    public SavingsGoal(){
        this.currency="GBP";
        this.name="hellloooO!";
        this.target = new Target();
    }

    public void setCurrency(String currency){
        this.currency=currency;
    }

    public void setName(String name){
        this.name=name;
    }

    public String toString(){
        return "{\n" +
                "\t\"currency\" : \"" + this.currency + "\",\n" +
                "\t\"name\" : \"" + this.name + "\",\n" +
                "  \"target\": {\n" +
                "    \"currency\": \"" + this.target.getCurrency() + "\",\n" +
                "    \"minorUnits\":" + this.target.getMinorUnits() + "\n" +
                "  }\n" +
                "}";
    }

    private String getCurrency(){
        return this.currency;
    }

    private String getName(){
        return this.name;
    }



    private class Target {
        public String currency;
        public Integer minorUnits;

        public Target(){
            this.currency="GBP";
            this.minorUnits=500;
        }

        public Target(String currency,Integer minorUnits){
            this.currency = currency;
            this.minorUnits = minorUnits;
        }

        public void setCurrency(String currency){
            this.currency= currency;
        }

        public void setMinorUnits(Integer minorUnits){
            this.minorUnits=minorUnits;
        }

        public String getCurrency (){
            return this.currency;
        }

        public Integer getMinorUnits(){
            return this.minorUnits;
        }
    }
}
