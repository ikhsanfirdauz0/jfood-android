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

    public BuatPesananRequest(String foodList, String customerId, Response.Listener<String> listener)
    {
        super(Method.POST, URLcash, listener, null);
        params = new HashMap<>();
        params.put("foods", foodList);
        params.put("customer", customerId);
        params.put("deliveryFee", "5000");
    }

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
