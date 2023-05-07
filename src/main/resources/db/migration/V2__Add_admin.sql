insert into users(username, password, is_active)
    values ('admin', '$2a$12$KNACkdlMRwbY1FCkcEau/u31.YB7p.SwflLz/LM.5Tjk8gVqIBXlu', true);

insert into user_roles(user_id, roles)
    values (1, 'ADMIN'), (1, 'USER');