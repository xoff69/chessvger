# docker build -t chessvger-monitor . --no-cache
# docker run  chessvger-monitor 
# go get github.com/gorilla/mux
FROM golang:1.23

WORKDIR /app

# Download Go modules
COPY . .
RUN go mod download

# Build
RUN go build -o monitor

# Run
CMD ["./monitor ","cluster"]