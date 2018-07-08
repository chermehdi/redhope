package com.mql.redhope.buisness;

import com.mql.redhope.models.EventPost;
import java.util.List;
import javax.json.JsonObject;


/**
 * @author mehdithe
 */
public interface EventPostService {

  List<EventPost> getAllPosts();

  EventPost createEventPost(JsonObject content);

  List<EventPost> getPaginated(int offset, int size);
}
