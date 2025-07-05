FROM node:20 AS builder
WORKDIR /app
COPY . .

RUN npm install
RUN npm run build

FROM caddy:2-alpine

COPY --from=builder /app/dist /usr/share/caddy

COPY Caddyfile /etc/caddy/Caddyfile

EXPOSE 80
