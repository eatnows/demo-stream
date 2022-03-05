package me.eatnows.streamdemo.maxmincount;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class User {
    private int id;
    private String name;
    private String emailAddress;
    private boolean isVerified;
    private LocalDateTime createdAt;
    private List<Integer> friendUserIds;

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public User setVerified(boolean verified) {
        isVerified = verified;
        return this;
    }

    public User setFriendUserIds(List<Integer> friendUserIds) {
        this.friendUserIds = friendUserIds;
        return this;
    }

    public User setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getEmailAddress() {
        return Optional.ofNullable(emailAddress);
    }

    public boolean isVerified() {
        return isVerified;
    }

    public List<Integer> getFriendUserIds() {
        return friendUserIds;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
