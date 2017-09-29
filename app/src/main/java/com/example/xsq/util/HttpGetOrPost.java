package com.example.xsq.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;

import aes.AesException;
import aes.WXBizMsgCrypt;

/**
 * 原先使用的Post或者Get方法的使用
 *
 * @author Administrator
 */
    //
    // 在这一行中做， 没有什么是可以无视的。 我要自己更改一下啊。
public class HttpGetOrPost {

    private static final String ENCODE = HTTP.UTF_8;

    public static String getHttpGet() {
        return "";
    }

    /**
     * @param Url     地址
     * @param Content 参数内容
     * @return 返回数据
     * @throws UnsupportedEncodingException
     */
    public static String getHttpPost(String Url, String Content)
            throws UnsupportedEncodingException {
        // 这种的是 做提示用的。
//		//Application.logMessage("http post:" + Content);
        return getHttpPost("application/json", ENCODE, Url, Content);
    }

    /**
     * @param ContentType application/json application/rdf+xml application/xml text/html
     *                    text/plain
     * @param Charset     编码格式
     * @param Url         地址
     * @param Content     参数内容
     * @return result 返回数据
     * @throws UnsupportedEncodingException
     */
    public static String getHttpPost(String ContentType, String Charset,
                                     String Url, String Content) throws UnsupportedEncodingException {
        String result = "";
        HttpPost request = new HttpPost(Url);
        request.setHeader("Accept", ContentType);
        request.setHeader("Content-type", ContentType);
        StringEntity entity = new StringEntity(Content, Charset);
        request.setEntity(entity);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
        HttpResponse response = null;

        try {
            response = httpClient.execute(request);
            result = EntityUtils.toString(response.getEntity());
//			//Application.logMessage("http post:" + result.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    /**
     * 表单提交方式上传数据
     *
     * @param Url
     * @param params
     * @return String
     * @throws java.io.UnsupportedEncodingException
     */
    public static String getHttpPostLin(String Url,
                                        LinkedList<NameValuePair> params)
            throws UnsupportedEncodingException {
        return getHttpPost("text/html", ENCODE, Url, params);
    }

    /**
     * @param ContentType 格式 application/json application/rdf+xml application/xml
     *                    text/html text/plain
     * @param Charset
     * @param Url
     * @return result 数据内容
     * @throws java.io.UnsupportedEncodingException
     */
    public static String getHttpPost(String ContentType, String Charset,
                                     String Url, LinkedList<NameValuePair> params)
            throws UnsupportedEncodingException {
        //Application.logMessage("http post:" + Url);

        String result = "";
        if (params == null) {
            params = new LinkedList<NameValuePair>();
        }
        HttpPost request = new HttpPost(Url);
        request.setEntity(new UrlEncodedFormEntity(params, Charset));
        try {
            System.out.println(EntityUtils.toString(request.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DefaultHttpClient httpClient = new DefaultHttpClient();
         httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10000);
         httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,15000);
        HttpResponse response = null;
        try {
            // response = httpClient.execute(request);
            response = httpClient.execute(request);
            result = EntityUtils.toString(response.getEntity());
            //Application.logMessage("http post:" + result.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    /**
     * json提交方式上传数据
     *
     * @param Url
     * @param params
     * @return String
     * @throws java.io.UnsupportedEncodingException
     */
    public static String getJsonHttpGetLin(String Url, String  params) throws UnsupportedEncodingException {
        // json 方式提交数据， 并且。。 没有我现在都不知道我做什么了。 我真的是醉 了。
//        return getJsonHttpGet2("text/html", ENCODE, Url, params);
        return getJsonHttpGet2("text/html", ENCODE, Url, params);
    }

    // 9/11
    public static String getJsonHttpGet2(String ContentType, String Charset, String Url, String params)
            throws UnsupportedEncodingException {
        //Application.logMessage("http post:" + Url);
        String result = "";
        if (params == null) {
            params = "";
        }
        try {

            System.out.println(params.toString());
//            HttpGet request = new HttpGet(Url + "?" + URLEncoder.encode(params.toString(), "utf-8"));
            HttpGet request = new HttpGet(Url + "?" + params);
            DefaultHttpClient httpClient = new DefaultHttpClient();

            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);

            HttpResponse response = null;

            // 下面这三行直接就是得到 返回的信息了？？ 不用在 handle Message 中处理？？
            // 应该是对的，  post 方式提交的数据可以直接得到数据，
            // android 中 必须在 handler 中处理的是 更改UI 的操作， 网络链接的可以不用放到 handler 中的？
            response = httpClient.execute(request);
            result = EntityUtils.toString(response.getEntity());
            //Application.logMessage("http post:" + result.toString());
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
        return result;
    }

    // get 方式 循环添加数据
    public String UrlSJ(JSONObject params){
        String newS = "";

        return "";
    }


    // 原先方式
    public static String getJsonHttpGet(String ContentType, String Charset, String Url, JSONObject params)
            throws UnsupportedEncodingException {
        //Application.logMessage("http post:" + Url);
        String result = "";
        if (params == null) {
            params = new JSONObject();
        }
        WXBizMsgCrypt wxcpt = null;
        try {
            wxcpt = new WXBizMsgCrypt(NumberUtil.sToken, NumberUtil.sEncodingAESKey, NumberUtil.sCorpID);

            String sEncryptMsg = null;

            sEncryptMsg = wxcpt.EncryptMsg(params.toString(), System.currentTimeMillis() + ""); // 用微信进行加密

            System.out.println(params.toString());
            System.out.println(sEncryptMsg);
            HttpGet request = new HttpGet(Url + "?" + URLEncoder.encode(sEncryptMsg, "utf-8"));
            DefaultHttpClient httpClient = new DefaultHttpClient();

            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);

            HttpResponse response = null;

            // 下面这三行直接就是得到 返回的信息了？？ 不用在 handle Message 中处理？？
            // 应该是对的，  post 方式提交的数据可以直接得到数据，
            // android 中 必须在 handler 中处理的是 更改UI 的操作， 网络链接的可以不用放到 handler 中的？
            response = httpClient.execute(request);
            result = EntityUtils.toString(response.getEntity());
            //Application.logMessage("http post:" + result.toString());
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
        return result;
    }

    /**
     * json提交方式上传数据
     *
     * @param Url
     * @param params
     * @return String
     * @throws java.io.UnsupportedEncodingException
     */
    public static String getJsonHttpPostLin(String Url,
                                            JSONObject params)
            throws UnsupportedEncodingException {
        return getJsonHttpPost("text/html", ENCODE, Url, params);
    }

    public static String getJsonHttpPost(String ContentType, String Charset, String Url, JSONObject params)
            throws UnsupportedEncodingException {
        //Application.logMessage("http post:" + Url);
        String result = "";
        if (params == null) {
            params = new JSONObject();
        }
        HttpPost request = new HttpPost(Url);
        WXBizMsgCrypt wxcpt = null;
        try {

            wxcpt = new WXBizMsgCrypt(NumberUtil.sToken, NumberUtil.sEncodingAESKey, NumberUtil.sCorpID);
        } catch (AesException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        String sEncryptMsg = null;
        try {
            sEncryptMsg = wxcpt.EncryptMsg(params.toString(), System.currentTimeMillis() + "");
        } catch (AesException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        StringEntity entity = new StringEntity(sEncryptMsg, "utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        request.setEntity(entity);
        System.out.println(params.toString());
        System.out.println(sEncryptMsg);
        DefaultHttpClient httpClient = new DefaultHttpClient();
         httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10000);
         httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,15000);
        HttpResponse response = null;
        try {
            // response = httpClient.execute(request);
            response = httpClient.execute(request);
            result = EntityUtils.toString(response.getEntity());
            //Application.logMessage("http post:" + result.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

//////////////////////////////////////////// delete 方法///////////////

    /**
     * json提交方式上传数据
     *
     * @param Url
     * @param params
     * @return String
     * @throws java.io.UnsupportedEncodingException
     */
    public static String getJsonHttpDeleteLin(String Url,
                                              JSONObject params)
            throws UnsupportedEncodingException {
        return getJsonHttpDelete("text/html", ENCODE, Url, params);
    }

    public static String getJsonHttpDelete(String ContentType, String Charset,
                                           String Url, JSONObject params)
            throws UnsupportedEncodingException {
        //Application.logMessage("http post:" + Url);
        String result = "";
        if (params == null) {
            params = new JSONObject();
        }
//		HttpDelete request = new HttpDelete(Url);
        HttpDeleteWithBody httpdelete = new HttpDeleteWithBody(Url);
        WXBizMsgCrypt wxcpt = null;
        try {
            wxcpt = new WXBizMsgCrypt(NumberUtil.sToken, NumberUtil.sEncodingAESKey, NumberUtil.sCorpID);
        } catch (AesException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        String sEncryptMsg = null;
        try {
            sEncryptMsg = wxcpt.EncryptMsg(params.toString(), System.currentTimeMillis() + "");
        } catch (AesException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        StringEntity entity = new StringEntity(sEncryptMsg, "utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpdelete.setEntity(entity);
//        ((HttpResponse) request).setEntity(entity);
        System.out.println(params.toString());
        System.out.println(sEncryptMsg);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,15000);
        HttpResponse response = null;
        try {
            // response = httpClient.execute(request);
            response = httpClient.execute(httpdelete);
            result = EntityUtils.toString(response.getEntity());
            //Application.logMessage("http post:" + result.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
        return result;
    }

    //////////////////////////////////////////// put 方法///////////////

    /**
     * json提交方式上传数据
     *
     * @param Url
     * @param params
     * @return String
     * @throws java.io.UnsupportedEncodingException
     */
    public static String getJsonHttpPut(String Url,
                                        JSONObject params)
            throws UnsupportedEncodingException {
        return getJsonHttpPutLine("text/html", ENCODE, Url, params);
    }

    public static String getJsonHttpPutLine(String ContentType, String Charset,
                                            String Url, JSONObject params)
            throws UnsupportedEncodingException {
        //Application.logMessage("http post:" + Url);
        String result = "";
        if (params == null) {
            params = new JSONObject();
        }
        HttpPut request = new HttpPut(Url);
        WXBizMsgCrypt wxcpt = null;
        try {
            wxcpt = new WXBizMsgCrypt(NumberUtil.sToken, NumberUtil.sEncodingAESKey, NumberUtil.sCorpID);
        } catch (AesException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        String sEncryptMsg = null;
        try {
            sEncryptMsg = wxcpt.EncryptMsg(params.toString(), System.currentTimeMillis() + "");
        } catch (AesException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        StringEntity entity = new StringEntity(sEncryptMsg, "utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        request.setEntity(entity);
        System.out.println(params.toString());
        System.out.println(sEncryptMsg);
        DefaultHttpClient httpClient = new DefaultHttpClient();
         httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10000);
         httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,15000);
        HttpResponse response = null;
        try {
            // response = httpClient.execute(request);
            response = httpClient.execute(request);
            result = EntityUtils.toString(response.getEntity());
            //Application.logMessage("http post:" + result.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

}
