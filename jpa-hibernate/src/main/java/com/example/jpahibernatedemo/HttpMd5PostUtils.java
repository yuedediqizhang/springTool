package com.example.jpahibernatedemo;


import cn.hutool.crypto.SecureUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.UUID;


@Component
public class HttpMd5PostUtils
{
    private static final Logger log = LoggerFactory.getLogger(HttpMd5PostUtils.class);

    public static String sendPost(String url, String param)
    {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try
        {
            log.info("sendPost - {}", url);
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null)
            {
                result.append(line);
            }
            log.info("recv - {}", result);
        }
        catch (ConnectException e)
        {
            log.error("调用HttpUtils.sendPost ConnectException, url=" + url + ",param=" + param, e);
        }
        catch (SocketTimeoutException e)
        {
            log.error("调用HttpUtils.sendPost SocketTimeoutException, url=" + url + ",param=" + param, e);
        }
        catch (IOException e)
        {
            log.error("调用HttpUtils.sendPost IOException, url=" + url + ",param=" + param, e);
        }
        catch (Exception e)
        {
            log.error("调用HttpsUtil.sendPost Exception, url=" + url + ",param=" + param, e);
        }
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
                if (in != null)
                {
                    in.close();
                }
            }
            catch (IOException ex)
            {
                log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
            }
        }
        return result.toString();
    }

    private static class TrustAnyTrustManager implements X509TrustManager
    {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
        {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
        {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers()
        {
            return new X509Certificate[] {};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier
    {
        @Override
        public boolean verify(String hostname, SSLSession session)
        {
            return true;
        }
    }


    /**
     * HTTP请求
     * @param url
     * @param json
     * @return
     */

    public static Result doPost(String url, String json, String appKey, String appSecret) {
        Result result = new Result();
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {

            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            httpclient = httpClientBuilder.build();
            HttpPost httppost = new HttpPost(url);

            httppost.setHeader("Content-Type", "application/json;charset=utf-8");
            httppost.setHeader("Expect", "100-continue");
            httppost.setHeader("Accept-Encoding", "gzip,deflate");
            httppost.setHeader("Connection", "Keep-Alive");
            // 获取时间戳
            String timestamp = System.currentTimeMillis() + "";
            // 获取随机字符串
            String nonceStr = UUID.randomUUID().toString().replace("-", "");
            String qs = String.format("timestamp=%s&nonceStr=%s&secret=%s&params=%s", timestamp, nonceStr, appSecret, json);
            String sign = SecureUtil.md5(qs).toLowerCase();
            httppost.setHeader("timestamp", timestamp);
            httppost.setHeader("nonceStr", nonceStr);
            httppost.setHeader("signature", sign);
            httppost.setHeader("appKey", appKey);
            //如果json 为null，会出现异常
            if (null != json) {
                StringEntity stringEntity = new StringEntity(json, "utf-8");
                stringEntity.setContentEncoding("UTF-8");
                stringEntity.setContentType("application/json");
                httppost.setEntity(stringEntity);
            }

            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                int status = response.getStatusLine().getStatusCode();

                if ((status >= 200 && status < 300)) {
                    result.setData(EntityUtils.toString(entity));
                    result.setStatus(true);
                } else {
                    log.error("调用接口异常:URL:{}参数：{}返回值：{}",url,json,EntityUtils.toString(entity));
                    result.setData(EntityUtils.toString(entity));
                    result.setMsg("调用接口异常");
                    result.setStatus(false);
                }
            }
        } catch (Exception ex) {
            log.error("调用HttpUtils.doPostAspect ConnectException, url=" + url + ",param=" + json, ex);
            result.setStatus(false);
            result.setMsg("调用接口异常");

            ex.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("call http service response close error:{}", e);
                    log.error("调用HttpUtils.doPostAspect ConnectException, url=" + url + ",param=" + json, e);
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

 public static class Result {
        private boolean status;
        private String msg;
        private String data;

     public boolean isStatus() {
         return status;
     }

     public Result setStatus(boolean status) {
         this.status = status;
         return this;
     }

     public String getMsg() {
         return msg;
     }

     public Result setMsg(String msg) {
         this.msg = msg;
         return this;
     }

     public String getData() {
         return data;
     }

     public Result setData(String data) {
         this.data = data;
         return this;
     }
 }

    /**
     * 向指定 URL 发送POST方法的请求
     * url 发送请求的 URL
     * params 请求的参数集合
     * @return 远程资源的响应结果
     */
    public static String sendFormPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new    StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            if (params != null) {
                StringBuilder param = new    StringBuilder();
                for (Map.Entry<   String,    String> entry : params.entrySet()) {
                    if(param.length()>0){
                        param.append("&");
                    }
                    param.append(entry.getKey());
                    param.append("=");
                    param.append(entry.getValue());
                    System.out.println(entry.getKey()+":"+entry.getValue());
                }
                System.out.println("param:"+param.toString());
                out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
}
