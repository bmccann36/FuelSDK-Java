package com.exacttarget.fuelsdk;

public class MyETDataExtension extends ETSoapObject {

    public static <T extends ETSoapObject> ETResponse<T> retrieve(ETClient client,
                                                                  String continueRequest,
                                                                  Class<T> type)
            throws ETSdkException
    {
        return retrieve(client, "DataExtensionObject[TEST_beta_app_send_defs]", new ETFilter(), continueRequest, type);
    }
    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setId(String id) {

    }
}
