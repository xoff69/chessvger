# docker build -t chessvger-monitor .
# docker run  chessvger-monitor 
# go get github.com/gorilla/mux
FROM golang:1.23

WORKDIR /app

# Download Go modules
COPY . .
RUN go mod download

# Copy the source code. Note the slash at the end, as explained in
# https://docs.docker.com/reference/dockerfile/#copy
COPY *.go ./

# Build
RUN go build -o /monitor

# Run
CMD ["/monitor"]