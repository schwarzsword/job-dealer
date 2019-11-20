import axios from 'axios';

export const urlPort = axios.create({
    baseURL: "http://localhost:8080",
    withCredentials: true,

});