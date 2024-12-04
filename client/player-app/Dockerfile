# docker build -t chessvger-client . --no-cache
FROM node:16 AS build

WORKDIR /app
COPY package*.json ./
COPY . .

RUN npm install
RUN npm run build

FROM nginx:alpine

COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 3001

CMD ["nginx", "-g", "daemon off;"]
