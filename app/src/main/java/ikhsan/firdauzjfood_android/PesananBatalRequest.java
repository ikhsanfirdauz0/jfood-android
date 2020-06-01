package ikhsan.firdauzjfood_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PesananBatalRequest extends StringRequest
{
    private static final String URL = "http://10.0.2.2:8080/invoice/invoiceStatus/";
    private Map<String, String> params;
    private String STATUS_BATAL = "Cancelled";

    /**
     *
     * @param currentInvoiceId the invoice id that wanted to be changed the status string value to "Cancelled"
     * @param listener the listener that will invoke this request
     */
    public PesananBatalRequest(String currentInvoiceId, Response.Listener<String> listener)
    {
        super(Request.Method.PUT, URL+currentInvoiceId, listener, null);
        params = new HashMap<>();
        params.put("id", currentInvoiceId);
        params.put("status", STATUS_BATAL);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return params;
    }
}
