-- Insert sample users (password: "@Password123" hashed with BCrypt)
INSERT INTO Users
    (UserID, Username, Password, Role)
VALUES
    (CAST('11111111-1111-1111-1111-111111111111' AS UNIQUEIDENTIFIER), 'manager1', '$2a$12$GBxRcdz1TuJ2yhB45caF/eZZjj7QAgHPk/OAgzOpGAs8QlY1ryxje', 'Manager'),
    (CAST('22222222-2222-2222-2222-222222222222' AS UNIQUEIDENTIFIER), 'manager2', '$2a$12$GBxRcdz1TuJ2yhB45caF/eZZjj7QAgHPk/OAgzOpGAs8QlY1ryxje', 'Manager'),
    (CAST('33333333-3333-3333-3333-333333333333' AS UNIQUEIDENTIFIER), 'member1', '$$2a$12$GBxRcdz1TuJ2yhB45caF/eZZjj7QAgHPk/OAgzOpGAs8QlY1ryxje', 'Member'),
    (CAST('44444444-4444-4444-4444-444444444444' AS UNIQUEIDENTIFIER), 'member2', '$2a$12$GBxRcdz1TuJ2yhB45caF/eZZjj7QAgHPk/OAgzOpGAs8QlY1ryxje', 'Member'),
    (CAST('55555555-5555-5555-5555-555555555555' AS UNIQUEIDENTIFIER), 'member3', '$2a$12$GBxRcdz1TuJ2yhB45caF/eZZjj7QAgHPk/OAgzOpGAs8QlY1ryxje', 'Member'),
    (CAST('66666666-6666-6666-6666-666666666666' AS UNIQUEIDENTIFIER), 'member4', '$2a$12$GBxRcdz1TuJ2yhB45caF/eZZjj7QAgHPk/OAgzOpGAs8QlY1ryxje', 'Member');

-- Insert profiles for users with @gmail.com emails
INSERT INTO Profiles
    (ProfileID, UserID, FirstName, LastName, Email, Phone, DateOfBirth)
VALUES
    (NEWID(), CAST('11111111-1111-1111-1111-111111111111' AS UNIQUEIDENTIFIER), 'John', 'Smith', 'john.smith@gmail.com', '1234567890', '1985-06-15'),
    (NEWID(), CAST('22222222-2222-2222-2222-222222222222' AS UNIQUEIDENTIFIER), 'Sarah', 'Johnson', 'sarah.j@gmail.com', '2345678901', '1988-03-22'),
    (NEWID(), CAST('33333333-3333-3333-3333-333333333333' AS UNIQUEIDENTIFIER), 'Michael', 'Brown', 'michael.b@gmail.com', '3456789012', '1990-11-30'),
    (NEWID(), CAST('44444444-4444-4444-4444-444444444444' AS UNIQUEIDENTIFIER), 'Emily', 'Davis', 'emily.d@gmail.com', '4567890123', '1992-08-25'),
    (NEWID(), CAST('55555555-5555-5555-5555-555555555555' AS UNIQUEIDENTIFIER), 'David', 'Wilson', 'david.w@gmail.com', '5678901234', '1987-04-18'),
    (NEWID(), CAST('66666666-6666-6666-6666-666666666666' AS UNIQUEIDENTIFIER), 'Lisa', 'Anderson', 'lisa.a@gmail.com', '6789012345', '1993-01-10');

-- Insert sample projects
INSERT INTO Projects
    (ProjectID, ProjectName, Description, StartDate, EndDate, ManagerID)
VALUES
    (CAST('77777777-7777-7777-7777-777777777777' AS UNIQUEIDENTIFIER), 'Website Redesign', 'Complete overhaul of company website', '2024-03-01', '2024-05-30', CAST('11111111-1111-1111-1111-111111111111' AS UNIQUEIDENTIFIER)),
    (CAST('88888888-8888-8888-8888-888888888888' AS UNIQUEIDENTIFIER), 'Mobile App Development', 'New mobile application for customers', '2024-03-15', '2024-06-30', CAST('11111111-1111-1111-1111-111111111111' AS UNIQUEIDENTIFIER)),
    (CAST('99999999-9999-9999-9999-999999999999' AS UNIQUEIDENTIFIER), 'Database Migration', 'Migrate legacy database to new system', '2024-04-01', '2024-05-15', CAST('22222222-2222-2222-2222-222222222222' AS UNIQUEIDENTIFIER)),
    (CAST('AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA' AS UNIQUEIDENTIFIER), 'Security Audit', 'Annual security review and updates', '2024-04-15', '2024-05-30', CAST('22222222-2222-2222-2222-222222222222' AS UNIQUEIDENTIFIER));

