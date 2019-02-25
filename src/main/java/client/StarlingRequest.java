package client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Liam on 25/02/2019.
 */
public class StarlingRequest {

    private final Logger logger = LoggerFactory.getLogger(StarlingRequest.class);
    private String ACCESS_TOKEN;

    private ResponseEntity<String> responseEntity;
    private HttpEntity<String> entity;
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper;
    private JsonNode responseJson;

    public StarlingRequest (String accesstkn){
        restTemplate = new RestTemplate();
        this.ACCESS_TOKEN=accesstkn;
        this.objectMapper = new ObjectMapper();
        this.entity = new HttpEntity<String>(getHeaders());
    }

    private MultiValueMap<String, String> getHeaders(){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Accept", "application/json");
        headers.add("Content-Type","application/json");
        headers.add("Authorization", ACCESS_TOKEN);
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        return headers;
    }



    public void executePut(String requestStr,String requestBody){
        try {
            URI uri = new URI(requestStr);
            logger.info("invoking request: " + requestStr);

            MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
            jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            restTemplate.getMessageConverters().add(jsonHttpMessageConverter);

            HttpEntity<String> entity = new HttpEntity<String>(requestBody, getHeaders());

            logger.info("Request Body being put: " + entity.getBody());
            responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);

            responseJson = objectMapper.readTree(responseEntity.getBody());
            logger.info("request returning: " + responseEntity.getBody());

        } catch (URISyntaxException e) {
            logger.error("Ensure to enter a valid token");
            e.printStackTrace();
        } catch(HttpClientErrorException hte ){
            logger.error("request returning: " + hte.getResponseBodyAsString());
            hte.printStackTrace();
            throw hte;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void executeGet(String requestStr){
        try {
            URI uri = new URI(requestStr);
            logger.info("invoking request: " + requestStr);

            responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

            responseJson = objectMapper.readTree(responseEntity.getBody());
            logger.info("returning: " + responseEntity.getBody());

        } catch (URISyntaxException e) {
            logger.error("Ensure to enter a valid token");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HttpStatus getHttpStatus(){
        return responseEntity.getStatusCode();
    }

    public JsonNode getResponseJson(){
        return responseJson;
    }
}
