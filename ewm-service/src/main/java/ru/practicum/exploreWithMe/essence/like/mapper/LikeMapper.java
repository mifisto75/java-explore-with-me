package ru.practicum.exploreWithMe.essence.like.mapper;

import ru.practicum.exploreWithMe.essence.event.model.Event;
import ru.practicum.exploreWithMe.essence.like.model.Like;
import ru.practicum.exploreWithMe.essence.user.model.User;

public class LikeMapper {
    public static Like newLike(Event event, User user, Boolean boll) {
        Like like = new Like();
        like.setEvent(event);
        like.setUser(user);
        like.setIsPositive(boll);
        return like;
    }
}
