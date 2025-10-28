# Frontend Dockerfile (Angular build served by nginx)
# Build Stage
FROM node:20 AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build -- --configuration=production

# Serve Stage
FROM nginx:stable
COPY --from=build /app/dist/EMS_frontend/browser /usr/share/nginx/html
# Optional: copy a custom nginx.conf if you need SPA fallback
# COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
