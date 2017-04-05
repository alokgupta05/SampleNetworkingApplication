package sample.codetest.com.musicapplication;

import org.junit.Before;
import org.junit.Test;

import sample.codetest.com.musicapplication.utils.Constant;
import sample.codetest.com.musicapplication.utils.UtilHelper;
import static org.junit.Assert.*;
/**
 * Created by Megha on 22-03-2017.
 */


    public class DateValidatorTest {

        private UtilHelper utilHelper;

        @Before
        public void init() {
            utilHelper = new UtilHelper();
        }

        @Test
        public void testDateIsNull() {
            assertFalse(utilHelper.isThisDateValid(null, Constant.INPUT_DATE));
        }

        @Test
        public void testDayIsInvalid() {
            assertFalse(utilHelper.isThisDateValid("2005-07-32T07:00:00Z", Constant.INPUT_DATE));
        }

        @Test
        public void testMonthIsInvalid() {
            assertFalse(utilHelper.isThisDateValid("2005-13-05T07:00:00Z", Constant.INPUT_DATE));
        }

        @Test
        public void testYearIsInvalid() {
            assertFalse(utilHelper.isThisDateValid("-19991-07-05T07:00:00Z", Constant.INPUT_DATE));
        }

        @Test
        public void testDateFormatIsInvalid() {
            assertFalse(utilHelper.isThisDateValid("07-2005-05T07:00:00Z", Constant.INPUT_DATE));
        }

        @Test
        public void testDateFeb29_2012() {
            assertTrue(utilHelper.isThisDateValid("2012-02-29T07:00:00Z", Constant.INPUT_DATE));
        }


        @Test
        public void testDateFeb28() {
            assertTrue(utilHelper.isThisDateValid("2005-02-28T07:00:00Z1", Constant.INPUT_DATE));
        }

        @Test
        public void testDateIsValid() {
            assertTrue(utilHelper.isThisDateValid("2005-07-05T07:00:00Z", Constant.INPUT_DATE));
        }

}
