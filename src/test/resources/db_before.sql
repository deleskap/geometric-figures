INSERT INTO shape (dtype, id, created_at, created_by, version, last_modified_at, last_modified_by, type, width, height)
VALUES ('Rectangle', 13, '2022-12-14 23:58:56.147539', 'creatorUserForTests', 0, '2022-12-14 23:58:56.147539', 'creatorUser',
        'RECTANGLE', 4.0, 2.0),
       ('Rectangle', 2, '2022-12-14 23:58:56.147539', 'creatorUser', 0, '2022-12-14 23:58:56.147539', 'creatorUser',
        'RECTANGLE', 3.5, 1.0),
       ('Rectangle', 3, '2022-12-14 23:58:56.147539', 'creatorUser', 0, '2022-12-14 23:58:56.147539', 'creatorUser',
        'RECTANGLE', 2.0, 3.0),
       ('Rectangle', 4, '2022-12-14 23:58:56.147539', 'creatorUser', 0, '2022-12-14 23:58:56.147539', 'creatorUser',
        'RECTANGLE', 5.0, 6.0);
INSERT INTO shape (dtype, id, created_at, created_by, version, last_modified_at, last_modified_by, type, width)
VALUES ('Square', 5, '2021-12-14 23:58:56.147539', 'creatorUser', 0, '2022-12-14 23:58:56.147539', 'creatorUser',
        'SQUARE', 4.0),
       ('Square', 6, '2022-12-14 23:58:56.147539', 'creatorUser', 0, '2022-12-14 23:58:56.147539', 'creatorUser',
        'SQUARE', 4.0),
       ('Square', 7, '2022-12-14 23:58:56.147539', 'creatorUser', 0, '2022-12-14 23:58:56.147539', 'creatorUser',
        'SQUARE', 2.0),
       ('Square', 8, '2022-12-14 23:58:56.147539', 'creatorUser', 0, '2022-12-14 23:58:56.147539', 'creatorUser',
        'SQUARE', 5.0);
INSERT INTO shape (dtype, id, created_at, created_by, version, last_modified_at, last_modified_by, type, radius)
VALUES ('Circle', 9, '2022-12-14 23:58:56.147539', 'creatorUser', 0, '2022-12-14 23:58:56.147539', 'creatorUser',
        'CIRCLE', 2.0),
       ('Circle', 10, '2022-12-14 23:58:56.147539', 'creatorUser', 0, '2022-12-14 23:58:56.147539', 'creatorUser',
        'CIRCLE', 3.0),
       ('Circle', 11, '2022-12-14 23:58:56.147539', 'creatorUser', 0, '2022-12-14 23:58:56.147539', 'creatorUser',
        'CIRCLE', 4.0),
        ('Circle', 12, '2022-12-14 23:58:56.147539', 'creatorUser', 0, '2022-12-14 23:58:56.147539', 'creatorUser',
        'CIRCLE', 6.0);
-- INSERT INTO hibernate_sequences (sequence_name, next_val) VALUES ('default',13)