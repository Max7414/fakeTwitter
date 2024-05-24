import java.util.*;

class NewsAndTime {
    int newsId;
    int time;
    NewsAndTime(int newsId, int time){
        this.newsId = newsId;
        this.time = time;
    }
}

class Twitter {
    LinkedList<HashSet<Integer>> follower = new LinkedList<>();
    HashSet<Integer> followerInner = new HashSet<>();
    HashMap<Integer,HashSet<Integer>> mapInner = new HashMap<>();
    HashMap<Integer,LinkedList<NewsAndTime>> newsFeeds = new HashMap<>();
    Map<Integer, LinkedList<NewsAndTime>> idToNewsInnerMap = new HashMap<>();
    PriorityQueue<NewsAndTime> newsSorted = new PriorityQueue<>(
            (n1, n2) -> n2.time - n1.time);


    public int getMAX() {
        return MAX;
    }

    private final int MAX;
    private int time;
    public Twitter() {
        this.MAX = 10;
        this.time = 0;
    }
    LinkedList<Integer> init = new LinkedList<>();

    public void postTweet(int userId, int tweetId) {
        for(int i =0;i<=userId;i++)
            init.add(0);
        if(init.get(userId)==0)
            follow(userId,userId);
        init.set(userId,1);
        idToNewsInnerMap.computeIfAbsent(userId,k -> new LinkedList<>()).add(new NewsAndTime(tweetId,time++));
        newsFeeds.put(userId,idToNewsInnerMap.get(userId));
    }

public List<Integer> getNewsFeed(int userId) {
    List<Integer> newsFeedList = new ArrayList<>();
    newsSorted = new PriorityQueue<>(
            (n1, n2) -> n2.time - n1.time);

    int count = 0;
    if(follower.isEmpty())
        return new LinkedList<>();
    for (var i : follower.get(userId)) {
        LinkedList<NewsAndTime> newsList = newsFeeds.get(i);
        if (newsList != null) {
            newsSorted.addAll(newsList);
        }
    }

    while (!newsSorted.isEmpty() && count<getMAX()) {
        newsFeedList.add(Objects.requireNonNull(newsSorted.poll()).newsId);
        count++;
    }

    return newsFeedList;
}

    public void follow(int followerId, int followeeId) {
            followerInner = mapInner.getOrDefault(followerId,new HashSet<>());
            followerInner.add(followeeId);
            mapInner.put(followerId,followerInner);
            if(followerId>=follower.size())
                for (int i = 0; i < followerId+1; i++) {
                    follower.add(new HashSet<>());
                }

            follower.set(followerId, mapInner.get(followerId));//如果沒有會報錯
    }

    public void unfollow(int followerId, int followeeId) {
        if(followerId != followeeId) {
            mapInner.get(followerId).remove(followeeId);
            follower.set(followerId, mapInner.get(followerId));
        }
    }
}

