package net.dingyabin;

import net.wecash.utils.HTTPBuilder;
import net.wecash.utils.HTTPClient;
import org.apache.http.message.BasicHeader;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MrDing
 * Date: 2020/6/1.
 * Time:14:48
 */
public class Test58 {


    public static void main(String[] args) {

//        int[] array = {2, 1, 5, 4, 7, 8, 9, 22, 11, 3, 98};
//
//        for (int i = 0; i < array.length - 1; i++) {
//            for (int j = 0; j < array.length - i - 1; j++) {
//                if (array[j] % 2 == 0 && array[j + 1] % 2 == 1) {
//                    int tmp = array[j];
//                    array[j] = array[j + 1];
//                    array[j + 1] = tmp;
//                }
//            }
//        }
//        System.out.println(Arrays.toString(array));



//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://grammarbot.p.rapidapi.com/check"))
//                .header("content-type", "application/x-www-form-urlencoded")
//                .header("x-rapidapi-key", "edc747db03mshc61fd6fc984d647p18b491jsncb76fd44fa05")
//                .header("x-rapidapi-host", "grammarbot.p.rapidapi.com")
//                .method("POST", HttpRequest.BodyPublishers.ofString("text=Susan%20go%20to%20the%20store%20everyday&language=en-US"))
//                .build();
//        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());


//        HTTPBuilder build = new HTTPBuilder().url("https://grammarbot.p.rapidapi.com/check?text=Susan%20go%20to%20the%20store%20everyday&language=en-US")
//                .headers(
//                        new BasicHeader[]{
//                                new BasicHeader("content-type", "application/x-www-form-urlencoded"),
//                                new BasicHeader("x-rapidapi-host", "grammarbot.p.rapidapi.com"),
//                                new BasicHeader("x-rapidapi-key", "edc747db03mshc61fd6fc984d647p18b491jsncb76fd44fa05")}
//                )
//                .build();
//
//        String post = HTTPClient.post(build);
//        System.out.println(post);


        String ss= "<p style=\"color: rgb(0, 0, 0); font-size: medium; text-align: start; white-space: normal; visibility: visible;\"><span style=\"font-size: 15px; visibility: visible;\">1.鼠标右击【解压到 Office2021(64bit)】。</span></p>";
        Pattern codePompile = Pattern.compile(">([a-zA-Z0-9_.。\\u4e00-\\u9fa5]+?)</");
        Matcher matcher = codePompile.matcher(ss);
        while (matcher.find()){
            String group = matcher.group(1);
            System.out.println("地址:    "+group);
        }

    }


}
