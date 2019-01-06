package com.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.domain.LogEntry;

import java.util.function.Function;

public class LogParser implements Function<String, LogEntry> {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    public LogEntry apply(String s) {
        return GSON.fromJson(s, LogEntry.class);
    }
}
