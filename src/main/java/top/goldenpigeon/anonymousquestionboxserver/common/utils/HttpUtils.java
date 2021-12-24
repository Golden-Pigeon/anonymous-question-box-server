package top.goldenpigeon.anonymousquestionboxserver.common.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Slf4j
public class HttpUtils {

    private static final Integer SUCCESS_RESPONSE = 200;

    public static String doGet(String url, Map<String, String> param) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        String resultString = "";

        try {
            URIBuilder builder = new URIBuilder(url);

            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }

            URI uri = builder.build();

            HttpGet httpGet = new HttpGet(uri);

            response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == SUCCESS_RESPONSE) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return resultString;

    }
}
