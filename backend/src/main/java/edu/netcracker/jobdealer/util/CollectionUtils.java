package edu.netcracker.jobdealer.util;

import edu.netcracker.jobdealer.entity.Applicant;

import java.util.List;

public class CollectionUtils {
    public static boolean contains(List<Applicant> list, Applicant applicant) {
        return list.stream().anyMatch(e -> e.equals(applicant));
    }
}
