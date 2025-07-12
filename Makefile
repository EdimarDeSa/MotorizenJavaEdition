dev_up:
	docker compose --profile dev up -d

dev_down:
	docker compose --profile dev down

dev_reset:
	docker compose --profile dev down
	docker compose --profile dev up -d

monitor_up:
	docker compose --profile monitor up -d

monitor_down:
	docker compose --profile monitor down
