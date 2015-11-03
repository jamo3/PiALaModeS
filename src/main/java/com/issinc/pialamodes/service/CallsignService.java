package com.issinc.pialamodes.service;

import com.issinc.pialamodes.domain.Callsign;
import com.issinc.pialamodes.persistence.CallsignRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  Created by jay.moss on 10/30/2015.
 */
@Service
public class CallsignService implements ICallsignService  {

    private CallsignRepository callsignRepo;
    private static final Logger log = LoggerFactory.getLogger(CallsignService.class);

    @Autowired
    public CallsignService(CallsignRepository repo) {
        this.callsignRepo = repo;
    }

    @Override
    @Transactional
    public Callsign create(Callsign callsign) {
        Callsign result;

        Callsign existing = callsignRepo.findOne(callsign.getHexIdent());
        if (existing != null) {
            result = existing;
        }
        else {
            result = callsignRepo.save(callsign);
        }
        return result;
    }

    @Override
    @Transactional
    public Callsign create(String hexIdent, String callsign) {
        Callsign result;
        result = callsignRepo.save(new Callsign(hexIdent, callsign));
        return result;
    }

    @Override
    @Transactional
    public List<Callsign> find() {
        return callsignRepo.findAll();
    }

    @Override
    @Transactional
    public Callsign findByCallsign(String callsign) {
        return callsignRepo.findByCallsign(callsign);
    }
}
