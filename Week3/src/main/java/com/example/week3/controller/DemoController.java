package com.example.week3.controller;

import com.example.week3.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class DemoController {

    private final DemoService demoService;

    @Autowired
    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/add")
    public Set<String> add(@RequestParam String name) {
        demoService.add(name);
        return getNames();
    }

    @GetMapping("/remove")
    public Set<String> remove(@RequestParam String name) {
        demoService.remove(name);
        return getNames();
    }

    @GetMapping("/list")
    public Set<String> getNames() {
        return demoService.getNames();
    }

    @GetMapping("/view/{name}")
    public String view(@PathVariable String name) {
        if (demoService.getNames().contains(name)) {
            return name;
        } else {
            return "Not found";
        }
    }
}
