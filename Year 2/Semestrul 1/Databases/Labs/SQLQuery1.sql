IF OBJECT_ID('Players', 'U') IS NOT NULL
	DROP TABLE Players;
IF OBJECT_ID('Staff', 'U') IS NOT NULL
	DROP TABLE Staff;
IF OBJECT_ID('Matches', 'U') IS NOT NULL
	DROP TABLE Matches;
IF OBJECT_ID('Coaches', 'U') IS NOT NULL
	DROP TABLE Coaches;
IF OBJECT_ID('Equipment', 'U') IS NOT NULL
	DROP TABLE Equipment;
IF OBJECT_ID('MedicalRecords', 'U') IS NOT NULL
	DROP TABLE MedicalRecords;
IF OBJECT_ID('TrainingSessions', 'U') IS NOT NULL
	DROP TABLE TrainingSessions;
IF OBJECT_ID('PlayerStatistics', 'U') IS NOT NULL
	DROP TABLE PlayerStatistics;
IF OBJECT_ID('ScoutingReports', 'U') IS NOT NULL
	DROP TABLE ScoutingReports;
IF OBJECT_ID('PlayerTraining', 'U') IS NOT NULL
	DROP TABLE PlayerTraining;

CREATE TABLE Players (
	PlayerID INT PRIMARY KEY,
	FirstName VARCHAR(50),
	LastName VARCHAR(50),
	Position VARCHAR(30),
	DateOfBirth DATE,
	Nationality VARCHAR(50)
);

CREATE TABLE Staff (
	StaffID INT PRIMARY KEY,
	FirstName VARCHAR(50),
	LastName VARCHAR(50),
	Role VARCHAR(50),
	Salary DECIMAL
);
ALTER TABLE Staff
DROP COLUMN StaffID;

ALTER TABLE Staff
ADD StaffID INT IDENTITY(1,1) NOT NULL PRIMARY KEY;


CREATE TABLE Matches (
	MatchID INT PRIMARY KEY,
	MatchDate DATE,
	MatchLocation VARCHAR(100),
	Opponent VARCHAR(100),
	Result VARCHAR(50),
	Scoreline VARCHAR(50)
);


CREATE TABLE TrainingSessions (
	SessionID INT PRIMARY KEY,
	SessionDate DATE,
	Duration INT,
	FocusArea VARCHAR(100)
);

CREATE TABLE Coaches(
	CoachID INT PRIMARY KEY,
	SessionID INT,
	LastName VARCHAR(50),
	FirstName VARCHAR(50),
	TrainingType VARCHAR(50),
	FOREIGN KEY (SessionID) REFERENCES TrainingSessions(SessionID)
);

CREATE TABLE Equipment (
	EquipmentID INT PRIMARY KEY,
	EquipmentType VARCHAR(50),
	Quantity INT,
	Condition VARCHAR(50)
);

CREATE TABLE PlayerStatistics (
	StatID INT PRIMARY KEY,
	PlayerID INT,
	MatchID INT,
	Goals INT,
	Assists INT,
	MinutesPlayed INT,
	FOREIGN KEY (PlayerID) REFERENCES Players(PlayerID),
	FOREIGN KEY (MatchID) REFERENCES Matches(MatchID)
);


CREATE TABLE MedicalRecords (
	RecordID INT PRIMARY KEY,
	PlayerID INT,
	RecordDate DATE,
	InjuryDetails VARCHAR(255),
	RecoveryTime VARCHAR(50),
	FOREIGN KEY (PlayerID) REFERENCES Players(PlayerID)
);

CREATE TABLE PlayerTraining (
	PlayerID INT,
	SessionID INT,
	PerformanceRating INT,
	PRIMARY KEY (PlayerID, SessionID),
	FOREIGN KEY (PlayerID) REFERENCES Players(PlayerID),
	FOREIGN KEY (SessionID) REFERENCES TrainingSessions(SessionID)
);

CREATE TABLE ScoutingReports (
    ReportID INT PRIMARY KEY,
    ScoutDate DATE,
    PlayerObserved VARCHAR(100),
    PotentialRating INT,
    ScoutID INT,
    FOREIGN KEY (ScoutID) REFERENCES Staff(StaffID)
);

INSERT INTO Players (PlayerID, FirstName, LastName, Position, DateOfBirth, Nationality)
VALUES 
(1, 'John', 'Doe', 'Forward', '1990-07-10', 'American'),
(2, 'Alice', 'Johnson', 'Midfielder', '1992-05-15', 'Canadian'),
(3, 'Carlos', 'Mendez', 'Defender', '1988-09-20', 'Spanish'),
(4, 'Emma', 'White', 'Goalkeeper', '1993-11-30', 'English');

INSERT INTO Staff (StaffID, FirstName, LastName, Role, Salary)
VALUES 
(1, 'Jane', 'Smith', 'Scout', 50000),
(2, 'Bob', 'Brown', 'Coach', 55000),
(3, 'Sally', 'Green', 'Physiotherapist', 45000),
(4, 'Frank', 'Black', 'Manager', 75000);

INSERT INTO Matches (MatchID, MatchDate, MatchLocation, Opponent, Result, Scoreline)
VALUES 
(1, '2024-10-10', 'Home Stadium', 'Rovers FC', 'Win', '2-0'),
(2, '2024-10-17', 'Away Stadium', 'City FC', 'Loss', '0-1'),
(3, '2024-10-24', 'Home Stadium', 'United FC', 'Draw', '1-1'),
(4, '2024-10-31', 'Away Stadium', 'Nationals FC', 'Win', '3-1');

