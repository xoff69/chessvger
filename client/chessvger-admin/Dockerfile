# docker build -t  chessvger-admin . --no-cache
FROM node:16 AS build-stage

WORKDIR /app

COPY package*.json ./
COPY . .

RUN npm install

FROM node:16 AS production-stage

WORKDIR /app
COPY --from=build-stage /app /app

EXPOSE 3002

CMD ["npm", "run", "dev","--" ,"--host"]
