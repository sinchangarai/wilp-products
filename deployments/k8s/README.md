############################################################################################

################################# Install Prometheus-Grafana Stack #################################

helm repo add prometheus-community [https://prometheus-community.github.io/helm-charts](https://prometheus-community.github.io/helm-charts)

helm repo update

kubectl create ns prom

kubectl get svc prometheus-grafana -o yaml -n prom > prom-stack-svc.yaml

perl -0777 -i.bak -pe 's/type: ClusterIP/type: LoadBalancer/' prom-stack-svc.yaml

kubectl apply -f prom-stack-svc.yaml

until \[ -n "$(kubectl get svc prometheus-grafana -n prom -o jsonpath='{.status.loadBalancer.ingress\[0\].ip}')" \];

do

echo “external-ip is yet to be assigned to prometheus stack"

sleep 10

done

export grafana\_ip=$(kubectl get svc prometheus-grafana -n prom -o jsonpath='{.status.loadBalancer.ingress\[0\].ip}')

\# login to grafana using admin/prom-operator on browser

############################################################################################

############################################################################################

###################################### Deploy Mongo DB ########################################

cd product/deployments/k8s/mongo-db

kubectl apply -f mongo-namespace.yaml

kubectl apply -f mongo-sc.yaml

kubectl apply -f mongo-pvc.yaml

kubectl apply -f mongo-statefulset.yaml

kubectl apply -f mongo-service.yaml

############################################################################################

############################################################################################

################################## Deploy Springboot app ########################################

cd product/deployments/k8s/springboot-app

helm repo add ingress-nginx [https://kubernetes.github.io/ingress-nginx](https://kubernetes.github.io/ingress-nginx)

helm repo update

helm install nginx-ingress ingress-nginx/ingress-nginx -n app

kubectl apply -f app-namespace.yaml

kubectl apply -f app-deployment.yaml

kubectl apply -f app-svc.yaml

kubectl apply -f app-ingress.yaml

until \[ -n "$(kubectl get ing -n app -o jsonpath='{.items\[0\].status.loadBalancer.ingress\[0\].ip}'" \];

do

echo “external-ip is yet to be assigned to ingress”

sleep 10

done

export ingress\_ip=$(kubectl get ing -n app -o jsonpath='{.items\[0\].status.loadBalancer.ingress\[0\].ip}')

############################################################################################

############################################################################################

######################################## Test the app ##########################################

curl --location 'http://ingress\_ip:80/catalog/products' \\

\--header 'Host: test.ingress-nginx.com' \\

\--header 'Content-Type: application/json' \\

\--data '{

    "name": "iphone 15",

    "description": "dummy iphone 15 description",

    "price": 99999

}'

curl --location 'http://ingress\_ip:80/catalog/products' \\

\--header 'Host: test.ingress-nginx.com' \\

\--header 'Content-Type: application/json' \\

\--data '{

    "name": “pixel 7 pro,

    "description": "dummy google pixel 7 pro description",

    "price": 76543

}'

curl --location 'http://ingress\_ip:80/catalog/products' \\

\--header 'Host: test.ingress-nginx.com'

############################################################################################
