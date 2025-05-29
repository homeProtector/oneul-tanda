import http from 'k6/http';
import { check, fail } from 'k6';

export let options = {
    vus: 100,
    duration: '10s',
};

const token = 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZXIiLCJ0b2tlblZlcnNpb24iOjEsImlkIjoiYjNhODkyYTctYWJiZS00NmViLTgxZjItNmVhNDIxY2U4ZDMyIiwicm9sZSI6IkFETUlOIiwiaXNzIjoib25ldWwtdGFuZGEiLCJpYXQiOjE3NDgzNjc2OTksImV4cCI6MTc0ODM2OTQ5OX0.PnqbCywAwBP8N1E3UZElBTZ6OQuInlasMP6VR0QwuRM'; // 반드시 갱신 필요
const flightId = '2129373a-1741-45a4-a2b0-56914adcc906';

export default function () {
    const url = `http://localhost:19091/internal/v1/flights/${flightId}/seats/decrease?requiredSeats=1`;

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
