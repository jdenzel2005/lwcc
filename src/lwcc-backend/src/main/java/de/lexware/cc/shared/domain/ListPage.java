package de.lexware.cc.shared.domain;

import java.util.List;

public record ListPage<T>(
    List<T> content,
    long total
) {

}
