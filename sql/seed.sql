-- Insert sample users (password: "@Password123" hashed with BCrypt)
INSERT INTO Users
    (UserID, Username, Password, Role, CreatedAt, UpdatedAt)
VALUES
    (CAST('11111111-1111-1111-1111-111111111111' AS UNIQUEIDENTIFIER), 'manager1', '$2a$12$GBxRcdz1TuJ2yhB45caF/eZZjj7QAgHPk/OAgzOpGAs8QlY1ryxje', 'MANAGER', GETDATE(), GETDATE()),
    (CAST('22222222-2222-2222-2222-222222222222' AS UNIQUEIDENTIFIER), 'manager2', '$2a$12$GBxRcdz1TuJ2yhB45caF/eZZjj7QAgHPk/OAgzOpGAs8QlY1ryxje', 'MANAGER', GETDATE(), GETDATE()),
    (CAST('33333333-3333-3333-3333-333333333333' AS UNIQUEIDENTIFIER), 'member1', '$2a$12$TRAuL8k0WHRTYuW5RJUwO.iHZGvZUZS7iL7iSrhNqf.REjyQpY2MS', 'MEMBER', GETDATE(), GETDATE()),
    (CAST('44444444-4444-4444-4444-444444444444' AS UNIQUEIDENTIFIER), 'member2', '$2a$12$GBxRcdz1TuJ2yhB45caF/eZZjj7QAgHPk/OAgzOpGAs8QlY1ryxje', 'MEMBER', GETDATE(), GETDATE()),
    (CAST('55555555-5555-5555-5555-555555555555' AS UNIQUEIDENTIFIER), 'member3', '$2a$12$GBxRcdz1TuJ2yhB45caF/eZZjj7QAgHPk/OAgzOpGAs8QlY1ryxje', 'MEMBER', GETDATE(), GETDATE()),
    (CAST('66666666-6666-6666-6666-666666666666' AS UNIQUEIDENTIFIER), 'member4', '$2a$12$GBxRcdz1TuJ2yhB45caF/eZZjj7QAgHPk/OAgzOpGAs8QlY1ryxje', 'MEMBER', GETDATE(), GETDATE());

-- Insert profiles for users with @gmail.com emails
INSERT INTO Profiles
    (ProfileID, UserID, FirstName, LastName, Email, Phone, DateOfBirth, CreatedAt, UpdatedAt)
VALUES
    (NEWID(), CAST('11111111-1111-1111-1111-111111111111' AS UNIQUEIDENTIFIER), 'John', 'Smith', 'john.smith@gmail.com', '1234567890', '1985-06-15', GETDATE(), GETDATE()),
    (NEWID(), CAST('22222222-2222-2222-2222-222222222222' AS UNIQUEIDENTIFIER), 'Sarah', 'Johnson', 'sarah.j@gmail.com', '2345678901', '1988-03-22', GETDATE(), GETDATE()),
    (NEWID(), CAST('33333333-3333-3333-3333-333333333333' AS UNIQUEIDENTIFIER), 'Michael', 'Brown', 'michael.b@gmail.com', '3456789012', '1990-11-30', GETDATE(), GETDATE()),
    (NEWID(), CAST('44444444-4444-4444-4444-444444444444' AS UNIQUEIDENTIFIER), 'Emily', 'Davis', 'emily.d@gmail.com', '4567890123', '1992-08-25', GETDATE(), GETDATE()),
    (NEWID(), CAST('55555555-5555-5555-5555-555555555555' AS UNIQUEIDENTIFIER), 'David', 'Wilson', 'david.w@gmail.com', '5678901234', '1987-04-18', GETDATE(), GETDATE()),
    (NEWID(), CAST('66666666-6666-6666-6666-666666666666' AS UNIQUEIDENTIFIER), 'Lisa', 'Anderson', 'lisa.a@gmail.com', '6789012345', '1993-01-10', GETDATE(), GETDATE());

-- Insert sample projects
INSERT INTO Projects
    (ProjectID, ProjectName, Description, StartDate, EndDate, ManagerID)
