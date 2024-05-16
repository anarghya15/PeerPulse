kubectl delete -f kubeDeploy/mysql-service.yml
kubectl delete -f kubeDeploy/mysql-deployment.yml
kubectl delete -f kubeDeploy/backend-service.yml
kubectl delete -f kubeDeploy/backend-deployment.yml
kubectl delete -f kubeDeploy/frontend-service.yml
kubectl delete -f kubeDeploy/frontend-deployment.yml

helm uninstall filebeat
helm uninstall logstash
