package test;

import business.ControllerInterface;
import business.SystemController;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestExample {
    @Test
    public void happyPathScenario(){
        ControllerInterface ci = new SystemController();
        List<String> results = ci.allMemberIds();
        assertEquals(4, results.size());
    }
}
