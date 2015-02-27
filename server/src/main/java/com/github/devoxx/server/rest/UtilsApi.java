package com.github.devoxx.server.rest;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.wordnik.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by david.wursteisen on 26/02/2015.
 */
@Api("Utils method")
@RestController
public class UtilsApi {

    private final Random random = new SecureRandom();

    @RequestMapping(method = RequestMethod.GET, value = "/utils/random")
    public Integer random() {
        return random.nextInt(1000);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/utils/range/{start}/{end}")
    public List<Integer> range(@PathVariable("start") int start, @PathVariable("end") int end) {
        return IntStream.range(start, end).boxed().collect(Collectors.toList());
    }
}
