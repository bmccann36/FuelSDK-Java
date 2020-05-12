package com.exacttarget.fuelsdk;

public class NextPageUtil extends ETSoapObject {

    // ? don't even need this
//    public static <T extends ETSoapObject> ETResponse<T> getNextBatch(ETClient client, String continueRequest, String deName, Class<T> type) throws ETSdkException {
//        return ETSoapObject.retrieve(client, "DataExtensionObject[" + deName + "]", new ETFilter(), continueRequest, type);
//    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setId(String id) {

    }
}
