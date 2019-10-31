package edu.netcracker.jobdealer.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import edu.netcracker.jobdealer.service.JsonService;
import org.springframework.stereotype.Service;

@Service("jsonService")
public class JsonServiceImpl implements JsonService {
    @Override
    public String toJson(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Hibernate5Module());
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}