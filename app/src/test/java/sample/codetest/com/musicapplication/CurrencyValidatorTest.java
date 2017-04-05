package sample.codetest.com.musicapplication;

import org.junit.Before;
import org.junit.Test;

import sample.codetest.com.musicapplication.utils.UtilHelper;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Megha on 22-03-2017.
 */

public class CurrencyValidatorTest {

    private UtilHelper utilHelper;

    @Before
    public void init() {
        utilHelper = new UtilHelper();
    }

    @Test
    public void testCurrencyNull() {
        assertFalse(utilHelper.isValidCurrency(null));
    }
    @Test
    public void testInvalidCurrency() {
        assertFalse(utilHelper.isValidCurrency("SDS"));
    }

    @Test
    public void testValidCurrencyUS() {
        assertTrue(utilHelper.isValidCurrency("USD"));
    }

    @Test
    public void testValidCurrencyIndia() {
        assertTrue(utilHelper.isValidCurrency("INR"));
    }

    @Test
    public void testValidCurrencyEurope() {
        assertTrue(utilHelper.isValidCurrency("EUR"));
    }
}
