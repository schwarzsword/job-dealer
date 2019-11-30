import axios from 'axios';

export const urlPort = axios.create({
    baseURL: "http://localhost:8080/api/0.1",
    withCredentials: true,
});

export class Filters {
    constructor(limit, offset, money, requestedSkills, vacancyName, companyName, sortBy, descending) {
        this.limit = limit;
        this.offset = offset;
        this.money = money;
        this.requestedSkills = requestedSkills;
        this.vacancyName = vacancyName;
        this.companyName = companyName;
        this.sortBy = sortBy;
        this.descending = descending;
    }
}