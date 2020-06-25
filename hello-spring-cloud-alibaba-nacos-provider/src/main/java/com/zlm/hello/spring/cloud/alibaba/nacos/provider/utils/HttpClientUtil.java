package com.zlm.hello.spring.cloud.alibaba.nacos.provider.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    public static String doGet(String url, Map<String, String> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doGet(String url) {
        return doGet(url, null);
    }

    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    public static String doPost(String url) {
        return doPost(url, null);
    }

    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }
  /**
   * 使用httpclint 发送文件，如果不传输文件，直接设置fileParams=null，
   * 如果不设置请求头参数，直接设置headerParams=null，就可以进行普通参数的POST请求了
   *
   * @param url          请求路径
   * @param fileParams   文件参数
   * @param otherParams  其他字符串参数
   * @param headerParams 请求头参数
   * @return
   */
  public static String uploadFile(String url, Map<String, MultipartFile> fileParams,
                                  Map<String, String> otherParams, Map<String, String> headerParams) {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    String result = "";
    try {
      HttpPost httpPost = new HttpPost(url);
      //设置请求头
      if (headerParams != null && headerParams.size() > 0) {
        for (Map.Entry<String, String> e : headerParams.entrySet()) {
          String value = e.getValue();
          String key = e.getKey();
          if (StringUtils.isNotBlank(value)) {
            httpPost.setHeader(key, value);
          }
        }
      }
      MultipartEntityBuilder builder = MultipartEntityBuilder.create();
      builder.setCharset(Charset.forName("utf-8"));
      //加上此行代码解决返回中文乱码问题
      builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
      //    文件传输http请求头(multipart/form-data)
      if (fileParams != null && fileParams.size() > 0) {
        for (Map.Entry<String, MultipartFile> e : fileParams.entrySet()) {
          String fileParamName = e.getKey();
          MultipartFile file = e.getValue();
          if (file != null) {
            String fileName = file.getOriginalFilename();
            // 文件流
            builder.addBinaryBody(fileParamName, file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);
          }
        }
      }
      //    字节传输http请求头(application/json)
      ContentType contentType = ContentType.create("application/json", Charset.forName("UTF-8"));
      if (otherParams != null && otherParams.size() > 0) {
        for (Map.Entry<String, String> e : otherParams.entrySet()) {
          String value = e.getValue();
          if (StringUtils.isNotBlank(value)) {
            // 类似浏览器表单提交，对应input的name和value
            builder.addTextBody(e.getKey(), value, contentType);
          }
        }
      }
      HttpEntity entity = builder.build();
      httpPost.setEntity(entity);
      // 执行提交
      HttpResponse response = httpClient.execute(httpPost);
      HttpEntity responseEntity = response.getEntity();
      if (responseEntity != null) {
        // 将响应内容转换为字符串
        result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        httpClient.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return result;
  }
}
