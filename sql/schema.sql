Create Database TaskManager
use TaskManager
-- Users table for authentication and basic user information
CREATE TABLE Users
(
    UserID UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    Username NVARCHAR(50) UNIQUE NOT NULL,
    Password NVARCHAR(255) NOT NULL,
    Role NVARCHAR(20) CHECK (Role IN ('Manager', 'Member')) NOT NULL,
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
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (ManagerID) REFERENCES Users(UserID)
);

-- Tasks table
CREATE TABLE Tasks
(
    TaskID UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    ProjectID UNIQUEIDENTIFIER NOT NULL,
    TaskName NVARCHAR(100) NOT NULL,
    Description NVARCHAR(MAX),
    Status NVARCHAR(20) CHECK (Status IN ('Pending', 'In Progress', 'Completed')) DEFAULT 'Pending',
    DueDate DATE,
    AssignedTo UNIQUEIDENTIFIER,
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (ProjectID) REFERENCES Projects(ProjectID),
    FOREIGN KEY (AssignedTo) REFERENCES Users(UserID)
);

-- Project Members table (for managing team members in projects)
CREATE TABLE ProjectMembers
(
    ProjectID UNIQUEIDENTIFIER NOT NULL,
    UserID UNIQUEIDENTIFIER NOT NULL,
    JoinedAt DATETIME DEFAULT GETDATE(),
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE(),
    PRIMARY KEY (ProjectID, UserID),
    FOREIGN KEY (ProjectID) REFERENCES Projects(ProjectID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Add new table for task assignments
CREATE TABLE TaskAssignments
(
    TaskID UNIQUEIDENTIFIER,
    UserID UNIQUEIDENTIFIER,
    Status NVARCHAR(20) DEFAULT 'PENDING',
    SubmissionLink NVARCHAR(MAX),
    SubmissionFilePath NVARCHAR(MAX),
    AssignedAt DATETIME DEFAULT GETDATE(),
    CompletedAt DATETIME,
    PRIMARY KEY (TaskID, UserID),
    FOREIGN KEY (TaskID) REFERENCES Tasks(TaskID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Add submission columns to Tasks table
ALTER TABLE Tasks ADD
    SubmissionLink NVARCHAR(MAX),
    SubmissionFilePath NVARCHAR(MAX);

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
        AND Tasks.Status = 'Completed'
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
    -- Kiểm tra nếu có bản ghi vừa được cập nhật thành COMPLETED
    IF EXISTS (
        SELECT 1
    FROM inserted i
    WHERE i.Status = 'COMPLETED'
    )
    BEGIN
        -- Lấy TaskID từ bản ghi vừa được cập nhật
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