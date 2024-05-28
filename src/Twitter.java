import java.io.*;
import java.util.*;

class NewsAndTime implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    int newsId;
    int time;

    public String getContext() {
        return context;
    }

    private final String context;

    NewsAndTime(int newsId, int time, String context) {
        this.newsId = newsId;
        this.time = time;
        this.context = context;
    }
}

class Twitter implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    LinkedList<HashSet<Integer>> follower = new LinkedList<>();
    HashSet<Integer> followerInner = new HashSet<>();
    HashMap<Integer, HashSet<Integer>> mapInner = new HashMap<>();



    HashMap<Integer, LinkedList<NewsAndTime>> newsFeeds = new HashMap<>();
    Map<Integer, LinkedList<NewsAndTime>> idToNewsInnerMap = new HashMap<>();
    transient PriorityQueue<NewsAndTime> newsSorted = new PriorityQueue<>(
            (n1, n2) -> n2.time - n1.time);//transient 不會被序列化


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

    public void postTweet(int userId, int tweetId, String context) {
        for (int i = 0; i <= userId; i++)
            init.add(0);
        if (init.get(userId) == 0)
            follow(userId, userId);
        init.set(userId, 1);
        idToNewsInnerMap.computeIfAbsent(userId, k -> new LinkedList<>()).add(new NewsAndTime(tweetId, time++,context));
        newsFeeds.put(userId, idToNewsInnerMap.get(userId));
    }


public List<String> getNewsFeed(int userId) {
    List<String> newsFeedList = new ArrayList<>();
    newsSorted = new PriorityQueue<>(
            (n1, n2) -> n2.time - n1.time);

        int count = 0;
        if (follower.isEmpty())
            return new LinkedList<>();
        for (var i : follower.get(userId)) {
            LinkedList<NewsAndTime> newsList = newsFeeds.get(i);
            if (newsList != null) {
                newsSorted.addAll(newsList);
            }
        }

        while (!newsSorted.isEmpty() && count < getMAX()) {
            newsFeedList.add(Objects.requireNonNull(newsSorted.poll()).getContext());
            count++;
        }

        return newsFeedList;
    }

    public void follow(int followerId, int followeeId) {
        followerInner = mapInner.getOrDefault(followerId, new HashSet<>());
        followerInner.add(followeeId);
        mapInner.put(followerId, followerInner);
        if (followerId >= follower.size())
            for (int i = 0; i < followerId + 1; i++) {
                follower.add(new HashSet<>());
            }

        follower.set(followerId, mapInner.get(followerId));
    }

    public void unfollow(int followerId, int followeeId) {
        if (followerId != followeeId) {
            mapInner.get(followerId).remove(followeeId);
            follower.set(followerId, mapInner.get(followerId));
        }
    }
    public HashSet<Integer> followerList(int id) {
        return follower.get(id);
    }
    public void toSave()
    {
        try(FileOutputStream fos = new FileOutputStream("data.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static Twitter loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Twitter) ois.readObject();
        }
    }
}

