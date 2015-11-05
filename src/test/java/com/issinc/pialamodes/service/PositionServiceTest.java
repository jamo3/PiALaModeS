package com.issinc.pialamodes.service;

import com.issinc.pialamodes.ServerApplication;
import com.issinc.pialamodes.domain.Position;
import com.issinc.pialamodes.persistence.PositionRepository;
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
 *  Created by jay.moss on 11/4/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServerApplication.class)
public class PositionServiceTest extends TestCase {

    @Autowired
    IPositionService service;
    @Autowired
    PositionRepository repo;

    @Before
    public void setUp() throws InterruptedException {
        Position p001 = new Position("abc123", 25.0D, 25.0D, 90.0D, 123.0D, 3.0D);
        service.create(p001);
        // Thread.sleep(500L);
        Position p002 = new Position("abc123", 25.1D, 25.51D, 90.1D, 123.1D, 3.1D);
        service.create(p002);
    }

    @After
    public void cleanUp() {
        // repo.deleteAll();
        repo.deleteByHexIdent("abc123");
    }

    @Test
    public void testCreate() throws Exception {
        Position src = new Position("abc123", 25.2D, 25.2D, 90.2D, 123.2D, 3.2D);
        Position ret = service.create(src);
        assertNotNull("Retrieved Object should not be null", ret);
        assertEquals("hexIdent should match", src.getHexIdent(), ret.getHexIdent());
        assertNotNull("timestamp should not be null", ret.getTimestamp());
        assertEquals("lat should match", src.getLat(), ret.getLat());
        assertEquals("lon should match", src.getLon(), ret.getLon());
        assertEquals("heading should match", src.getHeading(), ret.getHeading());
        assertEquals("ground speed should match", src.getGroundSpeed(), ret.getGroundSpeed());
    }

    @Test
    public void testCreateFromParams() throws Exception {
        Position ret = service.create("abc123", 25.3D, 25.3D, 90.3D, 223.3D, 3.3D);
        assertNotNull("Retrieved Object should not be null", ret);
        assertEquals("hexIdent should match", "abc123", ret.getHexIdent());
        assertNotNull("timestamp should not be null", ret.getTimestamp());
        assertEquals("lat should match", 25.3D, ret.getLat());
        assertEquals("lon should match", 25.3D, ret.getLon());
        assertEquals("heading should match", 90.3D, ret.getHeading());
        assertEquals("ground speed should match", 223.3D, ret.getGroundSpeed());
        assertEquals("vertical rate should match", 3.3D, ret.getVerticalRate());
    }

    @Test
    public void testFind() throws Exception {
        List<Position> dbList = service.find();
        assertNotNull("Retrieved Object List should not be null", dbList);
        assertTrue("Should find two object", dbList.size() == 2);
    }

    public void testFindLast() throws Exception {

    }
}