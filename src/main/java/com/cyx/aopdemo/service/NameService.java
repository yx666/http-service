package com.cyx.aopdemo.service;

import com.cyx.aopdemo.model.User;

import java.util.List;

public interface NameService {

    String getName(String name);

    String getAge(String name);

    User getUser(String name,String age);

    void getUserVoid(String name,String age);

    List<User> getUserList(String name, String age);
}
