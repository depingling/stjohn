package com.cleanwise.service.api.util;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;

import com.espendwise.ocean.testng.EswPipeDataProvider;
import com.espendwise.ocean.testng.EswPipeDataProviderSource;

import java.util.Locale;


public class UtilityTest {
	@BeforeMethod
	public void setUp() throws Exception {
		
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		
	}
	
	@EswPipeDataProviderSource(name="Utility.goodIsStreetAddress.txt")
	@Test(dataProvider = "EswPipeDP", dataProviderClass = EswPipeDataProvider.class)
	public void testIsStreetAddress(String address) {
		assert Utility.isStreetAddress(address) : address+" Should be evaluated as a street address.";
	}
	@EswPipeDataProviderSource(name="Utility.badIsStreetAddress.txt")
	@Test(dataProvider = "EswPipeDP", dataProviderClass = EswPipeDataProvider.class)
	public void testNotIsStreetAddress(String address) {
		assert !Utility.isStreetAddress(address) : address+" Should NOT be evaluated as a street address.";
	}
	
	@Test
	public void testParseLocaleCodeV2() {
		String localeCd = "en_USXX";
		Locale locale = Utility.parseLocaleCodeV2(localeCd);
		AssertJUnit.assertEquals("Test: "+localeCd+" Country for non-standard USXX does not match", "USXX", locale.getCountry());
		AssertJUnit.assertEquals("Test: "+localeCd+" language for en does not match", "en", locale.getLanguage());
		
		//sunny day
		localeCd= "en_US";
		locale = Utility.parseLocaleCodeV2(localeCd);
		AssertJUnit.assertEquals("Test: "+localeCd+" Country for US does not match", "US", locale.getCountry());
		AssertJUnit.assertEquals("Test: "+localeCd+" language for en does not match", "en", locale.getLanguage());
		
		
		//no language
		localeCd= "US";
		locale = Utility.parseLocaleCodeV2(localeCd);
		AssertJUnit.assertEquals("Test: "+localeCd+" Country for US does not match", "US", locale.getCountry());
		AssertJUnit.assertEquals("Test: "+localeCd+" language for empty language does not match", "", locale.getLanguage());
		
		//no country
		localeCd= "en";
		locale = Utility.parseLocaleCodeV2(localeCd);
		AssertJUnit.assertEquals("Test: "+localeCd+" Country for empty country does not match", "", locale.getCountry());
		AssertJUnit.assertEquals("Test: "+localeCd+" language for en does not match", "en", locale.getLanguage());
		
		localeCd= "GarBageLANGUAGe";
		locale = Utility.parseLocaleCodeV2(localeCd);
		AssertJUnit.assertEquals("Test: "+localeCd+" Country for empty country does not match", "", locale.getCountry());
		//note lower casing of returned lang code!
		AssertJUnit.assertEquals("Test: "+localeCd+" language for garBageString does not match", "garbagelanguage", locale.getLanguage());
		
		localeCd= "GARBAGECOUNTRY";
		locale = Utility.parseLocaleCodeV2(localeCd);
		AssertJUnit.assertEquals("Test: "+localeCd+" Country for GARBAGECOUNTRY country does not match", "GARBAGECOUNTRY", locale.getCountry());
		AssertJUnit.assertEquals("Test: "+localeCd+" language for empty language does not match", "", locale.getLanguage());
		
		//extra information
		localeCd= "en_US_extrastuff";
		locale = Utility.parseLocaleCodeV2(localeCd);
		AssertJUnit.assertEquals("Test: "+localeCd+" Country for US does not match", "US", locale.getCountry());
		AssertJUnit.assertEquals("Test: "+localeCd+" language for en does not match", "en", locale.getLanguage());
		
		//empty locale code
		localeCd= "";
		locale = Utility.parseLocaleCodeV2(localeCd);
		AssertJUnit.assertEquals("Test: "+localeCd+" Country was not empty", "", locale.getCountry());
		AssertJUnit.assertEquals("Test: "+localeCd+" language was not empty", "", locale.getLanguage());
	}
}
