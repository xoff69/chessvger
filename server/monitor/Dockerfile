# docker build -t chessvger-monitor . --no-cache
# docker run  chessvger-monitor 
# go get github.com/gorilla/mux
# go mod tidy nettoyage
FROM golang:1.23
WORKDIR /app

COPY go.mod go.sum ./
RUN go mod tidy
RUN go mod download

COPY *.go ./
RUN  go get github.com/go-redis/redis/v8
RUN  go get github.com/lib/pq
RUN  go get github.com/segmentio/kafka-go

RUN go build -o /monitor


CMD [ "/monitor" ,"cluster"]