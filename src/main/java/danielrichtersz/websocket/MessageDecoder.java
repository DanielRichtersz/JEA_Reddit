package danielrichtersz.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import danielrichtersz.models.Post;

public class MessageDecoder implements Decoder.Text<Post> {

    private static Gson gson = new Gson();

    @Override
    public Post decode(String s) throws DecodeException {
        Post post = gson.fromJson(s, Post.class);
        return post;
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }
}