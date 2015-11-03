package com.issinc.pialamodes.service;

import com.issinc.pialamodes.ServerApplication;
import com.issinc.pialamodes.domain.Aircraft;
import com.issinc.pialamodes.persistence.AircraftRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 *  Created by jay.moss on 10/30/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServerApplication.class)
public class AircraftServiceTest extends TestCase {

    @Autowired
    IAircraftService service;
    @Autowired
    AircraftRepository repo;

    @Before
    public void setUp() {
        repo.deleteAll();
        Aircraft plane1 = new Aircraft("tail-1", "piper-cub");
        service.create(plane1);
    }

    @Test
    public void testCreate() throws Exception {
        Aircraft src = new Aircraft("tail-2", "piper-bear");
        Aircraft ret = service.create(src);
        assertNotNull("Retrieved Object should not be null", ret);
        assertNotNull("Retrieved Object Id should not be null", ret.getId());
        assertEquals("tailNumber should match", src.getTailNumber(), ret.getTailNumber());
        assertEquals("type should match", src.getType(), ret.getType());
    }

    @Test
    public void testCreate1() throws Exception {
        Aircraft ret = service.create("tail-3", "piper-peter");
        assertNotNull("Retrieved Object should not be null", ret);
        assertNotNull("Retrieved Object Id should not be null", ret.getId());
        assertEquals("tailNumber should match", "tail-3", ret.getTailNumber());
        assertEquals("type should match", "piper-peter", ret.getType());
    }

    @Test
    public void testFind() throws Exception {
        List<Aircraft> dbList = service.find();
        assertNotNull("Retrieved Object List should not be null", dbList);
        assertTrue("Should find one object", dbList.size() == 1);
    }

    @Test
    public void testFindByTailNumber() throws Exception {
        Aircraft ret = service.findByTailNumber("tail-1");
        assertNotNull("Retrieved Object should not be null", ret);
        assertEquals("tailNumber should match", "tail-1", ret.getTailNumber());
        assertEquals("type should match", "piper-cub", ret.getType());
    }
}