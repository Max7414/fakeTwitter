import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Twitter twitter;
        try {
            twitter = Twitter.loadFromFile("data.ser");
            System.out.println("成功載入資料");
        } catch (IOException | ClassNotFoundException e) {
            twitter = new Twitter();
            System.out.println("無先前資料，使用新資料");
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("歡迎使用X");
        while (true) {
            System.out.println("輸入您的身份(整數):");
            int id = sc.nextInt();
            System.out.println("################");
            System.out.println("[1]追蹤");
            System.out.println("[2]解除追蹤");
            System.out.println("[3]發文");
            System.out.println("[4]讀取文章");
            System.out.println("[5]讀取追蹤名單");
            System.out.println("[0]結束程式");
            System.out.println("################");
            System.out.println("選擇功能：");
            sc.nextLine();
            String s = sc.nextLine();


            switch (s) {
                case "1":
                    System.out.println("輸入要追蹤的對象");
                    int follow = sc.nextInt();
                    if (id == follow) {
                        System.out.println("不能追蹤自己");
                        break;
                    }
                    twitter.follow(id, follow);
                    System.out.println("用戶id:" + id + "成功追蹤 用戶id:" + follow);
                    break;

                case "2":
                    System.out.println("輸入解除追蹤的對象(整數)");
                    int unfollow = sc.nextInt();
                    try {
                        twitter.unfollow(id, unfollow);
                        System.out.println("用戶id:" + id + "以解除追蹤 用戶id:" + unfollow);
                    } catch (NullPointerException e) {
                        System.out.println("尚未追蹤此對象");
                    }
                    break;
                case "3":
                    Random r = new Random();

                    int tweetId = r.nextInt();

                    System.out.println("請輸入發文內容");
                    String context = sc.nextLine();
                    twitter.postTweet(id, tweetId, context);
                    System.out.println("成功發文！");
                    break;
                case "4":
                    System.out.println("Id:"+id+"的推文列表");
                    try {
                        System.out.println(twitter.getNewsFeed(id));
                    }
                    catch (Exception e)
                    {
                        System.out.println("未有任何推文");
                    }
                    break;
                case "5":
                    try {
                        System.out.println("用戶id:" + id + "追蹤清單:" + twitter.followerList(id).toString());
                    } catch (Exception e) {
                        System.out.println("尚未追蹤任何人");
                    }
                    break;
                case "0":
                    System.exit(0);
                default:
                    System.out.println("Unexpected value: " + s);
            }
            twitter.toSave();
        }


    }
}
