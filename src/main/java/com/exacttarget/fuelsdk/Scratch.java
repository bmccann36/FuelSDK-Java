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

        String reqId = res.getRequestId();
        // guess you don't even need the DE name ¯\_(ツ)_/¯
        ETResponse<ETDataExtensionRow> response2 = NextPageUtil.retrieve(client, reqId, ETDataExtensionRow.class);
        System.out.println(response2);

//        System.out.println("response code02");
//        System.out.println(response2.getResponseCode());
    }
}
