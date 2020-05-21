package com.mercedes.fuelsdk;

import com.exacttarget.fuelsdk.*;



public class DeRowPaginator {
    ETClient client = new ETClient();

    public DeRowPaginator() throws ETSdkException {
    }

    public void getRows(String deFilterExp) throws ETSdkException {
        // get first batch
        ETResponse<ETDataExtension> testDe = client.retrieve(ETDataExtension.class, deFilterExp);
        String reqId;
        boolean moreDataToFetchBool = false;
        int batchesFetched = 1;

        ETResponse<ETDataExtensionRow> res = testDe.getObject().select();
        System.out.println("first batch retrieved \n");
        // while more batches exist get next batch
        moreDataToFetchBool = this.moreDataToFetch(res);

        reqId = res.getRequestId();

        while(moreDataToFetchBool){
            // get another batch
            System.out.println("retrieving another data batch \n");
            ETResponse<ETDataExtensionRow> batch = this.retrieveNextBatch(reqId);
            moreDataToFetchBool = this.moreDataToFetch(batch);
            batchesFetched++;
            System.out.println("batches fetched: " + batchesFetched);
        }

    }

    public ETResponse<ETDataExtensionRow> retrieveNextBatch( String reqId) throws ETSdkException {
        return CustomDe.continueRetrieve(this.client, reqId);
    }

    public boolean moreDataToFetch(ETResponse<ETDataExtensionRow> etResponse){
        if(etResponse.getResponseCode().matches("MoreDataAvailable")){
            System.out.println("more data is available");
            return true;
        }
        System.out.println("all data has been fetched");
        return false;
    }
}

















