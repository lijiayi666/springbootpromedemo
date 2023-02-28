package com.lijiayi.springbootpromedemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
class SpringbootpromedemoApplicationTests {

    @Test
    void contextLoads() {
        Random random = new Random();
        int i = random.nextInt(2 -1 +1);
        System.out.println("************  " + i);
    }

}
