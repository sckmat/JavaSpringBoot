package com.yegorov.Lab1.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class HelloController {

    private ArrayList<String> arrayList;
    private HashMap<Integer, String> hashMap;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/update-array")
    public ArrayList<String> updateArrayList(@RequestParam(value = "s") String s) {
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        arrayList.add(s);
        return arrayList;
    }

    @GetMapping("/show-array")
    public ArrayList<String> showArrayList() {
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        return arrayList;
    }

    @GetMapping("/update-map")
    public HashMap<Integer, String> updateHashMap(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "value") String value) {
        if (hashMap == null) {
            hashMap = new HashMap<>();
        }
        hashMap.put(id, value);
        return hashMap;
    }

    @GetMapping("/show-map")
    public HashMap<Integer, String> showHashMap() {
        if (hashMap == null) {
            hashMap = new HashMap<>();
        }
        return hashMap;
    }

    @GetMapping("/show-all-length")
    public String showAllLength() {
        int arraySize = (arrayList == null) ? 0 : arrayList.size();
        int mapSize = (hashMap == null) ? 0 : hashMap.size();

        return String.format("ArrayList содержит %d элементов, HashMap содержит %d элементов.",
                arraySize, mapSize);
    }
}
