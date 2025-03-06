Create Database TaskManager
use TaskManager
-- Users table for authentication and basic user information
CREATE TABLE Users
(
    UserID UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    Username NVARCHAR(50) UNIQUE NOT NULL,
    Password NVARCHAR(255) NOT NULL,
    Role NVARCHAR(20) CHECK (Role IN ('MANAGER', 'MEMBER')) NOT NULL,
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE()
);

-- Profiles table for detailed user information
CREATE TABLE Profiles
(
    ProfileID UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    UserID UNIQUEIDENTIFIER UNIQUE NOT NULL,
    FirstName NVARCHAR(50) NOT NULL,
    LastName NVARCHAR(50) NOT NULL,
    Email NVARCHAR(100) UNIQUE NOT NULL,
    Phone NVARCHAR(15) UNIQUE NOT NULL,
    DateOfBirth DATE,
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Projects table
CREATE TABLE Projects
(
    ProjectID UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    ProjectName NVARCHAR(100) NOT NULL,
    Description NVARCHAR(MAX),
    StartDate DATE NOT NULL,
    EndDate DATE,
    ManagerID UNIQUEIDENTIFIER NOT NULL,
    TotalTasks INT DEFAULT 0,
    CompletedTasks INT DEFAULT 0,
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (ManagerID) REFERENCES Users(UserID),
    CONSTRAINT CHK_Project_Dates CHECK (
        StartDate >= CAST(GETDATE() AS DATE)
        AND (EndDate IS NULL OR EndDate >= StartDate)
    )
);

-- Tasks table
CREATE TABLE Tasks
(
    TaskID UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    ProjectID UNIQUEIDENTIFIER NOT NULL,
    TaskName NVARCHAR(100) NOT NULL,
    Description NVARCHAR(MAX),
    Status NVARCHAR(20) CHECK (Status IN ('PENDING', 'IN_PROGRESS', 'COMPLETED')) DEFAULT 'PENDING',
    DueDate DATE NOT NULL,
    AssignedTo UNIQUEIDENTIFIER,
    SubmissionLink NVARCHAR(255),
    SubmissionFilePath NVARCHAR(255),
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (ProjectID) REFERENCES Projects(ProjectID) ON DELETE CASCADE,
    FOREIGN KEY (AssignedTo) REFERENCES Users(UserID),
    CONSTRAINT CHK_Task_DueDate CHECK (
        DueDate >= CAST(GETDATE() AS DATE)
    )
);

-- ProjectMembers table for many-to-many relationship between Projects and Users
CREATE TABLE ProjectMembers
(
    ProjectID UNIQUEIDENTIFIER NOT NULL,
    UserID UNIQUEIDENTIFIER NOT NULL,
    JoinedAt DATETIME DEFAULT GETDATE(),
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE(),
    PRIMARY KEY (ProjectID, UserID),
    FOREIGN KEY (ProjectID) REFERENCES Projects(ProjectID) ON DELETE CASCADE,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- TaskAssignments table for tracking task assignments and submissions
CREATE TABLE TaskAssignments
(
    TaskID UNIQUEIDENTIFIER,
    UserID UNIQUEIDENTIFIER,
    Status NVARCHAR(20) CHECK (Status IN ('PENDING', 'IN_PROGRESS', 'COMPLETED')) DEFAULT 'PENDING',
    SubmissionLink NVARCHAR(255),
    SubmissionFilePath NVARCHAR(255),
    AssignedAt DATETIME DEFAULT GETDATE(),
    CompletedAt DATETIME,
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE(),
    PRIMARY KEY (TaskID, UserID),
    FOREIGN KEY (TaskID) REFERENCES Tasks(TaskID) ON DELETE CASCADE,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    CONSTRAINT CHK_Assignment_CompletedAt CHECK (
        CompletedAt IS NULL OR CompletedAt >= AssignedAt
    )
);

-- Create mapping tables for encoded IDs
CREATE TABLE ProjectIdMapping
(
    ProjectID UNIQUEIDENTIFIER NOT NULL,
    EncodedProjectID NVARCHAR(255) NOT NULL,
    CreatedAt DATETIME DEFAULT GETDATE(),
    PRIMARY KEY (EncodedProjectID),
    FOREIGN KEY (ProjectID) REFERENCES Projects(ProjectID)
);

CREATE TABLE TaskIdMapping
(
    TaskID UNIQUEIDENTIFIER NOT NULL,
    EncodedTaskID NVARCHAR(255) NOT NULL,
    CreatedAt DATETIME DEFAULT GETDATE(),
    PRIMARY KEY (EncodedTaskID),
    FOREIGN KEY (TaskID) REFERENCES Tasks(TaskID)
);
-- Trigger để tự động cập nhật UpdatedAt khi dữ liệu thay đổi
GO
CREATE TRIGGER TR_Users_UpdateTimestamp ON Users AFTER UPDATE AS
BEGIN
    UPDATE Users
    SET UpdatedAt = GETDATE()
    FROM Users u
        INNER JOIN inserted i ON u.UserID = i.UserID
END
GO

CREATE TRIGGER TR_Profiles_UpdateTimestamp ON Profiles AFTER UPDATE AS
BEGIN
    UPDATE Profiles
    SET UpdatedAt = GETDATE()
    FROM Profiles p
        INNER JOIN inserted i ON p.ProfileID = i.ProfileID
END
GO

CREATE TRIGGER TR_Projects_UpdateTimestamp ON Projects AFTER UPDATE AS
BEGIN
    UPDATE Projects
    SET UpdatedAt = GETDATE()
    FROM Projects p
        INNER JOIN inserted i ON p.ProjectID = i.ProjectID
END
GO

CREATE TRIGGER TR_Tasks_UpdateTimestamp ON Tasks AFTER UPDATE AS
BEGIN
    UPDATE Tasks
    SET UpdatedAt = GETDATE()
    FROM Tasks t
        INNER JOIN inserted i ON t.TaskID = i.TaskID
END
GO

CREATE TRIGGER TR_ProjectMembers_UpdateTimestamp ON ProjectMembers AFTER UPDATE AS
BEGIN
    UPDATE ProjectMembers
    SET UpdatedAt = GETDATE()
    FROM ProjectMembers pm
        INNER JOIN inserted i ON pm.ProjectID = i.ProjectID AND pm.UserID = i.UserID
END

-- 2. Trigger tự động cập nhật số lượng tasks trong project
GO
CREATE TRIGGER TR_UpdateProjectTaskCount
ON Tasks
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    -- Tạo bảng tạm để lưu ProjectID bị ảnh hưởng
    DECLARE @AffectedProjects TABLE (ProjectID UNIQUEIDENTIFIER)

    -- Thu thập ProjectID từ các thao tác INSERT/UPDATE/DELETE
    INSERT INTO @AffectedProjects
        (ProjectID)
            SELECT DISTINCT ProjectID
        FROM inserted
    UNION
        SELECT DISTINCT ProjectID
        FROM deleted

    -- Cập nhật thống kê cho các projects bị ảnh hưởng
    UPDATE Projects
    SET 
        TotalTasks = (
            SELECT COUNT(*)
    FROM Tasks
    WHERE Tasks.ProjectID = Projects.ProjectID
        ),
        CompletedTasks = (
            SELECT COUNT(*)
    FROM Tasks
    WHERE Tasks.ProjectID = Projects.ProjectID
        AND Tasks.Status = 'COMPLETED'
        )
    WHERE Projects.ProjectID IN (SELECT ProjectID
    FROM @AffectedProjects)
END
GO

-- Trigger để cập nhật status của Task thành COMPLETED khi TaskAssignment được cập nhật thành COMPLETED
CREATE TRIGGER TR_UpdateTaskStatusOnSubmission
ON TaskAssignments
AFTER UPDATE
AS
BEGIN
    IF EXISTS (
        SELECT 1
    FROM inserted i
    WHERE i.Status = 'COMPLETED'
    )
    BEGIN
        DECLARE @TaskID UNIQUEIDENTIFIER;
        SELECT @TaskID = TaskID
        FROM inserted;

        -- Cập nhật status của Task thành COMPLETED
        UPDATE Tasks
        SET Status = 'COMPLETED',
            UpdatedAt = GETDATE()
        WHERE TaskID = @TaskID;

        -- Cập nhật tất cả TaskAssignments khác của task này thành COMPLETED
        UPDATE TaskAssignments
        SET Status = 'COMPLETED',
            CompletedAt = GETDATE()
        WHERE TaskID = @TaskID
            AND Status = 'IN_PROGRESS';
    END
END;

GO
CREATE TRIGGER trg_ProjectMapping
ON Projects
AFTER INSERT
AS
BEGIN
    -- Insert mappings for all inserted projects
    INSERT INTO ProjectIdMapping
        (ProjectID, EncodedProjectID)
    SELECT
        i.ProjectID,
        REPLACE(
            REPLACE(
                REPLACE(
                    CAST('' AS XML).value(
                        'xs:base64Binary(xs:hexBinary(sql:column("encrypted")))',
                        'VARCHAR(MAX)'
                    ),
                    '+', '-'
                ),
                '/', '_'
            ),
            '=', ''
        )
    FROM inserted i
    CROSS APPLY (
        SELECT HASHBYTES('SHA2_256', CONCAT(i.ProjectID, 'your-secret-salt', 'PROJECT')) as encrypted
    ) t;
END;


GO
CREATE TRIGGER trg_TaskMapping
ON Tasks
AFTER INSERT
AS
BEGIN
    -- Insert mappings for all inserted tasks
    INSERT INTO TaskIdMapping
        (TaskID, EncodedTaskID)
    SELECT
        i.TaskID,
        REPLACE(
            REPLACE(
                REPLACE(
                    CAST('' AS XML).value(
                        'xs:base64Binary(xs:hexBinary(sql:column("encrypted")))',
                        'VARCHAR(MAX)'
                    ),
                    '+', '-'
                ),
                '/', '_'
            ),
            '=', ''
        )
    FROM inserted i
    CROSS APPLY (
        SELECT HASHBYTES('SHA2_256', CONCAT(i.TaskID, 'your-secret-salt', 'TASK')) as encrypted
    ) t;
END;



-- Insert sample users (password: "@Password123" hashed with BCrypt)
select * from ProjectIdMapping
select * from TaskIdMapping