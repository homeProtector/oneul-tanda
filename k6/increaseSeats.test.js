import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    vus: 100,
    duration: '10s',
};

const token = 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZXIiLCJyb2xlIjoiQURNSU4iLCJpZCI6ImIzYTg5MmE3LWFiYmUtNDZlYi04MWYyLTZlYTQyMWNlOGQzMiIsInRva2VuVmVyc2lvbiI6MSwiaXNzIjoib25ldWwtdGFuZGEiLCJpYXQiOjE3NDg1MTAzNTUsImV4cCI6MTc0ODUxMjE1NX0.XtfYAqs5y-P_QhzgDEjnTfvQO19KdW6yGu3oh60Ox5Y'; // 반드시 갱신 필요
const flightId = '2129373a-1741-45a4-a2b0-56914adcc906';

export default function () {
    const url = `http://localhost:19091/internal/v1/flights/${flightId}/seats/increase?requiredSeats=1`;

    const headers = {
        Authorization: token,
    };

    let res = http.put(url, null, { headers });

    const success = check(res, {
        'status is 200': (r) => r.status === 200,
    });

    if (!success) {
        console.error(`❌ 요청 실패 | 상태코드: ${res.status} | 응답: ${res.body}`);
    }
}
