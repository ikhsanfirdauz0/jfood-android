package ikhsan.firdauzjfood_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest
{
    private static final String URL = "http://10.0.2.2:8080/customer/login";
    private Map<String, String> params;

    /**
     *
     * @param email is the customer's email that will be passed to the request URL
     * @param password is the customer's password that will be passed to the request URL to check if the password match the email
     * @param listener is the listener that will invoke this request
     */
    public LoginRequest(String email, String password, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return params;
    }

}
