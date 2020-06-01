package ikhsan.firdauzjfood_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BuatPesananRequest extends StringRequest
{
    private static final String URLcash = "http://10.0.2.2:8080/invoice/createCashInvoice";
    private static final String URLcashless = "http://10.0.2.2:8080/invoice/createCashlessInvoice";
    private HashMap<String, String> params;

    /**
     *
     * @param foodList is a list of food's id that will be passed to the request URL
     * @param customerId is the customer's id who order the foods and will be passed to the request URL
     * @param listener the listener that will invoke this request
     */
    public BuatPesananRequest(String foodList, String customerId, Response.Listener<String> listener)
    {
        super(Method.POST, URLcash, listener, null);
        params = new HashMap<>();
        params.put("foods", foodList);
        params.put("customer", customerId);
        params.put("deliveryFee", "5000");
    }

    /**
     *
     * @param foodList is a list of food's id that will be passed to the request URL
     * @param customerId is the customer's id who order the foods and will be passed to the request URL
     * @param promoCode is a string value of promo code that will be used in this invoice and will be passed to the request URL
     * @param listener the listener that will invoke this request
     */
    public BuatPesananRequest(String foodList, String customerId, String promoCode, Response.Listener<String> listener)
    {
        super(Method.POST, URLcashless, listener, null);
        params = new HashMap<>();
        params.put("foods", foodList);
        params.put("customer", customerId);
        params.put("promoCode", promoCode);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return params;
    }

}
