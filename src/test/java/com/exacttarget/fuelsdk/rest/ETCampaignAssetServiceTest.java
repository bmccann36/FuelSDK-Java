//
// ETCampaignAssetServiceTest.java -
//
//      x
//
// Copyright (C) 2013 ExactTarget
//
// @COPYRIGHT@
//

package com.exacttarget.fuelsdk.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.exacttarget.fuelsdk.ETCampaignAssetService;
import com.exacttarget.fuelsdk.ETCampaignService;
import com.exacttarget.fuelsdk.ETClient;
import com.exacttarget.fuelsdk.ETConfiguration;
import com.exacttarget.fuelsdk.ETSdkException;
import com.exacttarget.fuelsdk.ETServiceResponse;
import com.exacttarget.fuelsdk.filter.ETComplexFilter;
import com.exacttarget.fuelsdk.filter.ETFilter;
import com.exacttarget.fuelsdk.filter.ETFilterOperators;
import com.exacttarget.fuelsdk.filter.ETSimpleFilter;
import com.exacttarget.fuelsdk.model.ETCampaign;
import com.exacttarget.fuelsdk.model.ETCampaignAsset;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ETCampaignAssetServiceTest{

	private static final String TEST_CAMPAIGN_CODE_PATCH = "TestCode_PATCH";
	private static final String TEST_CAMPAIGN_CODE = "TestCode";
	protected static Logger logger = Logger.getLogger(ETCampaignAssetServiceTest.class);
	protected static ETCampaignService campaignService;
	protected static ETCampaignAssetService assetService;
	protected static ETClient client = null;
	protected static ETConfiguration configuration = null;
	protected ETFilter filter;
	protected ETFilter filterUpdated;
	
	
	@Before
	public void setUp() throws ETSdkException {
		logger.debug("SetUp");
		configuration = new ETConfiguration("/fuelsdk-test.properties");
        client = new ETClient(configuration);
		
		campaignService = new ETCampaignServiceImpl();
		assetService = new ETCampaignAssetServiceImpl();
	}
	
	@Test
	public void ATestClean() throws ETSdkException {
		logger.debug("TestClean()");
		
		List<ETCampaign> campaigns = retrieveAllCampaigns();

		logger.debug("Received Count during clean: " + campaigns.size());
		
		for( ETCampaign c: campaigns )
		{
			logger.debug("Received during Clean: " + c);
			if( TEST_CAMPAIGN_CODE.equals(c.getCampaignCode()) || TEST_CAMPAIGN_CODE_PATCH.equals(c.getCampaignCode()))
			{
				logger.debug("Deleting during Clean: " + c);
				deleteCampaign(c);
			}
		}
	
	}
	
	@Test
	public void TestAssociateAsset() throws ETSdkException{
		logger.debug("TestAssociateAsset()");

		ETCampaign campaign = createCampaign(TEST_CAMPAIGN_CODE);
		
		ETCampaignAsset asset = new ETCampaignAsset();
		asset.setCampaignId(campaign.getId());
		asset.setItemID("321");
		asset.setType("EMAIL");
		
		//TEST begin
		ETServiceResponse<ETCampaignAsset> response = assetService.post(client, asset);
		
		Assert.assertNotNull(response);
		Assert.assertTrue(response.getStatus());
		Assert.assertNotNull(response.getResults().get(0));
		
		ETCampaignAsset returnedAsset = response.getResults().get(0);		
		String campaignID = returnedAsset.getCampaignId();
		String id = returnedAsset.getId();
		
		ETCampaignAsset responseAsset = retrieveAsset(campaignID, id);
		
		Assert.assertNotNull(responseAsset);
		
		Assert.assertEquals(campaignID, responseAsset.getCampaignId());
		//TEST end
		
		deleteCampaign(campaign);
	}
	
	@Test
	public void TestUnassociateAsset() throws ETSdkException
	{
		logger.debug("TestUnassociateAsset()");

		ETCampaign campaign = createCampaign(TEST_CAMPAIGN_CODE);
		
		ETCampaignAsset asset = new ETCampaignAsset();
		asset.setCampaignId(campaign.getId());
		asset.setItemID("321");
		asset.setType("EMAIL");
		
		ETServiceResponse<ETCampaignAsset> response = assetService.post(client, asset);
		
		Assert.assertNotNull(response);
		Assert.assertTrue(response.getStatus());
		
		ETCampaignAsset returnedAsset = response.getResults().get(0);		
		String campaignID = returnedAsset.getCampaignId();
		String id = returnedAsset.getId();
		
		ETCampaignAsset responseAsset = retrieveAsset(campaignID, id);
		
		Assert.assertNotNull(responseAsset);
		
		String responseCampaignId = responseAsset.getCampaignId();
		
		Assert.assertEquals(campaignID, responseCampaignId);

		response = null;
		//Delete
		response = assetService.delete(client, responseAsset);

		Assert.assertNotNull(response);
		Assert.assertTrue(response.getStatus());
		
		response = null;
		
		//Validate it's been deleted
		response = assetService.get(client, new ETSimpleFilter("campaignId", ETFilterOperators.EQUALS, campaignID ));

		Assert.assertNotNull(response);
		Assert.assertTrue(response.getStatus());
		Assert.assertEquals(0,response.getResults().size());
		
		deleteCampaign(campaign);
	}

	private ETCampaignAsset retrieveAsset(String campaignID, String id) throws ETSdkException {
		ETServiceResponse<ETCampaignAsset> response;
		ETComplexFilter filter = new ETComplexFilter();
		List<ETFilter> filters = new ArrayList<ETFilter>();
		filters.add(new ETSimpleFilter("campaignId", ETFilterOperators.EQUALS, campaignID ));
		filters.add(new ETSimpleFilter("id", ETFilterOperators.EQUALS, id ));
		filter.setAdditionalOperands(filters);
		
		response = assetService.get(client, filter);
		
		Assert.assertNotNull(response);
		Assert.assertTrue(response.getStatus());
		Assert.assertEquals(1,response.getResults().size());
		
		ETCampaignAsset responseAsset = response.getResults().get(0);
		return responseAsset;
	}
	
	private ETCampaign createCampaign(String campaign) throws ETSdkException 
	{
		ETCampaign etObject = new ETCampaign();
		etObject.setName("testCampaign");
		etObject.setDescription("testCampaign");
		etObject.setCampaignCode(campaign);
		etObject.setColor("000fff");
		etObject.setFavorite(false);
		
		ETServiceResponse<ETCampaign> response =  campaignService.post(client, etObject);
		Assert.assertNotNull(response);
		Assert.assertTrue(response.getStatus());
		
		return response.getResults().get(0);
	}
	
	protected void deleteCampaign(ETCampaign etObject) throws ETSdkException
	{
		ETServiceResponse<ETCampaign> response = campaignService.delete(client, etObject);
		Assert.assertNotNull(response);
		Assert.assertTrue(response.getStatus());
	}

	protected List<ETCampaign> retrieveAllCampaigns() throws ETSdkException {
		ETServiceResponse<ETCampaign> response = campaignService.get(client);
		Assert.assertNotNull(response);
		Assert.assertTrue(response.getStatus());
		Assert.assertNotNull(response.getResults());
		return response.getResults();
	}
}
