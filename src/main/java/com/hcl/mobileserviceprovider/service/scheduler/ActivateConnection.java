package com.hcl.mobileserviceprovider.service.scheduler;

import com.hcl.mobileserviceprovider.service.repository.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ActivateConnection {

    @Autowired
    ConnectionRepository connectionRepository;

    //For every 2 minutes
    @Scheduled(cron = "0 0/2 * * * ?")
    public void activateConnection() {
        connectionRepository.activateConnections();
    }

}
