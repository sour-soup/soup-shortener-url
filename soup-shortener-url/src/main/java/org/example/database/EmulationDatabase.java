package org.example.database;

import java.util.HashMap;

public class EmulationDatabase {
    private static EmulationDatabase instance;
    private final HashMap<Long, String> linksMap;
    private final HashMap<String, Long> idMap;

    private EmulationDatabase() {
        linksMap = new HashMap<>();
        idMap = new HashMap<>();
    }

    public static synchronized EmulationDatabase getInstance() {
        if (instance == null) instance = new EmulationDatabase();
        return instance;
    }

    public void addLink(Long id, String link) {
        getInstance().linksMap.put(id, link);
        getInstance().idMap.put(link, id);
    }

    public String getLink(Long id) {
        return getInstance().linksMap.get(id);
    }

    public Long getId(String link) {
        return getInstance().idMap.get(link);
    }

    public Boolean checkLink(String link) {
        return getInstance().idMap.containsKey(link);
    }

    public Boolean checkId(Long id) {
        return getInstance().linksMap.containsKey(id);
    }
}
