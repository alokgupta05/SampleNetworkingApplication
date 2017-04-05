package sample.codetest.com.musicapplication.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

import sample.codetest.com.musicapplication.beans.TrackBean;
import sample.codetest.com.musicapplication.network.NetworkManager;

/**
 * Created by Megha on 22-03-2017.
 */

public class UtilHelper {

    public static SortedMap<Currency, Locale> currencyLocaleMap;

    static {
        currencyLocaleMap = new TreeMap<Currency, Locale>(new Comparator<Currency>() {
            public int compare(Currency c1, Currency c2) {
                return c1.getCurrencyCode().compareTo(c2.getCurrencyCode());
            }
        });
        for (Locale locale : Locale.getAvailableLocales()) {
            try {
                Currency currency = Currency.getInstance(locale);
                currencyLocaleMap.put(currency, locale);
            } catch (Exception e) {
            }
        }
    }


    public static String getCurrencySymbol(String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode);
        System.out.println(currencyCode + ":-" + currency.getSymbol(currencyLocaleMap.get(currency)));
        return currency.getSymbol(currencyLocaleMap.get(currency));
    }
    /*
    validates is currency is correct or not
     */
    public boolean isValidCurrency(String currencyCode){
        if(currencyCode==null)
            return false;
        try {
            Currency currency = Currency.getInstance(currencyCode);
            currency.getSymbol(currencyLocaleMap.get(currency));
            System.out.println(currencyCode + ":-" + currency.getSymbol(currencyLocaleMap.get(currency)));
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*
    validates is date is in correct format or not
     */
    public boolean isThisDateValid(String dateToValidate, String dateFormat){

        if(dateToValidate == null){
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);

        try {

            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
            System.out.println(date);

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }
    /*
   Parse json and show data
    */
    public List<TrackBean> convertStringToJson(String response){

        ArrayList<TrackBean> trackBeanList = new ArrayList<>();


        try {
            JSONObject rootJsonObject = new JSONObject(response);
            JSONArray jsonAlbumArray = rootJsonObject.optJSONArray("results");
            if(jsonAlbumArray!=null) {
                for (int i = 0; i < jsonAlbumArray.length(); i++) {
                    JSONObject albumJson = jsonAlbumArray.optJSONObject(i);

                    if (albumJson != null) {
                        TrackBean albumBean = new TrackBean();
                        albumBean.albumName= albumJson.optString("collectionName");
                        albumBean.artistName = albumJson.optString("artistName");
                        albumBean.artworkUrl30 = albumJson.optString("artworkUrl30");
                        albumBean.artworkUrl60 = albumJson.optString("artworkUrl60");
                        albumBean.artworkUrl100 = albumJson.optString("artworkUrl100");
                        albumBean.trackId = albumJson.optString("trackId");
                        albumBean.trackName = albumJson.optString("trackName");
                        albumBean.trackPrice = albumJson.optString("trackPrice");
                        albumBean.releaseDate = albumJson.optString("releaseDate");
                        albumBean.currency = albumJson.optString("currency");

                        trackBeanList.add(albumBean);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trackBeanList;

    }

   public List<TrackBean>  getAlbumList(String urlString){
       List<TrackBean> trackBeanList = null;
        try {
            URL url = new URL(urlString);
            String resultString = new NetworkManager().downloadUrl(url);
            if (resultString != null) {
                Log.d("AlbumAsyncTask","Response : "+resultString);
                trackBeanList = new UtilHelper().convertStringToJson(resultString);

            } else {
                trackBeanList = null;
            }
        } catch(Exception e) {
            trackBeanList = null;
        }
       return trackBeanList;
    }

}
