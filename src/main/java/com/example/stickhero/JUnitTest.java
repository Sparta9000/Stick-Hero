package com.example.stickhero;

import org.junit.Test;
import static org.junit.Assert.fail;

public class JUnitTest {
    @Test
    public void testPillarGenerationX() {
        double x = Pillar.getNextPillarXCoord();
        if (x > StickHeroApplication.getWidth()) fail();
        if (x < 0) fail();
    }

    @Test
    public void testPillarGenerationWidth() {
        double x = Pillar.getNextPillarXCoord();
        double width = Pillar.getNextPillarWidth(x);
        if (width < 0) fail();
        if (width + x > StickHeroApplication.getWidth()) fail();
    }
}
