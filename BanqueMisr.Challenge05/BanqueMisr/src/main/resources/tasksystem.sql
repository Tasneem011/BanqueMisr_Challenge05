CREATE SCHEMA IF NOT EXISTS task_system;

USE task_system;

CREATE TABLE IF NOT EXISTS tasks (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL,
    priority VARCHAR(50) NOT NULL,
    due_date TIMESTAMP NOT NULL,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
    );
CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Auto-incremented id
                      username VARCHAR(255) NOT NULL,        -- Non-null username
                      password VARCHAR(255) NOT NULL,        -- Non-null password
                      role VARCHAR(255) NOT NULL             -- Enum role stored as a string
);

CREATE TABLE history (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,      -- Auto-incremented id
                         action VARCHAR(255) NOT NULL,               -- Action performed (non-null)
                         timestamp TIMESTAMP NOT NULL,               -- Timestamp of the action (non-null)
                         task_id BIGINT,                             -- Foreign key for Task entity
                         FOREIGN KEY (task_id) REFERENCES task(id)  -- Foreign key constraint linking to Task
);
CREATE TABLE notifications (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,      -- Auto-incremented id
                               message VARCHAR(255) NOT NULL,              -- The content of the notification (non-null)
                               timestamp TIMESTAMP NOT NULL,              -- Timestamp of when the notification was created
                               status VARCHAR(50) NOT NULL,               -- The status of the notification (e.g., 'unread', 'read')
                               user_id BIGINT,                            -- Foreign key for User or another entity (e.g., Task, if relevant)
                               FOREIGN KEY (user_id) REFERENCES user(id)  -- Foreign key constraint linking to the User table
);
