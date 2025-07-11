#!/bin/sh

: ${REDIS_PASSWORD} || echo "REDIS_PASSWORD is not set. Exiting."
: ${REDIS_USER} || echo "REDIS_USER is not set. Exiting."
: ${REDIS_PORT} || echo "REDIS_PORT is not set. Exiting."
: ${REDIS_DATABASES} || echo "REDIS_DATABASES is not set. Exiting."

# Replace placeholders in redis.conf
sed -e "s/\${REDIS_PASSWORD}/$REDIS_PASSWORD/g" \
    -e "s/\${REDIS_USER}/$REDIS_USER/g" \
    -e "s/\${REDIS_PORT}/$REDIS_PORT/g" \
    -e "s/\${REDIS_DATABASES}/$REDIS_DATABASES/g" \
    /tmp/redis.conf > /home/redis/redis.conf

# Start Redis server with the modified configuration
echo "Iniciando Redis com o arquivo de configuração /home/redis/redis.conf"
exec redis-server /home/redis/redis.conf
echo "Redis iniciando coretamente..."