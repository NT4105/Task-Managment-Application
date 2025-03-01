-- Insert sample users (password: "password123" hashed with BCrypt)
INSERT INTO Users
    (UserID, Username, Password, Role)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'manager1', '$2a$10$xLhxHxkuXY.h3QgOBQbZgeHd6YurDzn0Z3ZZhN9wKjCezyXz3f1AS', 'Manager'),
    ('22222222-2222-2222-2222-222222222222', 'manager2', '$2a$10$xLhxHxkuXY.h3QgOBQbZgeHd6YurDzn0Z3ZZhN9wKjCezyXz3f1AS', 'Manager'),
    ('33333333-3333-3333-3333-333333333333', 'member1', '$2a$10$xLhxHxkuXY.h3QgOBQbZgeHd6YurDzn0Z3ZZhN9wKjCezyXz3f1AS', 'Member'),
    ('44444444-4444-4444-4444-444444444444', 'member2', '$2a$10$xLhxHxkuXY.h3QgOBQbZgeHd6YurDzn0Z3ZZhN9wKjCezyXz3f1AS', 'Member'),
    ('55555555-5555-5555-5555-555555555555', 'member3', '$2a$10$xLhxHxkuXY.h3QgOBQbZgeHd6YurDzn0Z3ZZhN9wKjCezyXz3f1AS', 'Member'),
    ('66666666-6666-6666-6666-666666666666', 'member4', '$2a$10$xLhxHxkuXY.h3QgOBQbZgeHd6YurDzn0Z3ZZhN9wKjCezyXz3f1AS', 'Member');

-- Insert profiles for users
INSERT INTO Profiles
    (ProfileID, UserID, FirstName, LastName, Email, Phone, DateOfBirth)
VALUES
    (NEWID(), '11111111-1111-1111-1111-111111111111', 'John', 'Smith', 'john.smith@example.com', '1234567890', '1985-06-15'),
    (NEWID(), '22222222-2222-2222-2222-222222222222', 'Sarah', 'Johnson', 'sarah.j@example.com', '2345678901', '1988-03-22'),
    (NEWID(), '33333333-3333-3333-3333-333333333333', 'Michael', 'Brown', 'michael.b@example.com', '3456789012', '1990-11-30'),
    (NEWID(), '44444444-4444-4444-4444-444444444444', 'Emily', 'Davis', 'emily.d@example.com', '4567890123', '1992-08-25'),
    (NEWID(), '55555555-5555-5555-5555-555555555555', 'David', 'Wilson', 'david.w@example.com', '5678901234', '1987-04-18'),
    (NEWID(), '66666666-6666-6666-6666-666666666666', 'Lisa', 'Anderson', 'lisa.a@example.com', '6789012345', '1993-01-10');

-- Insert sample projects
INSERT INTO Projects
    (ProjectID, ProjectName, Description, StartDate, EndDate, ManagerID)
VALUES
    ('77777777-7777-7777-7777-777777777777', 'Website Redesign', 'Complete overhaul of company website', '2024-03-01', '2024-05-30', '11111111-1111-1111-1111-111111111111'),
    ('88888888-8888-8888-8888-888888888888', 'Mobile App Development', 'New mobile application for customers', '2024-03-15', '2024-06-30', '11111111-1111-1111-1111-111111111111'),
    ('99999999-9999-9999-9999-999999999999', 'Database Migration', 'Migrate legacy database to new system', '2024-04-01', '2024-05-15', '22222222-2222-2222-2222-222222222222'),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Security Audit', 'Annual security review and updates', '2024-04-15', '2024-05-30', '22222222-2222-2222-2222-222222222222');

-- Add project members
INSERT INTO ProjectMembers
    (ProjectID, UserID, JoinedAt)
VALUES
    -- Website Redesign Team
    ('77777777-7777-7777-7777-777777777777', '33333333-3333-3333-3333-333333333333', GETDATE()),
    ('77777777-7777-7777-7777-777777777777', '44444444-4444-4444-4444-444444444444', GETDATE()),
    -- Mobile App Team
    ('88888888-8888-8888-8888-888888888888', '33333333-3333-3333-3333-333333333333', GETDATE()),
    ('88888888-8888-8888-8888-888888888888', '55555555-5555-5555-5555-555555555555', GETDATE()),
    ('88888888-8888-8888-8888-888888888888', '66666666-6666-6666-6666-666666666666', GETDATE()),
    -- Database Migration Team
    ('99999999-9999-9999-9999-999999999999', '44444444-4444-4444-4444-444444444444', GETDATE()),
    ('99999999-9999-9999-9999-999999999999', '55555555-5555-5555-5555-555555555555', GETDATE()),
    -- Security Audit Team
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '66666666-6666-6666-6666-666666666666', GETDATE());

