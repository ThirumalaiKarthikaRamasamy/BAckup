CREATE TABLE semaphores (
  key VARCHAR(1024) PRIMARY KEY,
  timestamp BIGINT NOT NULL,
  ms_to_live INTEGER NOT NULL
)