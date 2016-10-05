# mysql-service-broker


```
service_id=`uuidgen | tr A-Z a-z`
binding_id=`uuidgen | tr A-Z a-z`

curl -XPUT -u mysql:mysql http://localhost:8080/v2/service_instances/$service_id -H 'Content-Type: application/json' -d '{"service_id":"a", "plan_id": "b", "organization_guid":"c", "space_guid":"d"}'
curl -XPUT -u mysql:mysql http://localhost:8080/v2/service_instances/$service_id/service_bindings/$binding_id -H 'Content-Type: application/json' -d '{"service_id":"a", "plan_id": "b", "app_guid":"c", "bind_resource":{"app_guid":"c"}}'
curl -XDELETE -u mysql:mysql "http://localhost:8080/v2/service_instances/$service_id?service_id=a&plan_id=b"
curl -XDELETE -u mysql:mysql "http://localhost:8080/v2/service_instances/$service_id/service_bindings/$binding_id?service_id=a&plan_id=b"
```