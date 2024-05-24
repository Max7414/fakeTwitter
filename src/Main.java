public class Main {
    public static void main(String[] args)
    {
        Twitter twitter = new Twitter();
        twitter.postTweet(1,5);
        twitter.postTweet(2,3);
        twitter.postTweet(1,101);
        twitter.postTweet(2,13);
        twitter.postTweet(2,10);
        twitter.postTweet(1,2);
        twitter.postTweet(1,94);
        twitter.postTweet(2,505);
        twitter.postTweet(1,333);
        twitter.postTweet(2,22);
        twitter.postTweet(1,11);
        twitter.postTweet(1,205);
        twitter.postTweet(2,203);
        twitter.postTweet(1,201);
        twitter.postTweet(2,213);
        twitter.postTweet(1,200);
        twitter.postTweet(2,202);
        twitter.postTweet(1,204);
        twitter.postTweet(2,208);
        twitter.postTweet(2,233);
        twitter.postTweet(1,222);
        twitter.postTweet(2,211);

        System.out.println(twitter.getNewsFeed(1));
        twitter.follow(1,2);
        System.out.println(twitter.getNewsFeed(1));
        twitter.unfollow(1,2);
        System.out.println(twitter.getNewsFeed(1));

    }
}
