package io.leancoders.sitechtest.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@Data
public class Page<T> {

    private Collection<T> content;
    private boolean first;
    private boolean last;
    private int totalElements;
    private int totalPages;
    private int size;
    private int pageNumber;
    private int numberOfElements;

}