VALUES
    -- Website Redesign Project
    (CAST('77777777-7777-7777-7777-777777777777' AS UNIQUEIDENTIFIER),
        'Website Redesign',
        'Modernize company website with new design and features',
        GETDATE(), -- Start today
        DATEADD(month, 2, GETDATE()), -- End in 2 months
        CAST('11111111-1111-1111-1111-111111111111' AS UNIQUEIDENTIFIER)),

    -- Mobile App Development
    (CAST('88888888-8888-8888-8888-888888888888' AS UNIQUEIDENTIFIER),
        'Mobile App Development',
        'Develop new mobile application for customers',
        DATEADD(day, 1, GETDATE()), -- Start tomorrow
        DATEADD(month, 3, GETDATE()), -- End in 3 months
        CAST('11111111-1111-1111-1111-111111111111' AS UNIQUEIDENTIFIER)),

    -- Database Migration
    (CAST('99999999-9999-9999-9999-999999999999' AS UNIQUEIDENTIFIER),
        'Database Migration',
        'Migrate legacy database to new system',
        DATEADD(day, 2, GETDATE()), -- Start in 2 days
        DATEADD(month, 1, GETDATE()), -- End in 1 month
        CAST('22222222-2222-2222-2222-222222222222' AS UNIQUEIDENTIFIER));

-- Insert project members
INSERT INTO ProjectMembers
    (ProjectID, UserID, CreatedAt, UpdatedAt)
VALUES
    -- Website Redesign Team
    (CAST('77777777-7777-7777-7777-777777777777' AS UNIQUEIDENTIFIER), CAST('33333333-3333-3333-3333-333333333333' AS UNIQUEIDENTIFIER), GETDATE(), GETDATE()),
    (CAST('77777777-7777-7777-7777-777777777777' AS UNIQUEIDENTIFIER), CAST('44444444-4444-4444-4444-444444444444' AS UNIQUEIDENTIFIER), GETDATE(), GETDATE()),
    -- Mobile App Team
    (CAST('88888888-8888-8888-8888-888888888888' AS UNIQUEIDENTIFIER), CAST('33333333-3333-3333-3333-333333333333' AS UNIQUEIDENTIFIER), GETDATE(), GETDATE()),
    (CAST('88888888-8888-8888-8888-888888888888' AS UNIQUEIDENTIFIER), CAST('55555555-5555-5555-5555-555555555555' AS UNIQUEIDENTIFIER), GETDATE(), GETDATE()),
    -- Database Migration Team
    (CAST('99999999-9999-9999-9999-999999999999' AS UNIQUEIDENTIFIER), CAST('44444444-4444-4444-4444-444444444444' AS UNIQUEIDENTIFIER), GETDATE(), GETDATE()),
    (CAST('99999999-9999-9999-9999-999999999999' AS UNIQUEIDENTIFIER), CAST('66666666-6666-6666-6666-666666666666' AS UNIQUEIDENTIFIER), GETDATE(), GETDATE());

-- Insert sample tasks
INSERT INTO Tasks
    (TaskID, ProjectID, TaskName, Description, Status, DueDate, AssignedTo, CreatedAt, UpdatedAt)
VALUES
    -- Website Redesign Tasks
    (NEWID(),
        CAST('77777777-7777-7777-7777-777777777777' AS UNIQUEIDENTIFIER),
        'Design Homepage',
        'Create new homepage layout and design',
        'PENDING',
        DATEADD(day, 14, GETDATE()),
        CAST('33333333-3333-3333-3333-333333333333' AS UNIQUEIDENTIFIER),
        GETDATE(),
        GETDATE()),

    (NEWID(),
        CAST('77777777-7777-7777-7777-777777777777' AS UNIQUEIDENTIFIER),
        'Implement Navigation',
        'Develop responsive navigation menu',
        'IN_PROGRESS',
        DATEADD(day, 21, GETDATE()),
        CAST('44444444-4444-4444-4444-444444444444' AS UNIQUEIDENTIFIER),
        GETDATE(),
        GETDATE()),

    -- Mobile App Tasks
    (NEWID(),
        CAST('88888888-8888-8888-8888-888888888888' AS UNIQUEIDENTIFIER),
        'User Authentication',
        'Implement login and signup functionality',
        'PENDING',
        DATEADD(day, 30, GETDATE()),
        CAST('55555555-5555-5555-5555-555555555555' AS UNIQUEIDENTIFIER),
        GETDATE(),
        GETDATE()),

    -- Database Migration Tasks
    (NEWID(),
        CAST('99999999-9999-9999-9999-999999999999' AS UNIQUEIDENTIFIER),
        'Data Mapping',
        'Create data migration mappings',
        'PENDING',
        DATEADD(day, 7, GETDATE()),
        CAST('66666666-6666-6666-6666-666666666666' AS UNIQUEIDENTIFIER),
        GETDATE(),
        GETDATE());