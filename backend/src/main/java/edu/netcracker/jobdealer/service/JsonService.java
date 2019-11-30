package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.dto.Filters;

public interface JsonService {
    String toJson(Object o);

    Filters parseFilters(String s);
}