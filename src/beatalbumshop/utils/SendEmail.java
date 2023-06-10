package beatalbumshop.utils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.Base64;

public class SendEmail {
    public static boolean send(String toRecipient, String recipientName, String subject, String body) {
        String url = "https://api.elasticemail.com/v2/email/send";
        String apiKey = "FEE7C772E79AA83098363CEB89509257DE0C13DE3DE0D6E627CAA04E1F0FBB47494CE49E178799D17AB2E0BFA61B1BB9";
//        String to = "56duong@gmail.com";
//        String subject = "Test email";
        String from = "nguyenduong07122003@gmail.com";
        String senderName = "BEAT";
//        String body = "<html>\n" +
//                    "    <head>\n" +
//                    "        <style>\n" +
//                    "            * {\n" +
//                    "                margin: 0;\n" +
//                    "                padding: 0;\n" +
//                    "                box-sizing: border-box;\n" +
//                    "                font-family: Arial, Helvetica, sans-serif;\n" +
//                    "            }\n" +
//                    "\n" +
//                    "            .text-center {\n" +
//                    "                text-align: center;\n" +
//                    "            }\n" +
//                    "\n" +
//                    "            .text-left {\n" +
//                    "                text-align: left;\n" +
//                    "            }\n" +
//                    "\n" +
//                    "            .bd {\n" +
//                    "                font-size: 0.875rem;\n" +
//                    "            }\n" +
//                    "\n" +
//                    "            table {\n" +
//                    "                min-width: 600px;\n" +
//                    "                margin: 40px auto;\n" +
//                    "                border: 1px solid #000;\n" +
//                    "            }\n" +
//                    "\n" +
//                    "            table {\n" +
//                    "                border-bottom: 0;\n" +
//                    "            }\n" +
//                    "\n" +
//                    "            tr {\n" +
//                    "                display: block;\n" +
//                    "                width: 100%;\n" +
//                    "                border-bottom: 1px solid #000;\n" +
//                    "                text-align: center;\n" +
//                    "            }\n" +
//                    "\n" +
//                    "            td,\n" +
//                    "            th {\n" +
//                    "                display: inline-block;\n" +
//                    "                width: 440px;\n" +
//                    "                margin: auto;\n" +
//                    "                padding: 20px 0;\n" +
//                    "            }\n" +
//                    "\n" +
//                    "            .black-button {\n" +
//                    "                display: block;\n" +
//                    "                width: fit-content;\n" +
//                    "                padding: 7px 30px;\n" +
//                    "                border: 1px solid #000;\n" +
//                    "                border-radius: 5px;\n" +
//                    "                margin: auto;\n" +
//                    "                color: #fff;\n" +
//                    "                background-color: #000;\n" +
//                    "                text-transform: uppercase;\n" +
//                    "                text-decoration: none;\n" +
//                    "                cursor: pointer;\n" +
//                    "                transition: .2s;\n" +
//                    "            }\n" +
//                    "\n" +
//                    "            span {\n" +
//                    "                text-decoration: underline;\n" +
//                    "            }\n" +
//                    "\n" +
//                    "        </style>\n" +
//                    "    </head>\n" +
//                    "\n" +
//                    "    <div class=\"bd\">\n" +
//                    "        <table>\n" +
//                    "            <tbody>\n" +
//                    "                <tr>\n" +
//                    "                    <th>LOL MANAGEMENT</th>\n" +
//                    "                </tr>\n" +
//                    "\n" +
//                        content +
//                    "\n" +
//                    "\n" +
//                    "                <tr>\n" +
//                    "                    <td class=\"text-center\">\n" +
//                    "                        <small>© 2023 LOL MANAGEMENT</small>\n" +
//                    "                    </td>\n" +
//                    "                </tr>\n" +
//                    "            </tbody>\n" +
//                    "        </table>\n" +
//                    "\n" +
//                    "    </div>\n" +
//                    "</html>";

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

            // Set the basic authentication header
            String authHeader = "Basic " + Base64.getEncoder().encodeToString(("apikey:" + apiKey).getBytes(StandardCharsets.UTF_8));
            con.setRequestProperty("Authorization", authHeader);

            // Set the request parameters
            String urlParams = "apikey=" + apiKey + "&from=" + from + "&fromName=" + senderName + "&subject=" + subject + "&bodyHtml=" + body + "&to=" + toRecipient;

            // Send the post request
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(urlParams.getBytes());
            os.flush();
            os.close();

            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
//                System.out.println("success");
                return true;
            } else {
                System.out.println("Request failed with response code: " + responseCode);
                return false;
            }

//            // Read the response
//            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//
//            in.close();
//
//            // Print the response
//            System.out.println("Response Code : " + responseCode);
//            System.out.println(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return true;
    }
    
    
    
    public static boolean sendOrderStatusEmail(String toRecipient, String recipientName, String subject, String content) {
        String url = "https://api.elasticemail.com/v2/email/send";
        String apiKey = "FEE7C772E79AA83098363CEB89509257DE0C13DE3DE0D6E627CAA04E1F0FBB47494CE49E178799D17AB2E0BFA61B1BB9";
//        String to = "56duong@gmail.com";
//        String subject = "Test email";
        String from = "nguyenduong07122003@gmail.com";
        String senderName = "BEAT";
        String body = "" +
    "       <html>\n" +
    "            <head>\n" +
    "                <style>\n" +
    "                * {\n" +
    "                    margin: 0;\n" +
    "                    padding: 0;\n" +
    "                    box-sizing: border-box;\n" +
    "                    font-family: Arial, Helvetica, sans-serif;\n" +
    "                }\n" +
    "\n" +
    "                .text-center {\n" +
    "                    text-align: center;\n" +
    "                }\n" +
    "\n" +
    "                .text-left {\n" +
    "                    text-align: left;\n" +
    "                }\n" +
    "\n" +
    "                .bd {\n" +
    "                    font-size: 0.875rem;\n" +
    "                }\n" +
    "\n" +
    "                table {\n" +
    "                    min-width: 600px;\n" +
    "                    margin: 40px auto;\n" +
    "                    border: 1px solid #000;\n" +
    "                }\n" +
    "\n" +
    "                table {\n" +
    "                    border-bottom: 0;\n" +
    "                }\n" +
    "\n" +
    "                tr {\n" +
    "                    display: block;\n" +
    "                    width: 100%;\n" +
    "                    border-bottom: 1px solid #000;\n" +
    "                    text-align: center;\n" +
    "                }\n" +
    "\n" +
    "                td,\n" +
    "                th {\n" +
    "                    display: inline-block;\n" +
    "                    width: 440px;\n" +
    "                    margin: auto;\n" +
    "                    padding: 20px 0;\n" +
    "                }\n" +
    "\n" +
    "                .black-button {\n" +
    "                    display: block;\n" +
    "                    width: fit-content;\n" +
    "                    padding: 7px 30px;\n" +
    "                    border: 1px solid #000;\n" +
    "                    border-radius: 5px;\n" +
    "                    margin: auto;\n" +
    "                    color: #fff;\n" +
    "                    background-color: #000;\n" +
    "                    text-transform: uppercase;\n" +
    "                    text-decoration: none;\n" +
    "                    cursor: pointer;\n" +
    "                    transition: .2s;\n" +
    "                }\n" +
    "\n" +
    "                span {\n" +
    "                    text-decoration: underline;\n" +
    "                }\n" +
    "\n" +
    "            </style>\n" +
    "            </head>\n" +
    "\n" +
    "            <div class=\"bd\">\n" +
    "                <table>\n" +
    "                    <tbody>\n" +
    "                        <tr>\n" +
    "                            <th>BEAT</th>\n" +
    "                        </tr>\n" +
    "\n" +
    "                        <tr>\n" +
    "                            <td class=\"text-center\" style=\"padding: 80px 0 !important;\">\n" +
    "                                " + content + "\n" +
    "                            </td>\n" +
    "                        </tr>\n" +
    "\n" +
    "                        <tr>\n" +
    "                            <td>\n" +
    "                                <p class=\"text-center\">If you have made these changes, please disregard this email. However, if you did not request any modifications to your order, we kindly request you to contact our customer support team immediately</p>\n" +
    "                            </td>\n" +
    "                        </tr>\n" +
    "\n" +
    "                        <tr>\n" +
    "                            <td class=\"text-center\">\n" +
    "                                Should you need any further information, please <span>email us</span>.<br>\n" +
    "                                By contacting Client Service, you agree that your data will be transferred outside your country.\n" +
    "                                <br><br>\n" +
    "                                BEAT Client Service\n" +
    "                                <br><br>\n" +
    "                                <a href='beattobeat.online' class=\"black-button\">VISIT BEATTOBEAT.ONLINE</a>\n" +
    "                            </td>\n" +
    "                        </tr>\n" +
    "\n" +
    "                        <tr>\n" +
    "                            <td class=\"text-center\">\n" +
    "                               ©\n" +
    "                               " + Year.now().getValue() + "\n" +
    "                            </td>\n" +
    "                        </tr>\n" +
    "                    </tbody>\n" +
    "                </table>\n" +
    "\n" +
    "            </div>\n" +
    "        </html>";

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

            // Set the basic authentication header
            String authHeader = "Basic " + Base64.getEncoder().encodeToString(("apikey:" + apiKey).getBytes(StandardCharsets.UTF_8));
            con.setRequestProperty("Authorization", authHeader);

            // Set the request parameters
            String urlParams = "apikey=" + apiKey + "&from=" + from + "&fromName=" + senderName + "&subject=" + subject + "&bodyHtml=" + body + "&to=" + toRecipient;

            // Send the post request
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(urlParams.getBytes());
            os.flush();
            os.close();

            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                return true;
            } else {
                System.out.println("Request failed with response code: " + responseCode);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return true;
    }
}
