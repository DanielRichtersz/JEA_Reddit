package danielrichtersz.websocket;

import danielrichtersz.models.Post;
import danielrichtersz.models.Redditor;
import danielrichtersz.models.Subreddit;
import danielrichtersz.services.interfaces.RedditorService;
import danielrichtersz.services.interfaces.SubredditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Service
@ServerEndpoint(value = "/chat/{username}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class RedditPostEndpoint {

    @Autowired
    private SubredditService subredditService;

    @Autowired
    private RedditorService redditorService;

    private Session session;
    private static final Set<RedditPostEndpoint> REDDIT_POST_ENDPOINTS = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException, EncodeException {

        this.session = session;
        REDDIT_POST_ENDPOINTS.add(this);
        users.put(session.getId(), username);

        /*Subreddit foodSubreddit = subredditService.getByName("Food");
        Redditor owner = redditorService.findByUsername(username);
        Post post = new Post("Websocket Title Post", "Websocket Content Post", foodSubreddit, owner);

        broadcast(post);*/

        System.out.println("Session opened: " + username);
    }

    @OnMessage
    public void onMessage(Session session, Post post) throws IOException, EncodeException {
        List<Redditor> followers = null;
        try {
            String username = users.get(session.getId());
            System.out.println("Post created by: " + username);
            followers = subredditService.getFollowers(post.getSubreddit().getName());

            if (followers != null) {
                followers.add(new Redditor(username, "password"));
            }
            else {
                followers = new ArrayList<>();
                followers.set(0, new Redditor(username, "password"));
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        broadcast(post, followers);
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        REDDIT_POST_ENDPOINTS.remove(this);
        System.out.println("Closed session: " + users.get(session.getId()));
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        System.out.println("Websocket Error: Websocket " + users.get(session.getId()));
        throwable.printStackTrace();
    }

    private static void broadcast(Post message, List<Redditor> followers) throws IOException, EncodeException {
        REDDIT_POST_ENDPOINTS.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    if (isFollower(followers, users.get(endpoint.session.getId()))) {
                        endpoint.session.getBasicRemote()
                                .sendObject(message);
                    }
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static boolean isFollower(List<Redditor> followers, String username) {
        for (Redditor redditor : followers) {
            if (redditor.getUsername() == username) {
                return true;
            }
        }
        return false;
    }

}