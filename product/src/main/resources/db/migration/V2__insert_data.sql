INSERT INTO category (id, name, description, created_date) VALUES
('3442fcfc-b603-497d-ae53-f5e89f42705d',
 'Electronic devices and gadgets',
 '',
 '2026-01-12'),

('13c8c4ac-a031-47fb-afe5-5f938613919b',
 'Books',
 'Books and educational materials',
 '2026-01-12'),

('66562662-8f31-421d-9f84-9cabf3115e5e',
 'Clothing',
 'Apparel and fashion products',
 '2026-01-12');

INSERT INTO product (id, name, description, available_quantity, price, category_id, created_date) VALUES
('6207a925-113b-49f0-ae6f-83556f862f81',
 'Lenovo Legion 5',
 'Gaming Laptop',
 20,
 1200,
 '3442fcfc-b603-497d-ae53-f5e89f42705d',
 '2026-01-12'),

('b408c195-cfe7-46a7-80f1-4525ca5dda19',
 'Laptop', '15-inch laptop',
 25, 1200.00,
 '3442fcfc-b603-497d-ae53-f5e89f42705d',
 '2026-01-12'),

('73b51748-8a8c-498c-9640-3e5d26a69638', 'Smartphone',
 'Android smartphone', 50,
 800.00,
 '3442fcfc-b603-497d-ae53-f5e89f42705d',
 '2026-01-12'),

('0f22886f-4882-44ce-a283-1ca31fca6aba',
 'Spring Boot in Action',
 'Programming book',
 100,
 45.99,
 '13c8c4ac-a031-47fb-afe5-5f938613919b',
 '2026-01-12'),

('782d44ad-951f-49be-9fda-9299287632fc',
 'T-Shirt',
 'Cotton t-shirt size M',
 200,
 19.99,
 '66562662-8f31-421d-9f84-9cabf3115e5e',
 '2026-01-12');