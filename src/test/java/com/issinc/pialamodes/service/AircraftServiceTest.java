package com.issinc.pialamodes.service;

import com.issinc.pialamodes.ServerApplication;
import com.issinc.pialamodes.domain.Aircraft;
import com.issinc.pialamodes.persistence.AircraftRepository;
import junit.framework.TestCase;
import org.junit.After;
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
        Aircraft plane1 = new Aircraft("abc123", "tail-1", "piper-cub");
        service.create(plane1);
    }

    @After
    public void cleanUp() {
        repo.delete("abc123");
    }

    @Test
    public void testCreateDuplicate() throws Exception {
        Aircraft src = new Aircraft("abc123", "tail-1", "piper-cub");
        Aircraft ret = service.create(src);
        assertNotNull("Retrieved Object should not be null", ret);
        assertEquals("hexIdent should match", src.getHexIdent(), ret.getHexIdent());
        assertEquals("tailNumber should match", src.getTailNumber(), ret.getTailNumber());
        assertEquals("type should match", src.getType(), ret.getType());
    }

    @Test
    public void testCreate() throws Exception {
        Aircraft src = new Aircraft("def456", "tail-2", "piper-bear");
        Aircraft ret = service.create(src);
        assertNotNull("Retrieved Object should not be null", ret);
        assertEquals("hexIdent should match", src.getHexIdent(), ret.getHexIdent());
        assertEquals("tailNumber should match", src.getTailNumber(), ret.getTailNumber());
        assertEquals("type should match", src.getType(), ret.getType());
        repo.delete("def456");
    }

    @Test
    public void testCreate1() throws Exception {
        Aircraft ret = service.create("ghi789", "tail-3", "piper-peter");
        assertNotNull("Retrieved Object should not be null", ret);
        assertEquals("hexIdent should match", "ghi789", ret.getHexIdent());
        assertEquals("tailNumber should match", "tail-3", ret.getTailNumber());
        assertEquals("type should match", "piper-peter", ret.getType());
        repo.delete("ghi789");
    }

    @Test
    public void testCreateDuplicate1() throws Exception {
        Aircraft ret = service.create("abc123", "tail-1", "piper-cub");
        assertNotNull("Retrieved Object should not be null", ret);
        assertEquals("hexIdent should match", "abc123", ret.getHexIdent());
        assertEquals("tailNumber should match", "tail-1", ret.getTailNumber());
        assertEquals("type should match", "piper-cub", ret.getType());
    }

    @Test
    public void testFindAll() throws Exception {
        List<Aircraft> dbList = service.find();
        assertNotNull("Retrieved Object List should not be null", dbList);
        assertTrue("Should find one object", dbList.size() == 1);
    }

    @Test
    public void testFindById() throws Exception {
        Aircraft ret = service.findByHexIdent("abc123");
        assertNotNull("Retrieved Object should not be null", ret);
        assertEquals("hexIdent should match", "abc123", ret.getHexIdent());
        assertEquals("tailNumber should match", "tail-1", ret.getTailNumber());
        assertEquals("type should match", "piper-cub", ret.getType());
    }

    @Test
    public void testFindByTailNumber() throws Exception {
        Aircraft ret = service.findByTailNumber("tail-1");
        assertNotNull("Retrieved Object should not be null", ret);
        assertEquals("hexIdent should match", "abc123", ret.getHexIdent());
        assertEquals("tailNumber should match", "tail-1", ret.getTailNumber());
        assertEquals("type should match", "piper-cub", ret.getType());
    }
}