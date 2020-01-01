package com.cyx.aopdemo.service.impl;

import com.cyx.aopdemo.annotation.FirmHttpService;
import com.cyx.aopdemo.model.User;
import com.cyx.aopdemo.service.NameService;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.List;

@FirmHttpService
@Service("nameServiceImpl")
public class NameServiceImpl implements NameService {


    @Override
    public String getName(String name) {
        return "NameServiceImpl.name:" + name;
    }

    @Override
    public String getAge(String age) {
        return "NameServiceImpl.age:" + age;
    }

    @Override
    public User getUser(String name,String age) {
        return new User(name,age);
    }

    @Override
    public void getUserVoid(String name, String age) {

    }

    @Override
    public List<User> getUserList(String name, String age) {
        return null;
    }
}
