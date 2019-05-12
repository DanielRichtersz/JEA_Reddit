package danielrichtersz.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import danielrichtersz.models.Post;

public class MessageEncoder implements Encoder.Text<Post> {

    private static Gson gson = new Gson();

    @Override
    public String encode(Post post) throws EncodeException {
        String json = gson.toJson(post);
        return json;
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