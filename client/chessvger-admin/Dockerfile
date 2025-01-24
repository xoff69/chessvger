# docker build -t  chessvger-client . --no-cache
chessvger-client . --no-cache


WORKDIR /app

COPY package*.json ./
COPY . .

RUN npm install


EXPOSE 3002

CMD ["npm", "run", "dev","--" ,"--host"]
