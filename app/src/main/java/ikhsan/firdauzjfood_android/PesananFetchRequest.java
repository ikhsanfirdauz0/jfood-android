package ikhsan.firdauzjfood_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PesananFetchRequest extends StringRequest
{
    private static final String URL = "http://10.0.2.2:8080/invoice/customer/";
    private Map<String, String> params;

    /**
     *
     * @param customerId the customer id of the owner of the invoice that want to be fetched
     * @param listener the listener that will invoke this request
     */
    public PesananFetchRequest(String customerId, Response.Listener<String> listener)
    {
        super(Method.GET, URL+customerId, listener, null);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return params;
    }
}
