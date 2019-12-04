package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Response;

import java.util.List;
import java.util.UUID;

public interface ResponseService {

    List<Response> getRespondents(UUID vacancyId, String email);

    List<Response> getResponses(String email);

    void apply(UUID vacancyId, String email);

    boolean isApplied(UUID id, String email);

    void setStatus(UUID id, String status, String email);
}
