package ikhsan.firdauzjfood_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/customer/register";
    private Map<String, String> params;

    /**
     *
     * @param name is the name in the registration form that will be passed tp the request URL
     * @param email is the email in the registration form that will be passed tp the request URL
     * @param password is the password in the registration form that will be passed tp the request URL
     * @param listener is the listener that will invoke this request
     */
    public RegisterRequest(String name, String email, String password, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return params;
    }

}
