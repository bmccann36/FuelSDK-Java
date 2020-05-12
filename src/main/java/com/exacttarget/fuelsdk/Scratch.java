package com.exacttarget.fuelsdk;

public class Scratch {
    public static void main(String[] args) throws ETSdkException {
        ETConfiguration configuration = new ETConfiguration();

        configuration.set("clientId", System.getProperty("ET_CLIENT_ID"));
        configuration.set("clientSecret", System.getProperty("ET_CLIENT_SECRET"));
        configuration.set("soapEndpoint", System.getProperty("SOAP_ENDPOINT"));
        ETClient client = new ETClient(configuration);


        ETResponse<ETDataExtension> testDe = client.retrieve(ETDataExtension.class, "key = TEST_beta_app_send_defs");

        ETResponse<ETDataExtensionRow> res = testDe.getObject().select();
//        System.out.println(res);
//        System.out.println(res.getTotalCount());
        System.out.println("response code");
        System.out.println(res.getResponseCode());
//        System.out.println(reqId);
        String reqId = res.getRequestId();
        ETResponse<ETDataExtensionRow> response2 = MyETDataExtension.retrieve(client, reqId, ETDataExtensionRow.class);
        System.out.println("response code02");
        System.out.println(response2.getResponseCode());
    }
}
