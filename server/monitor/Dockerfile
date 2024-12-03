# docker build -t chessvger-monitor . --no-cache
# docker run  chessvger-monitor 
# go get github.com/gorilla/mux
FROM golang:1.23

# Définir le répertoire de travail à l'intérieur du conteneur
WORKDIR /app

# Copier les fichiers de configuration Go (go.mod et go.sum) pour installer les dépendances
COPY go.mod  ./

# Télécharger les dépendances (utilisation de cache si possible)
RUN go mod download

# Copier le reste du code source dans le conteneur
COPY . .

RUN go build -o monitor .

CMD ["ls", "-la"]