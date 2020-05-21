package com.exacttarget.fuelsdk;

import com.exacttarget.fuelsdk.*;

public class CustomDe extends ETSoapObject {

    public static <T extends ETSoapObject> ETResponse<ETDataExtensionRow> continueRetrieve(ETClient client, String continueRequest) throws ETSdkException {
        return ETSoapObject.retrieve(client, continueRequest, ETDataExtensionRow.class);
    }

//    retrieve(ETClient client,
//             String continueRequest,
//             Class<T> type)

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setId(String id) {

    }
}