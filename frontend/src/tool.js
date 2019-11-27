import axios from 'axios';

export const urlPort = axios.create({
    baseURL: "http://localhost:8080/api/0.1",
    withCredentials: true,
});