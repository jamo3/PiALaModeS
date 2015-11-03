package com.issinc.pialamodes.service;

import com.issinc.pialamodes.ServerApplication;
import com.issinc.pialamodes.domain.Callsign;
import com.issinc.pialamodes.persistence.CallsignRepository;
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
public class CallsignServiceTest extends TestCase {

    @Autowired
    ICallsignService service;
    @Autowired
    CallsignRepository repo;

    @Before
    public void setUp() {
        repo.deleteAll();
        Callsign w100 = new Callsign("abc123", "whiskey-100");
        service.create("abc123", "whiskey-100");
    }

    @Test
    public void testCreateObj() throws Exception {
        Callsign src = new Callsign("def456", "tail-2");
        Callsign ret = service.create(src);
        assertNotNull("Retrieved Object should not be null", ret);
        assertEquals("hexIdent should match", src.getHexIdent(), ret.getHexIdent());
        assertEquals("tailNumber should match", src.getCallsign(), ret.getCallsign());
        assertNotNull("timestamp should not be null", ret.getLastModifiedDate());
    }

    @Test
    public void testCreate() throws Exception {
        Callsign ret = service.create("ghi789", "bravo-200");
        assertNotNull("Retrieved Object should not be null", ret);
        assertEquals("hexIdent should match", "ghi789", ret.getHexIdent());
        assertEquals("callsign should match", "bravo-200", ret.getCallsign());
        assertNotNull("timestamp should not be null", ret.getLastModifiedDate());
    }

    @Test
    public void testFind() throws Exception {
        List<Callsign> dbList = service.find();
        assertNotNull("Retrieved Object List should not be null", dbList);
        assertTrue("Should find one object", dbList.size() == 1);
    }

    @Test
    public void testFindByCallsign() throws Exception {
        Callsign ret = service.findByCallsign("whiskey-100");
        assertNotNull("Retrieved Object should not be null", ret);
        assertEquals("hexIdent should match", "abc123", ret.getHexIdent());
        assertEquals("callsign should match", "whiskey-100", ret.getCallsign());
        assertNotNull("timestamp should not be null", ret.getLastModifiedDate());
    }
}