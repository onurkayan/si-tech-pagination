package io.leancoders.sitechtest.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ResultTest {

    Result result;

    @Before
    public void setUp() throws Exception {
        result = new Result();
    }

    @Test
    public void getHref() {
        String href = "https://link/to/result";
        result.setHref(href);
        assertEquals(href, result.getHref());
    }

    @Test
    public void getTitle() {
        String title = "Document title";
        result.setTitle(title);
        assertEquals(title, result.getTitle());
    }

    @Test
    public void getSummary() {
        String summary = "Document summary";
        result.setSummary(summary);
        assertEquals(summary, result.getSummary());
    }
}