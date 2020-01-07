package com.example;

import java.util.*;



import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    /**
     * This function listens at endpoint "/api/HttpTrigger-Java". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpTrigger-Java&code={your function key}
     * 2. curl "{your host}/api/HttpTrigger-Java?name=HTTP%20Query&code={your function key}"
     * Function Key is not needed when running locally, it is used to invoke function deployed to Azure.
     * More details: https://aka.ms/functions_authorization_keys
     */
/*    @FunctionName("functionappdemo0312")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        String query = request.getQueryParameters().get("name");
        String name = request.getBody().orElse(query);

        if (name == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).body("Hello, " + name).build();
        }
    }
    */
	
    @FunctionName("LBGPoCFunctionApp")
    public void readingEventHubData(
        @EventHubTrigger(
            name = "msg",
            eventHubName = "lbgpoceventhub", 
            cardinality = Cardinality.ONE,
            //connection = "Endpoint=sb://lbgpoceventhubns.servicebus.windows.net/;SharedAccessKeyName=testpolicy;SharedAccessKey=OYDTI1Yh/HWkx3DMMUczQ/LXiZD02R8jf86/0p9RKh0=;EntityPath=lbgpoceventhub")
            connection="EventHubConnectionString")
            String str,
        @CosmosDBOutput(
            name = "databaseOutput",
            databaseName = "cosmosdb1",
            collectionName = "partyinfo1",
            //connectionStringSetting = "AccountEndpoint=lbgcosmospoc.cassandra.cosmos.azure.com;AccountKey=ommXu78m2xyJ884YOcPbXMjlr63MCFGtKpWL6KgTPWcPb7AQHi4TXBw1AsnAzfhLAQn2kmPrXtkSXf2N5AHY0Q==;")
            connectionStringSetting="CosmosDBconnectionString")
            OutputBinding<String> document,
        final ExecutionContext context) {

    
 
        context.getLogger().info("Event hub message received: " + str);
        
        PostalAddressDTO dto = new PostalAddressDTO();
    	dto.setUserid(1002);
    	dto.setName("Sumit");
    	dto.setEmail("FFFF");
        	
/*
        if (item.getPressure() > 30) {
            item.setNormalPressure(false);
        } else {
            item.setNormalPressure(true);
        }

        if (item.getTemperature() < 40) {
            item.setTemperatureStatus(status.COOL);
        } else if (item.getTemperature() > 90) {
            item.setTemperatureStatus(status.HOT);
        } else {
            item.setTemperatureStatus(status.WARM);
        }*/
    	

        //final String database = new JSONObject(dto).toString();
        
    	
    	try {
    		JSONObject eventGridMessage = new JSONObject(dto.toString());
			 eventGridMessage.put("id", java.util.UUID.randomUUID().toString());
			 document.setValue(eventGridMessage.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        //context.getLogger().info("UserID" + document.getValue().getUserid());
        //context.getLogger().info("Name" + document.getValue().getName());

        context.getLogger().info("Document " + document);

    }
 
}
