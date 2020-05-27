package ikhsan.firdauzjfood_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PesananSelesaiRequest extends StringRequest
{
    private static final String URL = "http://10.0.2.2:8080/invoice/invoiceStatus/";
    private Map<String, String> params;
    private String STATUS_FINISHED = "Finished";
    public PesananSelesaiRequest(String currentInvoiceId, Response.Listener<String> listener)
    {
        super(Request.Method.PUT, URL+currentInvoiceId, listener, null);
        params = new HashMap<>();
        params.put("id", currentInvoiceId);
        params.put("status", STATUS_FINISHED);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return params;
    }
}
