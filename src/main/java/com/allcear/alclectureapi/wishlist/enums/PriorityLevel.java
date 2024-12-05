package com.allcear.alclectureapi.wishlist.enums;

public enum PriorityLevel {
    HIGH(1),
    MEDIUM(2),
    LOW(3);

    private final int priority;

    PriorityLevel(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}