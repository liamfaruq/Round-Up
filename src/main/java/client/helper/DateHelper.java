package client.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Liam on 25/02/2019.
 */
public class DateHelper {

    private Date endDate;
    private static SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
    private final static Logger logger = LoggerFactory.getLogger(DateHelper.class);

    public DateHelper(String endDateStr){
        try {
            endDate=simpleDateFormat.parse(endDateStr);
            logger.info("End Date is: " + endDate.toString());
        } catch (ParseException e) {
            logger.error("please ensure to enter the date in the format of yyyy-mm-dd");
            e.printStackTrace();
        }
    }

    public static String getTodaysDateStr() {
        Date todaysDate = new Date();
        logger.info("Todays Date is: " + todaysDate.toString());
        return simpleDateFormat.format(todaysDate);
    }

    public Date getEndDateOfTransactions(){
        return endDate;
    }

    public Date getStartDateOfTransactions(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DATE , -7);
        Date startDate= calendar.getTime();

        logger.info("Start Date is: " + startDate.toString());
        return startDate;
    }

    public static Date getDateFromStrResponse(String dateStr){
        Date dateParsed = null;
        try {
            dateParsed=simpleDateFormat.parse(dateStr.replaceAll("T.*","").replace("\"",""));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateParsed;
    }
}