INSERT INTO TrainingSessions (SessionID, SessionDate, Duration, FocusArea)
VALUES 
(1, '2024-10-05', 120, 'Tactical'),
(2, '2024-10-12', 90, 'Physical'),
(3, '2024-10-19', 110, 'Technical'),
(4, '2024-10-26', 100, 'Recovery');

INSERT INTO Coaches (CoachID, SessionID, LastName, FirstName, TrainingType)
VALUES 
(1, 1, 'Brown', 'Bob', 'Tactical'),
(2, 2, 'Brown', 'Bob', 'Physical'),
(3, 3, 'Black', 'Frank', 'Technical'),
(4, 4, 'Green', 'Sally', 'Recovery');

INSERT INTO Equipment (EquipmentID, EquipmentType, Quantity, Condition)
VALUES 
(1, 'Ball', 20, 'Good'),
(2, 'Cone', 50, 'Average'),
(3, 'Jersey', 25, 'New'),
(4, 'Goal Net', 4, 'Poor');

INSERT INTO PlayerStatistics (StatID, PlayerID, MatchID, Goals, Assists, MinutesPlayed)
VALUES 
(1, 1, 1, 2, 0, 90),
(2, 2, 1, 0, 2, 85),
(3, 3, 2, 0, 0, 90),
(4, 4, 3, 1, 1, 45);

INSERT INTO MedicalRecords (RecordID, PlayerID, RecordDate, InjuryDetails, RecoveryTime)
VALUES 
(1, 1, '2024-09-10', 'Sprained ankle', '2 weeks'),
(2, 2, '2024-10-01', 'Knee inflammation', '1 week'),
(3, 3, '2024-08-15', 'Broken finger', '3 weeks');

INSERT INTO PlayerTraining (PlayerID, SessionID, PerformanceRating)
VALUES 
(1, 1, 8),
(2, 2, 7),
(3, 3, 9),
(4, 4, 6);

INSERT INTO ScoutingReports (ReportID, ScoutDate, PlayerObserved, PotentialRating, ScoutID)
VALUES 
(1, '2024-10-15', 'Michael Oher', 9, 1),
(2, '2024-10-18', 'Lucy Hale', 8, 1),
(3, '2024-10-22', 'Will Smith', 7, 1),
(4, '2024-10-25', 'Stephen Curry', 10, 2);


ALTER TABLE Matches
ADD Result VARCHAR(50),
    Scoreline VARCHAR(50);

UPDATE Matches
SET Result = 'Draw', Scoreline = '1-1'
WHERE MatchDate BETWEEN '2024-01-01' AND '2024-12-31' AND MatchID IN (1);

UPDATE Staff
SET Salary = Salary + 1000
WHERE Salary < 60000 AND Role NOT LIKE 'Coach%';

UPDATE Players
SET Position = 'Midfielder'
WHERE DateOfBirth >= '1990-01-01' AND LastName IS NOT NULL;

DELETE FROM PlayerTraining
WHERE SessionID = 2 OR PerformanceRating IS NULL;

DELETE FROM MedicalRecords
WHERE PlayerID = 3 AND RecoveryTime = '3 weeks';

-- Lists all forwards and all midfielders
SELECT FirstName, LastName, Position FROM Players WHERE Position = 'Forward'
UNION ALL
SELECT FirstName, LastName, Position FROM Players WHERE Position = 'Midfielder';

-- Lists all coaches and scouts
SELECT FirstName, LastName, Role FROM Staff WHERE Role = 'Coach'
UNION
SELECT FirstName, LastName, Role FROM Staff WHERE Role = 'Scout';

-- Finds American players born after 1990
SELECT PlayerID FROM Players WHERE Nationality = 'American'
INTERSECT
SELECT PlayerID FROM Players WHERE DateOfBirth > '1990-01-01';

-- Finds matches that were won at Away Stadium
SELECT MatchID, Opponent FROM Matches WHERE Result = 'Win' 
AND MatchLocation IN (SELECT MatchLocation FROM Matches WHERE MatchLocation = 'Away Stadium');

--Lists players who have never been injured
SELECT PlayerID, FirstName, LastName FROM Players
EXCEPT
SELECT PlayerID, FirstName, LastName FROM Players WHERE PlayerID IN (SELECT PlayerID FROM MedicalRecords);

-- Finds players who have never scored or assisted
SELECT FirstName, LastName 
FROM Players WHERE PlayerID NOT IN 
(SELECT PlayerID FROM PlayerStatistics WHERE Goals > 0 OR Assists > 0);

-- Shows player statistics for each match where the result was a draw
SELECT p.FirstName, p.LastName, ps.Goals, ps.Assists, m.MatchDate, m.Result
FROM Players p
INNER JOIN PlayerStatistics ps ON p.PlayerID = ps.PlayerID
INNER JOIN Matches m ON m.MatchID = ps.MatchID
WHERE m.Result = 'Draw';

-- Lists all players and their scoring records, if any
SELECT p.FirstName, p.LastName, COALESCE(ps.Goals, 0) AS Goals
FROM Players p
LEFT JOIN PlayerStatistics ps ON p.PlayerID = ps.PlayerID
ORDER BY p.LastName;

-- Lists all training sessions along with the coach if assigned
SELECT t.SessionDate, t.FocusArea, c.FirstName, c.LastName
FROM TrainingSessions t
RIGHT JOIN Coaches c ON t.SessionID = c.SessionID;

-- Lists all staff and any specific coaching sessions they are responsible for
SELECT s.FirstName, s.LastName, s.Role, c.SessionID, c.TrainingType
FROM Staff s
FULL JOIN Coaches c ON s.StaffID = c.CoachID;

-- Finds matches where specific players played, identified by first name
SELECT MatchID, MatchDate, Opponent FROM Matches
WHERE MatchID IN (SELECT MatchID FROM PlayerStatistics WHERE PlayerID IN (SELECT PlayerID FROM Players WHERE FirstName IN ('John', 'Alice')));

