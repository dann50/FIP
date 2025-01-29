package com.example.week3.service;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;

@Service
public class DemoService {

    final Set<String> names = new TreeSet<>();

    public boolean add(final String name) {
        return names.add(name);
    }

    public Set<String> getNames() {
        return names;
    }

    public void remove(final String name) {
        names.remove(name);
    }
}
