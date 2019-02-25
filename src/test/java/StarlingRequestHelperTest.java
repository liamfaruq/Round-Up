import client.StarlingRequest;
import client.helper.DateHelper;
import client.helper.StarlingRequestHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
/**
 * Created by Liam on 25/02/2019.
 */
public class StarlingRequestHelperTest {
    private StarlingRequestHelper starlingRequestHelper;
    private StarlingRequest starlingRequest;
    private final static String transactionJsonStr ="{\n" +
            "  \"_links\": {\n" +
            "    \"nextPage\": {\n" +
            "      \"href\": \"NOT_YET_IMPLEMENTED\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"_embedded\": {\n" +
            "    \"transactions\": [\n" +
            "      {\n" +
            "        \"_links\": {\n" +
            "          \"detail\": {\n" +
            "            \"href\": \"api/v1/transactions/6c742596-b068-4a5f-bc01-0ce1bdaba046\",\n" +
            "            \"templated\": false\n" +
            "          }\n" +
            "        },\n" +
            "        \"id\": \"6c742596-b068-4a5f-bc01-0ce1bdaba046\",\n" +
            "        \"currency\": \"GBP\",\n" +
            "        \"amount\": -2.51,\n" +
            "        \"direction\": \"OUTBOUND\",\n" +
            "        \"created\": \"2019-02-25T20:36:03.531Z\",\n" +
            "        \"narrative\": \"SGUID99113401\",\n" +
            "        \"source\": \"INTERNAL_TRANSFER\",\n" +
            "        \"balance\": 2238.51\n" +
            "      },\n" +
            "      {\n" +
            "        \"_links\": {\n" +
            "          \"detail\": {\n" +
            "            \"href\": \"api/v1/transactions/6c742596-b068-4a5f-bc01-0ce1bdaba046\",\n" +
            "            \"templated\": false\n" +
            "          }\n" +
            "        },\n" +
            "        \"id\": \"6c742596-b068-4a5f-bc01-0ce1bdaba046\",\n" +
            "        \"currency\": \"GBP\",\n" +
            "        \"amount\": -2.31,\n" +
            "        \"direction\": \"OUTBOUND\",\n" +
            "        \"created\": \"2019-02-25T20:36:03.531Z\",\n" +
            "        \"narrative\": \"SGUID99113401\",\n" +
            "        \"source\": \"INTERNAL_TRANSFER\",\n" +
            "        \"balance\": 2238.51\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";


    @Before
    public void init(){
        starlingRequest = new StarlingRequest("");
        starlingRequestHelper = new StarlingRequestHelper(starlingRequest);
    }


    @Test
    public void shouldParseAllTheTransactionAmountsCorrectly() throws IOException {
        //Given an end date and start date
        DateHelper dateHelper = new DateHelper("2019-02-27");
        Date endDate = dateHelper.getEndDateOfTransactions();
        Date startDate = dateHelper.getStartDateOfTransactions();

        //When given a JsonNode from transactionJsonStr
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNodeTransactions = objectMapper.readTree(transactionJsonStr);
        List<Double> txns = starlingRequestHelper.getTransactionRoundUpListFromResponse(endDate,startDate,jsonNodeTransactions);

        //Then should return 2 transactions in transactionList
        assertThat(txns.size(),equalTo(2));
    }

}