-- Add project members
INSERT INTO ProjectMembers
    (ProjectID, UserID, JoinedAt)
VALUES
    -- Website Redesign Team
    (CAST('77777777-7777-7777-7777-777777777777' AS UNIQUEIDENTIFIER), CAST('33333333-3333-3333-3333-333333333333' AS UNIQUEIDENTIFIER), GETDATE()),
    (CAST('77777777-7777-7777-7777-777777777777' AS UNIQUEIDENTIFIER), CAST('44444444-4444-4444-4444-444444444444' AS UNIQUEIDENTIFIER), GETDATE()),
    -- Mobile App Team
    (CAST('88888888-8888-8888-8888-888888888888' AS UNIQUEIDENTIFIER), CAST('33333333-3333-3333-3333-333333333333' AS UNIQUEIDENTIFIER), GETDATE()),
    (CAST('88888888-8888-8888-8888-888888888888' AS UNIQUEIDENTIFIER), CAST('55555555-5555-5555-5555-555555555555' AS UNIQUEIDENTIFIER), GETDATE()),
    (CAST('88888888-8888-8888-8888-888888888888' AS UNIQUEIDENTIFIER), CAST('66666666-6666-6666-6666-666666666666' AS UNIQUEIDENTIFIER), GETDATE()),
    -- Database Migration Team
    (CAST('99999999-9999-9999-9999-999999999999' AS UNIQUEIDENTIFIER), CAST('44444444-4444-4444-4444-444444444444' AS UNIQUEIDENTIFIER), GETDATE()),
    (CAST('99999999-9999-9999-9999-999999999999' AS UNIQUEIDENTIFIER), CAST('55555555-5555-5555-5555-555555555555' AS UNIQUEIDENTIFIER), GETDATE()),
    -- Security Audit Team
    (CAST('AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA' AS UNIQUEIDENTIFIER), CAST('66666666-6666-6666-6666-666666666666' AS UNIQUEIDENTIFIER), GETDATE());

-- Insert sample tasks
INSERT INTO Tasks
    (TaskID, ProjectID, TaskName, Description, Status, DueDate, AssignedTo)
VALUES
    -- Website Redesign Tasks
    (CAST('BBBBBBBB-BBBB-BBBB-BBBB-BBBBBBBBBBBB' AS UNIQUEIDENTIFIER), CAST('77777777-7777-7777-7777-777777777777' AS UNIQUEIDENTIFIER), 'Homepage Design', 'Create new homepage layout', 'In Progress', '2024-03-15', CAST('33333333-3333-3333-3333-333333333333' AS UNIQUEIDENTIFIER)),
    (CAST('CCCCCCCC-CCCC-CCCC-CCCC-CCCCCCCCCCCC' AS UNIQUEIDENTIFIER), CAST('77777777-7777-7777-7777-777777777777' AS UNIQUEIDENTIFIER), 'Content Migration', 'Move content to new design', 'Pending', '2024-04-01', CAST('44444444-4444-4444-4444-444444444444' AS UNIQUEIDENTIFIER)),
    (CAST('DDDDDDDD-DDDD-DDDD-DDDD-DDDDDDDDDDDD' AS UNIQUEIDENTIFIER), CAST('77777777-7777-7777-7777-777777777777' AS UNIQUEIDENTIFIER), 'Mobile Responsiveness', 'Ensure mobile compatibility', 'Pending', '2024-04-15', CAST('33333333-3333-3333-3333-333333333333' AS UNIQUEIDENTIFIER));

-- Insert task assignments
INSERT INTO TaskAssignments
    (TaskID, UserID, Status, AssignedAt)
VALUES
    -- Website Redesign Assignments
    (CAST('BBBBBBBB-BBBB-BBBB-BBBB-BBBBBBBBBBBB' AS UNIQUEIDENTIFIER), CAST('33333333-3333-3333-3333-333333333333' AS UNIQUEIDENTIFIER), 'PENDING', GETDATE()),
    (CAST('CCCCCCCC-CCCC-CCCC-CCCC-CCCCCCCCCCCC' AS UNIQUEIDENTIFIER), CAST('44444444-4444-4444-4444-444444444444' AS UNIQUEIDENTIFIER), 'PENDING', GETDATE()),
    (CAST('DDDDDDDD-DDDD-DDDD-DDDD-DDDDDDDDDDDD' AS UNIQUEIDENTIFIER), CAST('33333333-3333-3333-3333-333333333333' AS UNIQUEIDENTIFIER), 'PENDING', GETDATE());