package com.exacttarget.fuelsdk;

public class Scratch {
    public static void main(String[] args) throws ETSdkException {

        ETClient client = new ETClient("fuelsdk.properties");


        ETResponse<ETDataExtension> testDe = client.retrieve(ETDataExtension.class, "key = TEST_beta_app_send_defs");
        System.out.println(testDe.getResponseCode());

        ETResponse<ETDataExtensionRow> res = testDe.getObject().select();
//        System.out.println(res);
        System.out.println(res.getTotalCount());
        System.out.println("response code");
        System.out.println(res.getResponseCode());
        System.out.println(" \n MAKING REQUEST 02 \n ");
        System.out.println(res.getRequestId());
//
        String reqId = res.getRequestId();
        ETResponse<ETDataExtensionRow> response2 = NextPageUtil.retrieve(client, reqId, ETDataExtensionRow.class);
        System.out.println("TOTAL COUNT");
        System.out.println(response2.getResults().toArray().length);
        System.out.println("response code");
        System.out.println(response2.getResponseCode());
        System.out.println(response2.getRequestId());

        System.out.println(" \n MAKING REQUEST 03 \n ");
        ETResponse<ETDataExtensionRow> response3 = NextPageUtil.retrieve(client, reqId, ETDataExtensionRow.class);
        System.out.println("TOTAL COUNT");
        System.out.println(response3.getResults().toArray().length);
        System.out.println("response code");
        System.out.println(response3.getResponseCode());


//        System.out.println("response code02");
//        System.out.println(response2.getResponseCode());
    }
}
