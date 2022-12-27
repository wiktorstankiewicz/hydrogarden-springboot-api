package com.hydrogarden.server;


import com.hydrogarden.server.circuitSchedule.CircuitScheduleService;
import com.hydrogarden.server.user.User;
import com.hydrogarden.server.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class Tester {
    @Autowired
    private CircuitScheduleService circuitScheduleService;
    @Autowired
    private UserService userService;



}
