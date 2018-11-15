package ir.sanatisharif.android.konkur96.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import ir.sanatisharif.android.konkur96.app.AppConfig;

/**
 * Created by Mohamad on 11/13/2018.
 */

public class ApiVolley {

    private static ApiVolley mAppSingletonInstance;
    private RequestQueue mRequestQueue;


    public static synchronized ApiVolley getInstance() {
        if (mAppSingletonInstance == null) {
            mAppSingletonInstance = new ApiVolley();
        }
        return mAppSingletonInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(AppConfig.context);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }


    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
