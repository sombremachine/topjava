curl -v http://localhost/topjava/rest/meals
curl -v http://localhost/topjava/rest/meals/100005
curl -i -X PUT -H 'Content-Type: application/json' -d '{"id":100005,"dateTime":"2015-05-31T10:00:00","description":"asdasd","calories":50,"user":null}' http://localhost/topjava/rest/meals/100005
curl -i -X POST -H 'Content-Type: application/json' -d '{"dateTime":"2016-05-31T10:00:00","description":"dddddd","calories":50,"user":null}' http://localhost/topjava/rest/meals
curl -v -X DELETE http://localhost/topjava/rest/meals/100005
curl -v 'http://localhost/topjava/rest/meals/filter?startDate=2015-05-29&endDate=2015-05-30&startTime=12:00&endTime=14:00'
