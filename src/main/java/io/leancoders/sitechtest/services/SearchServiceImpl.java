package io.leancoders.sitechtest.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.leancoders.sitechtest.domain.Page;
import io.leancoders.sitechtest.domain.Result;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

@Service
public class SearchServiceImpl implements SearchService {

    @Override
    public Collection<Result> getResults() throws Exception{
        //read json file data to String
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("search_results.txt").getFile());
        byte[] jsonData = Files.readAllBytes(file.toPath());

        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(file);

        JsonNode phoneNosNode = rootNode.path("results");
        Iterator<JsonNode> elements = phoneNosNode.elements();
        List<Result> results = new ArrayList<>();
        while(elements.hasNext()){
            Result result = objectMapper.convertValue(elements.next(),Result.class);
            results.add(result);
        }
        return results;
    }

    @Override
    public Optional<Page<Result>> getPage(int page, int size, Collection<Result> results) throws Exception {
        Page<Result> returnPage = new Page();
        if(results == null || results.isEmpty()){
            return Optional.empty();
        }
        List<Result> list = new ArrayList<>(results);

        returnPage.setTotalPages((int) Math.ceil((double)results.size() / size)  );
        int beginIndex = (page-1) * size;
        int endIndex = beginIndex + size;
        endIndex = Math.min(endIndex,results.size());

        if(beginIndex > results.size() || beginIndex == endIndex){
            return Optional.empty();
        }
        returnPage.setContent(list.subList(beginIndex,endIndex));
        returnPage.setPageNumber(page);
        returnPage.setSize(size);
        returnPage.setFirst(page == 1);
        returnPage.setLast(page == returnPage.getTotalPages());
        returnPage.setNumberOfElements(returnPage.getContent().size());
        returnPage.setTotalElements(results.size());
        return Optional.of(returnPage);
    }
}