-- Lists all players who have a medical record
SELECT FirstName, LastName FROM Players p
WHERE EXISTS (SELECT 1 FROM MedicalRecords m WHERE m.PlayerID = p.PlayerID);

-- Lists scouts who have reported on players with a potential rating above 8
SELECT FirstName, LastName FROM Staff s
WHERE EXISTS (
  SELECT 1 FROM ScoutingReports sr
  WHERE sr.ScoutID = s.StaffID AND sr.PotentialRating > 8
);

-- Summarizes total goals and assists by player
SELECT FirstName, LastName, SUM(Goals) AS TotalGoals, SUM(Assists) AS TotalAssists
FROM (SELECT p.FirstName, p.LastName, ps.Goals, ps.Assists FROM Players p JOIN PlayerStatistics ps ON p.PlayerID = ps.PlayerID) AS PlayerStats
GROUP BY FirstName, LastName;

-- Lists players whose goal tally exceeds the average
SELECT FirstName, LastName, Goals FROM (SELECT p.FirstName, p.LastName, SUM(ps.Goals) AS Goals FROM Players p JOIN PlayerStatistics ps ON p.PlayerID = ps.PlayerID GROUP BY p.FirstName, p.LastName) AS TotalGoals
WHERE Goals > (SELECT AVG(Goals) FROM PlayerStatistics);

-- Counts players by nationality
SELECT Nationality, COUNT(*) AS NumberOfPlayers
FROM Players
GROUP BY Nationality;

-- Lists focus areas where average session duration exceeds 100 minutes
SELECT FocusArea, AVG(Duration) AS AvgDuration
FROM TrainingSessions
GROUP BY FocusArea
HAVING AVG(Duration) > 100;

-- Lists teams whose average player goals are above the overall average
SELECT m.Opponent, AVG(ps.Goals) AS AvgGoals
FROM Matches m
JOIN PlayerStatistics ps ON m.MatchID = ps.MatchID
GROUP BY m.Opponent
HAVING AVG(ps.Goals) > (SELECT AVG(Goals) FROM PlayerStatistics);

-- Lists players who scored more goals in any match than the average in any other match
SELECT FirstName, LastName FROM Players p
WHERE EXISTS (
  SELECT 1 FROM PlayerStatistics ps
  WHERE ps.PlayerID = p.PlayerID AND ps.Goals > ANY (SELECT AVG(Goals) FROM PlayerStatistics GROUP BY MatchID)
);

-- Lists players who have assisted in every match they played
SELECT FirstName, LastName FROM Players p
WHERE NOT EXISTS (
  SELECT 1 FROM PlayerStatistics ps
  WHERE ps.PlayerID = p.PlayerID AND ps.Assists = 0
);

-- Lists players whose total assists are greater than the highest single-match assists
SELECT FirstName, LastName FROM Players p
WHERE (SELECT SUM(ps.Assists) FROM PlayerStatistics ps WHERE ps.PlayerID = p.PlayerID) > ALL 
(SELECT MAX(Assists) FROM PlayerStatistics GROUP BY MatchID);

-- Lists opponents where the scoreline was the maximum recorded
SELECT Opponent FROM Matches
WHERE Scoreline = (SELECT MAX(Scoreline) FROM Matches);

-- Lists distinct nationalities of players
SELECT DISTINCT Nationality
FROM Players
ORDER BY Nationality;

-- Lists the top 3 longest training sessions
SELECT TOP 3 SessionID, SessionDate, Duration, FocusArea
FROM TrainingSessions
ORDER BY Duration DESC;

-- Orders players by nationality and date of birth
SELECT FirstName, LastName, Nationality, DateOfBirth
FROM Players
ORDER BY Nationality, DateOfBirth DESC;

-- Lists all staff members, sorted by their salary in descending order
SELECT FirstName, LastName, Role, Salary
FROM Staff
ORDER BY Salary DESC;

-- Lists players who scored or assisted in either wins or draws, playing at least 80% of the match
SELECT DISTINCT p.FirstName, p.LastName, m.MatchDate, m.Result, ps.Goals, ps.Assists
FROM PlayerStatistics ps
JOIN Players p ON p.PlayerID = ps.PlayerID
JOIN Matches m ON m.MatchID = ps.MatchID
WHERE ((m.Result = 'Win' OR m.Result = 'Draw') AND (ps.Goals > 0 OR ps.Assists > 0))
  AND ps.MinutesPlayed >= 72
ORDER BY m.MatchDate DESC, p.LastName;

-- Finds players who have scored more goals than any other player in a specific match
SELECT FirstName, LastName
FROM Players
WHERE PlayerID = ANY (SELECT PlayerID FROM PlayerStatistics WHERE MatchID = 1 AND Goals > 0);

-- Finds players who have played more minutes than all other players in a specific match
SELECT FirstName, LastName
FROM Players
WHERE PlayerID = ALL (SELECT PlayerID FROM PlayerStatistics WHERE MatchID = 1 AND MinutesPlayed >= ALL 
(SELECT MinutesPlayed FROM PlayerStatistics WHERE MatchID = 1));

-- Finds players that scored more goals than the average in the first match
SELECT p.FirstName, p.LastName, AVG(ps.Goals) AS Goals
FROM Players p
JOIN PlayerStatistics ps ON p.PlayerID = ps.PlayerID
GROUP BY p.FirstName, p.LastName
HAVING AVG(ps.Goals) > (
    SELECT AVG(Goals)
    FROM PlayerStatistics
    WHERE MatchID = 1
)
ORDER BY Goals DESC;

