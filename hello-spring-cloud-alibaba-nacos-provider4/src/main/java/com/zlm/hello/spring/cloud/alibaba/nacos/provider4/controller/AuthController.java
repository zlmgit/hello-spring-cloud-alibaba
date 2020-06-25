package com.zlm.hello.spring.cloud.alibaba.nacos.provider4.controller;

import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider4.utils.JSONUtil;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider4.utils.JsonNodeSerializeUtil;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider4.utils.RsaUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("auth")
@RestController
@Slf4j
public class AuthController {

    private static String CLIENT_ID = "helijian";
    private static String SECRET = "47342ecc-ea55-4672-8af0-c5bea0056be5";
    private static String CLIENT_SECRET = CLIENT_ID+":"+SECRET;
    private static String AUTHORIZATION = "Basic "+ Base64.getUrlEncoder().encodeToString(CLIENT_SECRET.getBytes());
    private static String URL = "https://testapis.ezhiyang.com/open/authorize";
    private static String TEST_URL = "https://testapis.ezhiyang.com/open/rest";
    private static String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC/sGNVcU5WDdyku0WvIgecTs9Ky/EC/+huzQStB9VHKiWkldQCs00q9mbcZgeYkMLV9Jztr7tR4Z4ilVJ6iHAIDoFxJGYglqAlexXxWofOkEPzVBF3LmeDYLAPXZjvNZOrlc7gJYN2Lw2j5ZXrJW0p+NW+K5RBkK5bZM5atJrjL3p5Uq8VWqVwJFg7BVuLsNDoiwf5z4C+calHG1sW4Ve+U34/TGKAXAO9aQXWlPyA2uQSTkIYJZpBagveoQF2rn6PpxiRrci0dLbj3Gq9u05oemMkXz7OHTXhi0gdIiLIgCoLKeOo0xcLCfHqkoasGzkg0A+PAdPnflw9+a+yf0PrAgMBAAECggEBAKRAJB2mhRU/s3yD62XxpsvMy9qvfciwNx4aFpOdTqc3+iFZMN1PfiP+Fo1r775O7GQgwgb4uW2kAbhoctAD8Ns2crvcEJaD+p2jJl400IGzJEZwFrck0BXG15WTAeSW5/3y091Ex6yRDQMPSrK+0xdP3PaiqNq1enFdo2QdtUpMe78WmVtk/OFMUP+Ns2tSTqhjKwLXNKkRZFGaThA4VtZ6/tTWpAw7T00+hl3ckhh4jlZ0RcradYMYycH/fnz+LCCoUbMnDJdRFC9FxY7Mq97Aij3opgWwRSiVbZtbV+77gPDiFM4gxrbJne5tCXpDXeuMroCUF+F5vC3uiriHNUECgYEA73/oFlUgpzwS/8P6ntzcmFr4lQIk/aHB06QnFUFFtRy71HhX36eJ9GWW/ZmMBGKb4x4LHrib9Ij6bBDTupTds+TwoAokGq2u3liu8OEOflhnB7olhRlWwLyXPAdT/jJzfiZMF/VfjIh8E9vCfy1Hhfr4bBL62nc7+BJBkHFckhkCgYEAzOU85eAgf9Myy9HW6wgLaLL7Vs23NLgJ+0vVgxLwcz56MIixxGzDa19FK1IVPdNTMflf1rxe2SwbnLD3fQ5JnHJYu4uQdt+PZhAxQ92mSmv1oTBVrgsba1kqk+6F3OmMvFJockH8iUD2L6QD8cyO/WMZjGlccHgJzQE82d/z7qMCgYALYx150lpXGTgeCQdlfkLOy6R+ETgJByPcGDXzgML7tX4IwCANdj3M7Pec3ywVGUsoJUmKEGT533loofQvLtzLrNr0AQdubUufLsBCm7DAuRtM9sDJ70Z8q3rVU9Pehi+Pk+qSSQxd9i9QEmbQ/SolaKVfHhfnQzwKs3OClqE8IQKBgCSBAuSBlciwO/CMJhohiMbSfGzuoaFG2KBo1lVP3n7ptqNG90Tji71pb81UBmzJZZ0VrLl5Pfy7GwBDlxQ0o3C3/o8LQmWrVsT8RYqtxp6jtVNOZTVozS3G6QsKB9df1K8Ij4srrm5VoIbEVd+Sbjc+nKKjctCm0jgv7EN03uS5AoGBAKgNhzouXtVdwD+rt+XAhB1bUaR3SEAfYtJuOOdBKc0GSlVEr7fZXeXg5ILXFDHhTk+rIO1sGbojeVgKB1ab7DTH+v+ko7Q54CWjfe6AhbJqHZj0ZEVDvfMOAGW6H8AygOvF0/8sHmJwoymqO8snFcy/B2aTLguhlfWZrutNDjQ3";
    @GetMapping("getToken")
    public String getToken() {
        Map<String,Object> map = new HashMap<>();
        map.put("grant_type", "client_credentials");
        final String httpsResult = HttpRequest.post(URL)
                .header("Authorization", AUTHORIZATION)
                .form(map)
                .timeout(20000)
                .execute().body();
        Console.log(httpsResult);
        if (StringUtils.isBlank(httpsResult)) {
            return null;
        }
        final JSONObject jsonObject = JSON.parseObject(httpsResult);
        return (String)jsonObject.get("access_token");
    }

    @GetMapping("rest/{token}")
    public Object rest(@PathVariable("token") String token){
        String authorization = "Bearer "+StringUtils.trim(token);
        Map<String,Object> param = new HashMap<>();
        param.put("type", "ralph.personAuth");
        Map<String,Object> data = new HashMap<>();
        data.put("name","朱路明");
        data.put("certNo","429006199102240618");
        data.put("mobile","13477407066");
        data.put("accountType",1);
        data.put("accountNo","6212263602052292398");
        param.put("data",data);
        Map<String, Object> sign = sign(param);
        log.info("请求数据：{}",JSON.toJSONString(sign));
        log.info("请求参数{}",sign);
        String httpsResult = HttpRequest.post(TEST_URL)
                .header("Authorization", authorization)
                .header("Content-Type", "application/json")
                .form(sign)
                .execute().body();
        Console.log(httpsResult);
        return httpsResult;
    }

    public Map<String, Object> sign(Map<String,Object> param) {
        String signDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        String format = "name=%s&certNo=%s&mobile=%s&accountType=%s&accountNo=%s&signDate=%s" ;
        Map<String,Object> data = (Map<String, Object>) param.get("data");
        String plainText = String.format(format,data.get("name"),data.get("certNo"),data.get("mobile"),data.get("accountType"),data.get("accountNo"),signDate);
        String signature;
        try {
            /*PrivateKey privateKey = RsaUtil.loadPrivateKey(PRIVATE_KEY);*/
            PrivateKey privateKey = RsaUtil.loadPrivateKey(
                    IOUtils
                            .toString(new FileReader("C:\\Users\\zy\\Desktop\\helijian\\应用私钥2048.txt")));
            log.info("签名数据：{}",plainText);
            signature = RsaUtil.sign(plainText, privateKey);
            PublicKey publicKey = RsaUtil.loadPublicKey(
                    IOUtils.toString(new FileReader("C:\\Users\\zy\\Desktop\\helijian\\应用公钥2048.txt")));
            log.info("签名是否正确：{}",RsaUtil.verify(plainText,signature,publicKey));
        } catch (Exception e) {
            log.error("获取私钥异常:{}", e);
            throw new RuntimeException(e);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("signature", signature);
        result.put("signDate", signDate);
        param.put("sign",result);
        return param;
    }



}
