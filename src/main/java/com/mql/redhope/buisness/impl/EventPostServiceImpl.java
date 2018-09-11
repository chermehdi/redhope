package com.mql.redhope.buisness.impl;

import com.mql.redhope.buisness.EventPostService;
import com.mql.redhope.dao.EventPostDao;
import com.mql.redhope.domain.models.EventPost;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import org.slf4j.Logger;

/**
 * @author mehdithe
 */
@Stateless
public class EventPostServiceImpl implements EventPostService {

  @Inject
  private EventPostDao eventPostDao;

  @Inject
  Logger logger;

  @Override
  public List<EventPost> getAllPosts() {
    return eventPostDao.findAll();
  }

  @Override
  public EventPost createEventPost(JsonObject content) {
    EventPost newEventPost = EventPost.of(content.getString("content"));
    eventPostDao.save(newEventPost);
    return newEventPost;
  }

  @Override
  public List<EventPost> getPaginated(int offset, int size) {
    List<EventPost> all = getAllPosts();
    if (all.size() <= offset) {
      return Collections.emptyList();
    }
    return all.subList(offset, Math.min(offset + size, all.size()));
  }
}
