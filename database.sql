Create Database TaskManager
-- Users table for authentication and basic user information
CREATE TABLE Users
(
    UserId UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    Username NVARCHAR(50) UNIQUE NOT NULL,
    Password NVARCHAR(255) NOT NULL,
    Role NVARCHAR(20) CHECK (Role IN ('Manager', 'Member')) NOT NULL,
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE()
);

-- Profiles table for detailed user information
CREATE TABLE Profiles
(
    ProfileId UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    UserId UNIQUEIDENTIFIER UNIQUE NOT NULL,
    FirstName NVARCHAR(50) NOT NULL,
    LastName NVARCHAR(50) NOT NULL,
    Email NVARCHAR(100) UNIQUE NOT NULL,
    Phone NVARCHAR(15) UNIQUE NOT NULL,
    DateOfBirth DATE,
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (UserId) REFERENCES Users(UserId)
);

-- Projects table
CREATE TABLE Projects
(
    ProjectId UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    ProjectName NVARCHAR(100) NOT NULL,
    Description NVARCHAR(MAX),
    StartDate DATE NOT NULL,
    EndDate DATE,
    ManagerId UNIQUEIDENTIFIER NOT NULL,
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (ManagerId) REFERENCES Users(UserId)
);

-- Tasks table
CREATE TABLE Tasks
(
    TaskId UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    ProjectId UNIQUEIDENTIFIER NOT NULL,
    TaskName NVARCHAR(100) NOT NULL,
    Description NVARCHAR(MAX),
    Status NVARCHAR(20) CHECK (Status IN ('Pending', 'In Progress', 'Completed')) DEFAULT 'Pending',
    DueDate DATE,
    AssignedTo UNIQUEIDENTIFIER,
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (ProjectId) REFERENCES Projects(ProjectId),
    FOREIGN KEY (AssignedTo) REFERENCES Users(UserId)
);

-- Project Members table (for managing team members in projects)
CREATE TABLE ProjectMembers
(
    ProjectId UNIQUEIDENTIFIER NOT NULL,
    UserId UNIQUEIDENTIFIER NOT NULL,
    JoinedAt DATETIME DEFAULT GETDATE(),
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE(),
    PRIMARY KEY (ProjectId, UserId),
    FOREIGN KEY (ProjectId) REFERENCES Projects(ProjectId),
    FOREIGN KEY (UserId) REFERENCES Users(UserId)
);

-- Trigger để tự động cập nhật UpdatedAt khi dữ liệu thay đổi
GO
CREATE TRIGGER TR_Users_UpdateTimestamp ON Users AFTER UPDATE AS
BEGIN
    UPDATE Users
    SET UpdatedAt = GETDATE()
    FROM Users u
        INNER JOIN inserted i ON u.UserId = i.UserId
END
GO

CREATE TRIGGER TR_Profiles_UpdateTimestamp ON Profiles AFTER UPDATE AS
BEGIN
    UPDATE Profiles
    SET UpdatedAt = GETDATE()
    FROM Profiles p
        INNER JOIN inserted i ON p.ProfileId = i.ProfileId
END
GO

CREATE TRIGGER TR_Projects_UpdateTimestamp ON Projects AFTER UPDATE AS
BEGIN
    UPDATE Projects
    SET UpdatedAt = GETDATE()
    FROM Projects p
        INNER JOIN inserted i ON p.ProjectId = i.ProjectId
END
GO

CREATE TRIGGER TR_Tasks_UpdateTimestamp ON Tasks AFTER UPDATE AS
BEGIN
    UPDATE Tasks
    SET UpdatedAt = GETDATE()
    FROM Tasks t
        INNER JOIN inserted i ON t.TaskId = i.TaskId
END
GO

CREATE TRIGGER TR_ProjectMembers_UpdateTimestamp ON ProjectMembers AFTER UPDATE AS
BEGIN
    UPDATE ProjectMembers
    SET UpdatedAt = GETDATE()
    FROM ProjectMembers pm
        INNER JOIN inserted i ON pm.ProjectId = i.ProjectId AND pm.UserId = i.UserId
END

-- 2. Trigger tự động cập nhật số lượng tasks trong project
GO
CREATE TRIGGER TR_UpdateProjectTaskCount
ON Tasks
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    -- Tạo bảng tạm để lưu ProjectId bị ảnh hưởng
    DECLARE @AffectedProjects TABLE (ProjectId UNIQUEIDENTIFIER)

    -- Thu thập ProjectId từ các thao tác INSERT/UPDATE/DELETE
    INSERT INTO @AffectedProjects
        (ProjectId)
            SELECT DISTINCT ProjectId
        FROM inserted
    UNION
        SELECT DISTINCT ProjectId
        FROM deleted

    -- Cập nhật thống kê cho các projects bị ảnh hưởng
    UPDATE Projects
    SET 
        TotalTasks = (
            SELECT COUNT(*)
    FROM Tasks
    WHERE Tasks.ProjectId = Projects.ProjectId
        ),
        CompletedTasks = (
            SELECT COUNT(*)
    FROM Tasks
    WHERE Tasks.ProjectId = Projects.ProjectId
        AND Tasks.Status = 'Completed'
        )
    WHERE Projects.ProjectId IN (SELECT ProjectId
    FROM @AffectedProjects)
END
GO