-- Finds players who have played more minutes than the average minutes played in MatchID = 1
SELECT FirstName, LastName
FROM Players p
WHERE EXISTS (
  SELECT 1 FROM PlayerStatistics ps WHERE ps.PlayerID = p.PlayerID 
  AND ps.MinutesPlayed > (SELECT AVG(MinutesPlayed) FROM PlayerStatistics WHERE MatchID = 1)
);

-- Finds players who have scored a unique amount of goals in Match 1
SELECT FirstName, LastName
FROM Players p
WHERE NOT EXISTS (
  SELECT 1 FROM PlayerStatistics ps WHERE ps.PlayerID = p.PlayerID 
  AND ps.Goals NOT IN (SELECT Goals FROM PlayerStatistics WHERE MatchID = 1 AND Goals > 0)
);

-- Finds players who played an unique number of minutes in Match 1
SELECT FirstName, LastName
FROM Players p
WHERE NOT EXISTS (
  SELECT 1 FROM PlayerStatistics ps WHERE ps.PlayerID = p.PlayerID 
  AND ps.MinutesPlayed NOT IN (SELECT MinutesPlayed FROM PlayerStatistics WHERE MatchID = 1)
);

INSERT INTO PlayerStatistics (StatID, PlayerID, MatchID, Goals, Assists, MinutesPlayed)
VALUES (10, 9999, 8888, 1, 2, 85);
 
CREATE TABLE DatabaseVersion (
    VersionID INT PRIMARY KEY,
    AppliedDate DATETIME DEFAULT GETDATE()
);

INSERT INTO DatabaseVersion (VersionID) VALUES (1);

-- change column type
CREATE PROCEDURE Change_Column_Type
AS
BEGIN
    ALTER TABLE Players ALTER COLUMN Position VARCHAR(100);
    UPDATE DatabaseVersion SET VersionID = 2;
END;

-- column type change
CREATE PROCEDURE Revert_Column_Type
AS
BEGIN
    ALTER TABLE Players ALTER COLUMN Position VARCHAR(30);
    UPDATE DatabaseVersion SET VersionID = 1;
END;

-- a DEFAULT constraint
CREATE PROCEDURE Add_Default_Constraint
AS
BEGIN
    ALTER TABLE Players ADD CONSTRAINT DF_Position DEFAULT 'Unknown' FOR Position;
    UPDATE DatabaseVersion SET VersionID = 3;
END;

-- remove a DEFAULT constraint
CREATE PROCEDURE Remove_Default_Constraint
AS
BEGIN
    ALTER TABLE Players DROP CONSTRAINT DF_Position;
    UPDATE DatabaseVersion SET VersionID = 2;
END;

-- add a new column
CREATE PROCEDURE Add_Column
AS
BEGIN
    ALTER TABLE Players ADD Height DECIMAL(5,2);
    UPDATE DatabaseVersion SET VersionID = 4;
END;

-- remove a column
CREATE PROCEDURE Remove_Column
AS
BEGIN
    ALTER TABLE Players DROP COLUMN Height;
    UPDATE DatabaseVersion SET VersionID = 3;
END;

-- create a new table
CREATE PROCEDURE Create_New_Table
AS
BEGIN
    CREATE TABLE TeamColors (
        ColorID INT PRIMARY KEY,
        TeamColor VARCHAR(50)
    );
    UPDATE DatabaseVersion SET VersionID = 5;
END;

-- drop the new table
CREATE PROCEDURE Drop_New_Table
AS
BEGIN
    DROP TABLE TeamColors;
    UPDATE DatabaseVersion SET VersionID = 4;
END;

-- add a foreign key
CREATE PROCEDURE Add_Foreign_Key
AS
BEGIN
    ALTER TABLE Coaches ADD CONSTRAINT FK_Coaches_Staff FOREIGN KEY (CoachID) REFERENCES Staff(StaffID);
    UPDATE DatabaseVersion SET VersionID = 6;
END;

-- remove a foreign key
CREATE PROCEDURE Remove_Foreign_Key
AS
BEGIN
    ALTER TABLE Coaches DROP CONSTRAINT FK_Coaches_Staff;
    UPDATE DatabaseVersion SET VersionID = 5;
END;

-- change to specific version
CREATE PROCEDURE Update_Database_Version @TargetVersion INT
AS
BEGIN
    DECLARE @CurrentVersion INT;
    SELECT @CurrentVersion = VersionID FROM DatabaseVersion;

    WHILE @CurrentVersion < @TargetVersion AND @TargetVersion <= 6
    BEGIN
        IF @CurrentVersion = 1 AND @TargetVersion >= 2 BEGIN EXEC Change_Column_Type; SET @CurrentVersion = 2; END;
        IF @CurrentVersion = 2 AND @TargetVersion >= 3 BEGIN EXEC Add_Default_Constraint; SET @CurrentVersion = 3; END;
        IF @CurrentVersion = 3 AND @TargetVersion >= 4 BEGIN EXEC Add_Column; SET @CurrentVersion = 4; END;
        IF @CurrentVersion = 4 AND @TargetVersion >= 5 BEGIN EXEC Create_New_Table; SET @CurrentVersion = 5; END;
        IF @CurrentVersion = 5 AND @TargetVersion >= 6 BEGIN EXEC Add_Foreign_Key; SET @CurrentVersion = 6; END;
    END;

    WHILE @CurrentVersion > @TargetVersion AND @TargetVersion <= 6
    BEGIN
        IF @CurrentVersion = 6 AND @TargetVersion < 6 BEGIN EXEC Remove_Foreign_Key; SET @CurrentVersion = 5; END;
        IF @CurrentVersion = 5 AND @TargetVersion < 5 BEGIN EXEC Drop_New_Table; SET @CurrentVersion = 4; END;
        IF @CurrentVersion = 4 AND @TargetVersion < 4 BEGIN EXEC Remove_Column; SET @CurrentVersion = 3; END;
        IF @CurrentVersion = 3 AND @TargetVersion < 3 BEGIN EXEC Remove_Default_Constraint; SET @CurrentVersion = 2; END;
        IF @CurrentVersion = 2 AND @TargetVersion < 2 BEGIN EXEC Revert_Column_Type; SET @CurrentVersion = 1; END;
    END;

    UPDATE DatabaseVersion SET VersionID = @TargetVersion;
