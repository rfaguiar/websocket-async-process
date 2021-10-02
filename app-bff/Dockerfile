FROM node:14-stretch as builder

WORKDIR /home/src

COPY . .
RUN npm i

RUN npm run build

FROM nginx:1.19.6-alpine

COPY --from=builder /home/src/build /usr/share/nginx/html

RUN rm /etc/nginx/conf.d/default.conf

COPY nginx.conf /etc/nginx/conf.d/default.conf
