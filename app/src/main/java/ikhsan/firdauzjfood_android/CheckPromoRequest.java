package ikhsan.firdauzjfood_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CheckPromoRequest extends StringRequest
{
    private static final String URL = "http://10.0.2.2:8080/promo/";
    private HashMap<String, String> params;

    /**
     *
     * @param the code promo that needs to be checked whether exist or not in the database
     * @param listener the listener that will invoke this request
     */
    public CheckPromoRequest(String codePromo, Response.Listener<String> listener)
    {
        super(Method.GET, URL+codePromo, listener, null);
        params = new HashMap<>();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return params;
    }

}