END;

EXEC Update_Database_Version 5;

select * from DatabaseVersion;

CREATE TABLE [TestOwners] (
    [TestOwnerID] INT IDENTITY(1,1) NOT NULL,
    [TestID] INT NOT NULL,
    CONSTRAINT [PK_TestOwners] PRIMARY KEY CLUSTERED ([TestOwnerID]),
    CONSTRAINT [FK_TestOwners_Tests] FOREIGN KEY ([TestID]) REFERENCES [Tests]([TestID]) ON DELETE CASCADE ON UPDATE CASCADE
);

IF OBJECT_ID('dbo.vw_StaffBasic', 'V') IS NOT NULL 
    DROP VIEW dbo.vw_StaffBasic;
GO
CREATE VIEW dbo.vw_StaffBasic
AS
SELECT StaffID, FirstName, LastName, Role, Salary
FROM dbo.Staff;
GO

IF OBJECT_ID('dbo.vw_PlayerStats', 'V') IS NOT NULL 
    DROP VIEW dbo.vw_PlayerStats;
GO
CREATE VIEW dbo.vw_PlayerStats
AS
SELECT p.PlayerID, p.FirstName, p.LastName,
       ps.StatID, ps.Goals, ps.Assists, ps.MinutesPlayed
FROM dbo.Players p
INNER JOIN dbo.PlayerStatistics ps ON p.PlayerID = ps.PlayerID;
GO

IF OBJECT_ID('dbo.vw_TotalGoalsPerPlayer', 'V') IS NOT NULL 
    DROP VIEW dbo.vw_TotalGoalsPerPlayer;
GO
CREATE VIEW dbo.vw_TotalGoalsPerPlayer
AS
SELECT p.PlayerID,
       p.FirstName + ' ' + p.LastName AS FullName,
       SUM(ps.Goals) AS TotalGoals,
       COUNT(ps.MatchID) AS MatchesPlayed
FROM dbo.Players p
INNER JOIN dbo.PlayerStatistics ps ON p.PlayerID = ps.PlayerID
GROUP BY p.PlayerID, p.FirstName, p.LastName;
GO

INSERT INTO [Tables] (Name) VALUES
('Staff'),            -- 1
('ScoutingReports'),  -- 2
('Players'),          -- 3
('Matches'),          -- 4
('PlayerStatistics'), -- 5
('TrainingSessions'), -- 6
('PlayerTraining');   -- 7

-- Insert view names into [Views]
INSERT INTO [Views] (Name) VALUES
('vw_StaffBasic'),           -- 1
('vw_PlayerStats'),          -- 2
('vw_TotalGoalsPerPlayer');  -- 3

SELECT * FROM [Tables];
SELECT * FROM [Views];

INSERT INTO Tests (Name) VALUES ('test');
DECLARE @TestID INT = SCOPE_IDENTITY();
INSERT INTO TestTables (TestID, TableID, NoOfRows, Position) 
VALUES 
  (@TestID, 2,  5, 1),  -- ScoutingReports
  (@TestID, 5,  5, 2),  -- PlayerStatistics
  (@TestID, 7,  5, 3),  -- PlayerTraining
  (@TestID, 1,  5, 4),  -- Staff
  (@TestID, 3,  5, 5),  -- Players
  (@TestID, 4,  5, 6),  -- Matches
  (@TestID, 6,  5, 7);  -- TrainingSessions

-- Link the 3 views to this test
--   vw_StaffBasic -> (1)
--   vw_PlayerStats -> (2)
--   vw_TotalGoalsPerPlayer -> (3)
INSERT INTO TestViews (TestID, ViewID)
VALUES
  (@TestID, 1),
  (@TestID, 2),
  (@TestID, 3);
GO

IF OBJECT_ID('dbo.spRunTest','P') IS NOT NULL
   DROP PROCEDURE dbo.spRunTest;
GO

