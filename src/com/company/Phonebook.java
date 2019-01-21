package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1 on 20.01.2019.
 */
public class Phonebook {
    private Map<String, List<String>> phoneList = new HashMap<>();

    public void add(String surname, String phone) {
        if (phoneList.get(surname) != null) {
            phoneList.get(surname).add(phone);
            return;
        }
        phoneList.put(surname, new ArrayList<String>() {{add(phone);}});
    }

    public List<String> get(String surname) {
        return phoneList.get(surname);
    }
}
