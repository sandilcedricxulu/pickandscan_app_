package com.example.picknscan;
import android.os.StrictMode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpMethods {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    static OkHttpClient client = new OkHttpClient();

    public static String Post(String url,String username,String password, String body) throws IOException {
        body = "{\"api_username\":\"" + username + "\", \"api_password\":\"" + password + "\"," + body.substring(body.indexOf('{')+1);
        RequestBody requestBody = RequestBody.create(JSON, body);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String Get(String url,String username,String password,String body) throws IOException {
        body = "{\"isGET\":\"1\",\"api_username\":\"" + username + "\", \"api_password\":\"" + password + "\"," + body.substring(body.indexOf('{')+1);
        RequestBody requestBody = RequestBody.create(JSON, body);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public static String Put(String url, String username,String password, String body) throws IOException {
        MediaType JSON = MediaType.parse("application/json");
        body = "{\"api_username\":\"" + username + "\", \"api_password\":\"" + password + "\"," + body.substring(body.indexOf('{')+1);
        RequestBody requestBody = RequestBody.create(JSON, body);

        Request request = new Request.Builder()
                .url(url)
                .put(requestBody) //PUT
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
//    public static boolean isOnline() {
//        Runtime runtime = Runtime.getRuntime();
//        try {
//            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
//            int     exitValue = ipProcess.waitFor();
//            return (exitValue == 0);
//        }
//        catch (IOException e)          { e.printStackTrace(); }
//        catch (InterruptedException e) { e.printStackTrace(); }
//
//        return false;
//    }

    public static boolean isOnline() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return true;
        } catch (IOException e) { return false; }
    }
}