CREATE PROCEDURE dbo.spRunTest
(
    @TestID INT,                          -- The ID of the test we want to run
    @Description NVARCHAR(2000) = NULL    -- Optional description
)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @TestRunID INT;
    DECLARE @StartTime DATETIME = GETDATE();
    DECLARE @EndTime DATETIME;

    ----------------------------------------------------------------------------
    -- 1) Insert a new row in TestRuns to mark the start of this test run
    ----------------------------------------------------------------------------
    INSERT INTO TestRuns (Description, StartAt)
    VALUES (@Description, @StartTime);

    SET @TestRunID = SCOPE_IDENTITY();  -- capture newly inserted TestRunID

    ----------------------------------------------------------------------------
    -- 2) DELETE Data from each table in ascending Position
    ----------------------------------------------------------------------------
    DECLARE @DelCursor CURSOR;
    DECLARE @DelTableID INT, @DelTableName SYSNAME;
    DECLARE @DelPos INT, @DelNoOfRows INT;
	
    SET @DelCursor = CURSOR FAST_FORWARD FOR
        SELECT tt.TableID, t.Name, tt.Position, tt.NoOfRows
        FROM TestTables tt
        INNER JOIN [Tables] t ON tt.TableID = t.TableID
        WHERE tt.TestID = @TestID
        ORDER BY tt.Position ASC; -- ascending = child tables get deleted first

    OPEN @DelCursor;
    FETCH NEXT FROM @DelCursor INTO @DelTableID, @DelTableName, @DelPos, @DelNoOfRows;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        DECLARE @sqlDel NVARCHAR(MAX);

        -- Build a simple "DELETE FROM [TableName]"
        SET @sqlDel = N'DELETE FROM ' + QUOTENAME(@DelTableName) + N';';
        EXEC (@sqlDel);

        FETCH NEXT FROM @DelCursor INTO @DelTableID, @DelTableName, @DelPos, @DelNoOfRows;
    END

    CLOSE @DelCursor;
    DEALLOCATE @DelCursor;

    ----------------------------------------------------------------------------
    -- 3) INSERT Data into each table in descending Position
    ----------------------------------------------------------------------------
    DECLARE @InsCursor CURSOR;
    DECLARE @InsTableID INT, @InsTableName SYSNAME;
    DECLARE @InsPos INT, @InsNoOfRows INT;

    SET @InsCursor = CURSOR FAST_FORWARD FOR
        SELECT tt.TableID, t.Name, tt.Position, tt.NoOfRows
        FROM TestTables tt
        INNER JOIN [Tables] t ON tt.TableID = t.TableID
        WHERE tt.TestID = @TestID
        ORDER BY tt.Position DESC; -- descending = parent tables get inserted first

    OPEN @InsCursor;
    FETCH NEXT FROM @InsCursor INTO @InsTableID, @InsTableName, @InsPos, @InsNoOfRows;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        DECLARE @InsertStart DATETIME = GETDATE();

        ----------------------------------------------------------------------------
        -- We'll loop from 1 to @InsNoOfRows for each table
        ----------------------------------------------------------------------------
        DECLARE @i INT = 1;
        WHILE @i <= @InsNoOfRows
        BEGIN
            DECLARE @sql NVARCHAR(MAX);

            IF @InsTableName = 'TrainingSessions'
            BEGIN
                /*
                  PK = SessionID (manually provided).
                  For day we do '2025-01-' + (1..5, etc.). 
                */
                SET @sql = N'INSERT INTO TrainingSessions (SessionID, SessionDate, Duration)
                             VALUES (' 
                    + CONVERT(VARCHAR(10), 2000 + @i)
                    + N', ''2025-01-' 
                    + RIGHT('00' + CONVERT(VARCHAR(2), @i), 2)  -- produce '01','02', etc.
                    + N''', 90);';
            END
            ELSE IF @InsTableName = 'Matches'
            BEGIN
                /*
                  PK = MatchID
                */
                SET @sql = N'INSERT INTO Matches (MatchID, MatchDate, Opponent)
                             VALUES ('
                    + CONVERT(VARCHAR(10), 3000 + @i)
                    + N', ''2025-02-' 
                    + RIGHT('00' + CONVERT(VARCHAR(2), @i), 2)
                    + N''', ''Opponent' 
                    + CONVERT(VARCHAR(2), @i) 
                    + N''');';
            END
            ELSE IF @InsTableName = 'Players'
            BEGIN
                /*
                  PK = PlayerID
                */
                SET @sql = N'INSERT INTO Players (PlayerID, FirstName, LastName, Position)
                             VALUES ('
                    + CONVERT(VARCHAR(10), 4000 + @i)
                    + N', ''PlayerF' + CONVERT(VARCHAR(2), @i)
                    + N''', ''PlayerL' + CONVERT(VARCHAR(2), @i)
                    + N''', ''Midfielder'');';
            END
            ELSE IF @InsTableName = 'Staff'
            BEGIN
                /*
                  PK = StaffID
                */
                SET @sql = N'INSERT INTO Staff (StaffID, FirstName, LastName, Role, Salary)
                             VALUES ('
                    + CONVERT(VARCHAR(10), 1000 + @i)
                    + N', ''StaffF' + CONVERT(VARCHAR(2), @i)
                    + N''', ''StaffL' + CONVERT(VARCHAR(2), @i)
                    + N''', ''Coach'', 2000.00);';
            END
            ELSE IF @InsTableName = 'PlayerTraining'
            BEGIN
                /*
                  Composite PK = (PlayerID, SessionID) 
                  Must match the PlayerIDs and SessionIDs inserted above
                */
                DECLARE @PT_PlayerID INT = 4000 + @i;   -- matches what's inserted in Players
                DECLARE @SessionID INT = 2000 + @i;  -- matches what's inserted in TrainingSessions

                SET @sql = N'INSERT INTO PlayerTraining (PlayerID, SessionID, PerformanceRating)
                             VALUES ('
                    + CONVERT(VARCHAR(10), @PT_PlayerID)
                    + N', '
                    + CONVERT(VARCHAR(10), @SessionID)
                    + N', 7);';
            END
            ELSE IF @InsTableName = 'PlayerStatistics'
            BEGIN
                /*
                  PK = StatID
                  FKs = PlayerID & MatchID
                */
                DECLARE @StatID INT = 5000 + @i;
                DECLARE @MatchID INT = 3000 + @i;
                DECLARE @PlayerID INT = 4000 + @i;

                SET @sql = N'INSERT INTO PlayerStatistics (StatID, PlayerID, MatchID, Goals, Assists, MinutesPlayed)
                             VALUES ('
                    + CONVERT(VARCHAR(10), @StatID) + N', '
                    + CONVERT(VARCHAR(10), @PlayerID) + N', '
                    + CONVERT(VARCHAR(10), @MatchID) + N', 2, 1, 90);';
            END
            ELSE IF @InsTableName = 'ScoutingReports'
            BEGIN
                /*
                  PK = ReportID, 
                  FK = ScoutID references Staff(StaffID)
                */
                DECLARE @ReportID INT = 6000 + @i;
                DECLARE @ScoutID INT = 1000 + @i;  -- Staff offset

                SET @sql = N'INSERT INTO ScoutingReports (ReportID, ScoutDate, PlayerObserved, PotentialRating, ScoutID)
                             VALUES ('
                    + CONVERT(VARCHAR(10), @ReportID)
                    + N', ''2025-03-'
                    + RIGHT('00' + CONVERT(VARCHAR(2), @i), 2)
                    + N''', ''PlayerObs'
                    + CONVERT(VARCHAR(2), @i)
                    + N''', 85, '
                    + CONVERT(VARCHAR(10), @ScoutID) + N');';
            END
            ELSE
            BEGIN
                -- If we didn't handle some table, do nothing or just print a debug.
                SET @sql = N'-- Table not handled: ' + @InsTableName;
            END

            EXEC (@sql);

            SET @i += 1;
        END

        DECLARE @InsertEnd DATETIME = GETDATE();

        -- Log insertion times for this table
        INSERT INTO TestRunTables (TestRunID, TableID, StartAt, EndAt)
        VALUES (@TestRunID, @InsTableID, @InsertStart, @InsertEnd);

        FETCH NEXT FROM @InsCursor INTO @InsTableID, @InsTableName, @InsPos, @InsNoOfRows;
    END

    CLOSE @InsCursor;
    DEALLOCATE @InsCursor;

    ----------------------------------------------------------------------------
    -- 4) EVALUATE Views (TestViews) and log performance
    ----------------------------------------------------------------------------
    DECLARE @ViewCursor CURSOR;
    DECLARE @ViewID INT, @ViewName SYSNAME;

    SET @ViewCursor = CURSOR FAST_FORWARD FOR
        SELECT tv.ViewID, v.Name
        FROM TestViews tv
        INNER JOIN [Views] v ON tv.ViewID = v.ViewID
        WHERE tv.TestID = @TestID;

    OPEN @ViewCursor;
    FETCH NEXT FROM @ViewCursor INTO @ViewID, @ViewName;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        DECLARE @ViewStart DATETIME = GETDATE();
        DECLARE @sqlView NVARCHAR(MAX) = N'SELECT * FROM ' + QUOTENAME(@ViewName);

        EXEC(@sqlView);

        DECLARE @ViewEnd DATETIME = GETDATE();

        INSERT INTO TestRunViews (TestRunID, ViewID, StartAt, EndAt)
        VALUES (@TestRunID, @ViewID, @ViewStart, @ViewEnd);

        FETCH NEXT FROM @ViewCursor INTO @ViewID, @ViewName;
    END

    CLOSE @ViewCursor;
    DEALLOCATE @ViewCursor;

    ----------------------------------------------------------------------------
    -- 5) Update End Time in TestRuns
    ----------------------------------------------------------------------------
    SET @EndTime = GETDATE();
    UPDATE TestRuns
    SET EndAt = @EndTime
    WHERE TestRunID = @TestRunID;

    SELECT @TestRunID AS [TestRunID];
END;
GO

EXEC spRunTest 
    @TestID = 1, 
    @Description = N'Test';

SELECT * FROM TestRuns
SELECT * FROM TestRunTables
SELECT * FROM TestRunViews 

--lab 5
--a)
IF OBJECT_ID('dbo.fnIsValidSalary', 'FN') IS NOT NULL
    DROP FUNCTION dbo.fnIsValidSalary;
GO

CREATE FUNCTION dbo.fnIsValidSalary
(
    @Salary DECIMAL(10,2)
)
RETURNS BIT
AS
BEGIN
    DECLARE @IsValid BIT = 0;

    -- Example rule: salary must be greater than 0
    IF (@Salary > 0)
        SET @IsValid = 1;

    RETURN @IsValid;
END;

IF OBJECT_ID('dbo.fnIsValidRating', 'FN') IS NOT NULL
    DROP FUNCTION dbo.fnIsValidRating;
GO

CREATE FUNCTION dbo.fnIsValidRating
(
    @Rating INT
)
RETURNS BIT
AS
BEGIN
    DECLARE @IsValid BIT = 0;

    -- Example rule: rating must be between 1 and 100
    IF (@Rating >= 1 AND @Rating <= 100)
        SET @IsValid = 1;

    RETURN @IsValid;
END;

IF OBJECT_ID('dbo.spInsertStaffAndReport', 'P') IS NOT NULL
    DROP PROCEDURE dbo.spInsertStaffAndReport;
GO

IF OBJECT_ID('dbo.spInsertStaffAndReport','P') IS NOT NULL
    DROP PROCEDURE dbo.spInsertStaffAndReport;
GO

CREATE PROCEDURE dbo.spInsertStaffAndReport
(
    @FirstName       VARCHAR(50),
    @LastName        VARCHAR(50),
    @Role            VARCHAR(50),
    @Salary          DECIMAL(10,2),
    @ReportDate      DATE,
    @PlayerObserved  VARCHAR(100),
    @PotentialRating INT
)
AS
BEGIN
    SET NOCOUNT ON;

    -- Validate salary via fnIsValidSalary
    IF (SELECT dbo.fnIsValidSalary(@Salary)) = 0
    BEGIN
        RAISERROR('Invalid salary (must be > 0).', 16, 1);
        RETURN;
    END

    -- Validate potential rating via fnIsValidRating
    IF (SELECT dbo.fnIsValidRating(@PotentialRating)) = 0
    BEGIN
        RAISERROR('Invalid potential rating (must be between 1 and 100).', 16, 1);
        RETURN;
    END

    ----------------------------------------------------------------------------
    -- Generate a new StaffID by "MAX + 1" (since StaffID is not IDENTITY)
    ----------------------------------------------------------------------------
    DECLARE @NewStaffID INT;
    SELECT @NewStaffID = COALESCE(MAX(StaffID), 0) + 1
    FROM Staff;  -- no WHERE, so it finds the max ID in the entire table

    ----------------------------------------------------------------------------
    -- Insert into the parent table (Staff) with our manually generated @NewStaffID
    ----------------------------------------------------------------------------
    INSERT INTO Staff (StaffID, FirstName, LastName, Role, Salary)
    VALUES (@NewStaffID, @FirstName, @LastName, @Role, @Salary);

    ----------------------------------------------------------------------------
    -- Generate a new ReportID (also "MAX + 1") for ScoutingReports
    ----------------------------------------------------------------------------
    DECLARE @NewReportID INT;
    SELECT @NewReportID = COALESCE(MAX(ReportID), 0) + 1
    FROM ScoutingReports;

    ----------------------------------------------------------------------------
    -- Insert into the child table (ScoutingReports), referencing @NewStaffID
    ----------------------------------------------------------------------------
    INSERT INTO ScoutingReports (ReportID, ScoutDate, PlayerObserved, PotentialRating, ScoutID)
    VALUES (@NewReportID, @ReportDate, @PlayerObserved, @PotentialRating, @NewStaffID);

    PRINT 'Success: A new Staff member and one ScoutingReport record have been inserted.';
END;
GO



EXEC spInsertStaffAndReport
    @FirstName       = 'Raul',
    @LastName        = 'Parau',
    @Role            = 'Head Scout',
    @Salary          = 2500.00,
    @ReportDate      = '2025-01-15',
    @PlayerObserved  = 'Lionel Messi',
    @PotentialRating = 99;

select * from Staff;
select * from ScoutingReports;

--b)

drop view vw_TrainingDetails;
CREATE VIEW vw_TrainingDetails
AS
SELECT 
    p.PlayerID,
    p.FirstName      AS PlayerFirstName,
    p.LastName       AS PlayerLastName,
    pt.PerformanceRating,
    ts.SessionDate,
    ts.Duration,
    ts.FocusArea,
    c.CoachID,
    c.FirstName      AS CoachFirstName,
    c.LastName       AS CoachLastName,
    c.TrainingType
FROM Players p
JOIN PlayerTraining pt 
    ON p.PlayerID = pt.PlayerID
JOIN TrainingSessions ts
    ON pt.SessionID = ts.SessionID
JOIN Coaches c
    ON ts.SessionID = c.SessionID;

SELECT 
    CONCAT(PlayerFirstName, ' ', PlayerLastName) AS PlayerName,
    PerformanceRating,
    SessionDate,
    CONCAT(CoachFirstName, ' ', CoachLastName) AS CoachName,
    TrainingType
FROM vw_TrainingDetails
WHERE PerformanceRating >= 8
ORDER BY SessionDate DESC;

select *  from PlayerTraining


--c)

CREATE TABLE dbo.AuditLog
(
    AuditLogID    INT IDENTITY(1,1) PRIMARY KEY,
    ActionDate    DATETIME NOT NULL,
    ActionType    VARCHAR(10) NOT NULL,  -- 'INSERT', 'UPDATE', 'DELETE'
    TableName     SYSNAME NOT NULL,      -- e.g. 'Staff'
    RowsAffected  INT NOT NULL
);

CREATE TRIGGER dbo.tr_Staff_Audit
ON dbo.Staff
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @ActionType   VARCHAR(10);
    DECLARE @RowsAffected INT = 0;
    DECLARE @TableName    SYSNAME = 'Staff';

    /*
      We can detect the operation by checking which pseudo-tables have rows:
      - INSERT ONLY: 'inserted' has rows, 'deleted' is empty
      - DELETE ONLY: 'deleted'  has rows, 'inserted' is empty
      - UPDATE: both 'inserted' AND 'deleted' have rows
    */

    IF EXISTS (SELECT 1 FROM inserted) AND NOT EXISTS (SELECT 1 FROM deleted)
    BEGIN
        -- INSERT
        SET @ActionType = 'INSERT';
        SELECT @RowsAffected = COUNT(*) FROM inserted;
    END
    ELSE IF EXISTS (SELECT 1 FROM inserted) AND EXISTS (SELECT 1 FROM deleted)
    BEGIN
        -- UPDATE
        SET @ActionType = 'UPDATE';
        SELECT @RowsAffected = COUNT(*) FROM inserted; 
          -- or from deleted; typically same count
    END
    ELSE IF NOT EXISTS (SELECT 1 FROM inserted) AND EXISTS (SELECT 1 FROM deleted)
    BEGIN
        -- DELETE
        SET @ActionType = 'DELETE';
        SELECT @RowsAffected = COUNT(*) FROM deleted;
    END

    -- Insert a row into AuditLog capturing the event details
    INSERT INTO dbo.AuditLog
    (
        ActionDate,
        ActionType,
        TableName,
        RowsAffected
    )
    VALUES
    (
        GETDATE(),
        @ActionType,
        @TableName,
        @RowsAffected
    );
END;

INSERT INTO dbo.Staff (StaffID, FirstName, LastName, Role, Salary)
VALUES (1000, 'John', 'Smith', 'Coach', 2000.00);

SELECT * FROM dbo.AuditLog ORDER BY AuditLogID DESC;

UPDATE dbo.Staff
SET Salary = 2100.00
WHERE StaffID = 1000;

SELECT * FROM dbo.AuditLog ORDER BY AuditLogID DESC;

DELETE FROM dbo.Staff
WHERE StaffID = 1000;

SELECT * FROM dbo.AuditLog ORDER BY AuditLogID DESC;

