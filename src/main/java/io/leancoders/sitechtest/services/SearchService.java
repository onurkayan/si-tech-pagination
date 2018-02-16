package io.leancoders.sitechtest.services;

import io.leancoders.sitechtest.domain.Page;
import io.leancoders.sitechtest.domain.Result;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface SearchService {

    Optional<Page<Result>> getPage(int page, int size, Collection<Result> results) throws Exception;

    Collection<Result> getResults() throws Exception;
}
