import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Liam on 17/02/2019.
 */
public class RoundUpCalc {


    public static double getRoundNumber(List<Double> transactionRoundUpList){
        double acc = 0;
        double accRound = 0;
        DecimalFormat df2 = new DecimalFormat(".##");

        for (Double amount : transactionRoundUpList){
            accRound+=Math.ceil(amount);
            acc+=amount;
        }
        df2.setRoundingMode(RoundingMode.HALF_EVEN);

        double roundNumber = Double.parseDouble(df2.format(accRound-acc));

        return(roundNumber);
    }



}
