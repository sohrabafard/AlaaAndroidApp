package ir.sanatisharif.android.konkur96.api;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;

import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackList;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackMessage;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;

/**
 * Created by Mohamad on 11/13/2018.
 */

public class ApiRestClient {

    private static final String TAG = "ApiRestClient";
    // private IServerCallbackList iServerCallbackList;
    // private IServerCallbackMessage iServerCallbackMessage;
    //private IServerCallbackObject iServerCallbackObject;

    public static void postMessage(String url, HashMap<String, String> parameter, final IServerCallbackMessage iServerCallbackMessage) {

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                iServerCallbackMessage.onSuccess(response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    Log.i(TAG, "onErrorResponse: " + networkResponse.statusCode);

                    if (networkResponse.statusCode == 403) {

                    }
                }
            }

        });
        // Adding String request to request queue
        ApiVolley.getInstance().addToRequestQueue(strReq, "");

    }

}
