package io.leancoders.sitechtest.services;

import io.leancoders.sitechtest.domain.Page;
import io.leancoders.sitechtest.domain.Result;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

public class SearchServiceImplTest {

    SearchService searchService;
    Collection<Result> results;

    @Before
    public void setUp() throws Exception {
        searchService = new SearchServiceImpl();
        results = searchService.getResults();
        assertEquals(10,results.size());
    }

    @Test
    public void getEmptyResults() throws Exception{
        Optional<Page<Result>> pageOptional = searchService.getPage(1,10,null);
        assertFalse(pageOptional.isPresent());
    }

    @Test
    public void getInvalidPage() throws Exception{
        Optional<Page<Result>> pageOptional = searchService.getPage(2,10,results);
        assertFalse(pageOptional.isPresent());
    }

    @Test
    public void getPage1of1() throws Exception{

        Optional<Page<Result>> pageOptional = searchService.getPage(1,10,results);
        assertTrue(pageOptional.isPresent());
        Page page = pageOptional.get();
        assertEquals(results,page.getContent());
        assertTrue(page.isFirst());
        assertTrue(page.isLast());
        assertEquals(10,page.getTotalElements());
        assertEquals(1,page.getTotalPages());
    }

    @Test
    public void getPage1of2() throws Exception{
        int pageNumber=1;
        int size=5;
        Optional<Page<Result>> pageOptional = searchService.getPage(pageNumber,size,results);
        assertTrue(pageOptional.isPresent());
        Page page = pageOptional.get();
        List<Result> expected = new ArrayList<>(results);
        makeAssertions(page,expected.subList(0,5),true,false,5,
                results.size(),2,pageNumber,size);
    }

    @Test
    public void getPage2of2() throws Exception{
        int pageNumber=2, size=5;
        Optional<Page<Result>> pageOptional = searchService.getPage(pageNumber,size,results);
        assertTrue(pageOptional.isPresent());
        Page page = pageOptional.get();
        List<Result> expected = new ArrayList<>(results);
        makeAssertions(page,expected.subList(5,10),false,true,5,results.size(),2,
                pageNumber,size);
    }

    @Test
    public void getPage1of4() throws Exception{
        int pageNumber=1, size=3;
        Optional<Page<Result>> pageOptional = searchService.getPage(pageNumber, size, results);
        assertTrue(pageOptional.isPresent());
        Page page = pageOptional.get();
        List<Result> expected = new ArrayList<>(results);
        makeAssertions(page,expected.subList(0,3),true,false,3,results.size(),4,
                pageNumber,size);
    }

    @Test
    public void getPage2of4() throws Exception{
        int pageNumber=2, size=3;
        Optional<Page<Result>> pageOptional = searchService.getPage(pageNumber, size, results);
        assertTrue(pageOptional.isPresent());
        Page page = pageOptional.get();
        List<Result> expected = new ArrayList<>(results);
        makeAssertions(page,expected.subList(3,6),false,false,3,results.size(),4,
                pageNumber,size);
    }

    @Test
    public void getPage3of4() throws Exception{
        int pageNumber=3, size=3;
        Optional<Page<Result>> pageOptional = searchService.getPage(pageNumber, size, results);
        assertTrue(pageOptional.isPresent());
        Page page = pageOptional.get();
        List<Result> expected = new ArrayList<>(results);
        makeAssertions(page,expected.subList(6,9),false,false,3,results.size(),4,
                pageNumber,size);
    }

    @Test
    public void getPage4of4() throws Exception{
        int pageNumber=4, size=3;
        Optional<Page<Result>> pageOptional = searchService.getPage(pageNumber, size, results);
        assertTrue(pageOptional.isPresent());
        Page page = pageOptional.get();
        List<Result> expected = new ArrayList<>(results);
        makeAssertions(page,expected.subList(9,10),false,true,1,results.size(),4,
                pageNumber,size);
    }

    private void makeAssertions(Page page, Collection<Result> content,
                                boolean first, boolean last, int numberOfElements,
                                int totalElements, int totalPages,int pageNumber,int size) {
        assertEquals(content,page.getContent());
        assertEquals(first,page.isFirst());
        assertEquals(last,page.isLast());
        assertEquals(numberOfElements,page.getNumberOfElements());
        assertEquals(totalElements,page.getTotalElements());
        assertEquals(totalPages,page.getTotalPages());
        assertEquals(pageNumber,page.getPageNumber());
        assertEquals(size,page.getSize());
    }


    private Collection<Result> getResults() {
        Collection<Result> results = new ArrayList<>();
        results.add(new Result("https://link/to/result","title","summary"));
        results.add(new Result("https://link/to/result2","title2","summary2"));
        results.add(new Result("https://link/to/result3","title3","summary3"));
        results.add(new Result("https://link/to/result4","title4","summary4"));
        results.add(new Result("https://link/to/result5","title5","summary5"));
        results.add(new Result("https://link/to/result6","title6","summary6"));
        results.add(new Result("https://link/to/result7","title7","summary7"));
        results.add(new Result("https://link/to/result8","title8","summary8"));
        results.add(new Result("https://link/to/result9","title9","summary9"));
        results.add(new Result("https://link/to/result10","title10","summary10"));

        return results;
    }
}