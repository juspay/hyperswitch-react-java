import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.file.Paths;

import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.port;

import org.json.JSONObject;

public class server {

  public static void main(String[] args) {

    port(4242);

    staticFiles.externalLocation(Paths.get("public").toAbsolutePath().toString());

    post("/create-payment", (request, response) -> {

      response.type("application/json");

      /*
        If you have two or more “business_country” + “business_label” pairs configured in your Hyperswitch dashboard,
        please pass the fields business_country and business_label in this request body.
        For accessing more features, you can check out the request body schema for payments-create API here :
        https://api-reference.hyperswitch.io/docs/hyperswitch-api-reference/60bae82472db8-payments-create
      */

      String payload = "{ \"amount\": 100, \"currency\": \"USD\" }";

      String response_string = createPayment(payload);
      JSONObject response_json = new JSONObject(response_string);

      String client_secret = response_json.getString("client_secret");

      JSONObject final_response = new JSONObject();
      final_response.put("client_secret", client_secret);

      return final_response;

    });

  }

  private static String createPayment(String payload) {

    try {

      String HYPER_SWITCH_API_KEY = "HYPERSWITCH_API_KEY";
      String HYPER_SWITCH_API_BASE_URL = "https://sandbox.hyperswitch.io/payments";

      URL url = new URL(HYPER_SWITCH_API_BASE_URL);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();

      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setRequestProperty("Accept", "application/json");
      conn.setRequestProperty("api-key", HYPER_SWITCH_API_KEY);
      conn.setDoOutput(true);

      try (OutputStream os = conn.getOutputStream()) {
        byte[] input = payload.getBytes("utf-8");
        os.write(input, 0, input.length);
      }

      int responseCode = conn.getResponseCode();

      if (responseCode == HttpURLConnection.HTTP_OK) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
          StringBuilder response = new StringBuilder();
          String responseLine;
          while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
          }
          return response.toString();
        }
      } else {
        return "HTTP request failed with response code: " + responseCode;
      }
    } catch (IOException e) {
      return e.getMessage();
    }

  }

}
