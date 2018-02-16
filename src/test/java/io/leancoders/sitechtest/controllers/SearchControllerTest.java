package io.leancoders.sitechtest.controllers;

import io.leancoders.sitechtest.domain.Page;
import io.leancoders.sitechtest.domain.Result;
import io.leancoders.sitechtest.exception.MyResourceNotFoundException;
import io.leancoders.sitechtest.services.SearchService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class SearchControllerTest {

    SearchController searchController;

    @Mock
    SearchService searchService;

    @Mock
    Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        searchController = new SearchController(searchService);
    }

    @Test
    public void name() {
    }

    @Test
    public void handlePagination() throws Exception{
        Collection<Result> results = new ArrayList<>();

        Page<Result> page = new Page();
        page.setFirst(true);
        page.setLast(true);
        page.setContent(results);
        Optional<Page<Result>> pageOptional = Optional.of(page);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
        when(searchService.getResults()).thenReturn(results);
        when(searchService.getPage(1,5,results)).thenReturn(pageOptional);

        mockMvc.perform(get("/search?page=1&size=5"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

        verify(searchService,times(1)).getResults();
        verify(searchService,times(1)).getPage(1,5,results);
    }

    @Test
    public void handlePaginationWithoutSize() throws Exception{
        Collection<Result> results = new ArrayList<>();

        Page<Result> page = new Page();
        page.setFirst(true);
        page.setLast(true);
        page.setContent(results);
        Optional<Page<Result>> pageOptional = Optional.of(page);

        int defaultSize = 20;
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
        when(searchService.getResults()).thenReturn(results);
        when(searchService.getPage(1,defaultSize,results)).thenReturn(pageOptional);

        mockMvc.perform(get("/search?page=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

        verify(searchService,times(1)).getResults();
        verify(searchService,times(1)).getPage(1,defaultSize,results);
    }

    @Test()
    public void testResourceNotFound() throws Exception{
        Collection<Result> results = new ArrayList<>();

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
        when(searchService.getResults()).thenReturn(results);
        when(searchService.getPage(1,20,results)).thenReturn(Optional.empty());

        try {
            mockMvc.perform(get("/search?page=1"));
        }catch (NestedServletException ex){
            assertTrue(ex.getMessage().contains("MyResourceNotFoundException"));
        }
    }
}