-- Insert sample tasks
INSERT INTO Tasks
    (TaskID, ProjectID, TaskName, Description, Status, DueDate)
VALUES
    -- Website Redesign Tasks
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '77777777-7777-7777-7777-777777777777', 'Homepage Design', 'Create new homepage layout', 'In Progress', '2024-03-15'),
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', '77777777-7777-7777-7777-777777777777', 'Content Migration', 'Move content to new design', 'Pending', '2024-04-01'),
    ('dddddddd-dddd-dddd-dddd-dddddddddddd', '77777777-7777-7777-7777-777777777777', 'Mobile Responsiveness', 'Ensure mobile compatibility', 'Pending', '2024-04-15'),

    -- Mobile App Tasks
    ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', '88888888-8888-8888-8888-888888888888', 'UI Design', 'Design user interface', 'In Progress', '2024-03-30'),
    ('ffffffff-ffff-ffff-ffff-ffffffffffff', '88888888-8888-8888-8888-888888888888', 'Backend Integration', 'Connect to backend services', 'Pending', '2024-04-15'),
    ('gggggggg-gggg-gggg-gggg-gggggggggggg', '88888888-8888-8888-8888-888888888888', 'User Testing', 'Conduct user testing', 'Pending', '2024-05-01'),

    -- Database Migration Tasks
    ('hhhhhhhh-hhhh-hhhh-hhhh-hhhhhhhhhhhh', '99999999-9999-9999-9999-999999999999', 'Data Mapping', 'Map old to new database schema', 'In Progress', '2024-04-10'),
    ('iiiiiiii-iiii-iiii-iiii-iiiiiiiiiiii', '99999999-9999-9999-9999-999999999999', 'Migration Script', 'Write migration scripts', 'Pending', '2024-04-20'),

    -- Security Audit Tasks
    ('jjjjjjjj-jjjj-jjjj-jjjj-jjjjjjjjjjjj', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Vulnerability Scan', 'Run security scans', 'Pending', '2024-04-30'),
    ('kkkkkkkk-kkkk-kkkk-kkkk-kkkkkkkkkkkk', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Security Report', 'Prepare audit report', 'Pending', '2024-05-15');

-- Assign tasks to members
INSERT INTO TaskAssignments
    (TaskID, UserID, Status, AssignedAt)
VALUES
    -- Website Redesign Assignments
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '33333333-3333-3333-3333-333333333333', 'In Progress', GETDATE()),
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', '44444444-4444-4444-4444-444444444444', 'Pending', GETDATE()),
    ('dddddddd-dddd-dddd-dddd-dddddddddddd', '33333333-3333-3333-3333-333333333333', 'Pending', GETDATE()),

    -- Mobile App Assignments
    ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', '55555555-5555-5555-5555-555555555555', 'In Progress', GETDATE()),
    ('ffffffff-ffff-ffff-ffff-ffffffffffff', '66666666-6666-6666-6666-666666666666', 'Pending', GETDATE()),
    ('gggggggg-gggg-gggg-gggg-gggggggggggg', '33333333-3333-3333-3333-333333333333', 'Pending', GETDATE()),

    -- Database Migration Assignments
    ('hhhhhhhh-hhhh-hhhh-hhhh-hhhhhhhhhhhh', '44444444-4444-4444-4444-444444444444', 'In Progress', GETDATE()),
    ('iiiiiiii-iiii-iiii-iiii-iiiiiiiiiiii', '55555555-5555-5555-5555-555555555555', 'Pending', GETDATE()),

    -- Security Audit Assignments
    ('jjjjjjjj-jjjj-jjjj-jjjj-jjjjjjjjjjjj', '66666666-6666-6666-6666-666666666666', 'Pending', GETDATE()),
    ('kkkkkkkk-kkkk-kkkk-kkkk-kkkkkkkkkkkk', '66666666-6666-6666-6666-666666666666', 'Pending', GETDATE());
