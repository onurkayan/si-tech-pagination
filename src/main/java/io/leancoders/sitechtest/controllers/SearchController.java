package io.leancoders.sitechtest.controllers;

import io.leancoders.sitechtest.domain.Page;
import io.leancoders.sitechtest.domain.Result;
import io.leancoders.sitechtest.exception.MyResourceNotFoundException;
import io.leancoders.sitechtest.services.SearchService;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class SearchController {

    private SearchService searchService;
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @RequestMapping(value = "/search",params = {"page"},produces = { "application/vnd.leancoders.v2+json" })
    public String getSearchResults(@RequestParam("page") int page,
                                   @RequestParam(value = "size",required = false,defaultValue = "20") int size,
                                   Model model) throws Exception{
        Optional<Page<Result>> pageOptional= searchService.getPage(page,size,searchService.getResults());
        if (!pageOptional.isPresent()) {
            throw new MyResourceNotFoundException("Requested page not found");
        }
        model.addAttribute("page",pageOptional.get());
        return "index";
    }
}
