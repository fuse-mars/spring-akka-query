package com.nshimiye.domain;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class Database{

    private static Map<Long, Spending> db = new ConcurrentHashMap<>();

    public static void write(Spending spending) {
        db.put(spending.getId(), spending);
    }

    public static Spending read(Long key) {
        return db.get(key);
    }
}