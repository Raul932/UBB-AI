
CREATE TABLE ArchaeologicalSite (
    site_id   INT IDENTITY(1,1) PRIMARY KEY,
    latitude  DECIMAL(8,5) NOT NULL CHECK (latitude BETWEEN -90 AND 90),
    longitude DECIMAL(8,5) NOT NULL CHECK (longitude BETWEEN -180 AND 180)
);

CREATE TABLE Collections (
    collection_id   INT IDENTITY(1,1) PRIMARY KEY,
    collection_name            VARCHAR(255) NOT NULL,
    epoch           VARCHAR(255) NOT NULL,
    visitation_fee  DECIMAL(8,2) NOT NULL
);

CREATE TABLE Room (
    room_id     INT IDENTITY(1,1) PRIMARY KEY,
    temperature DECIMAL(5,2) NOT NULL,
    humidity    DECIMAL(5,2) NOT NULL
);

CREATE TABLE Exhibit (
    inventory_name VARCHAR(255) PRIMARY KEY,
    site_id        INT NOT NULL,
    collection_id  INT NOT NULL,
    room_id        INT NOT NULL,
    FOREIGN KEY (site_id)       REFERENCES ArchaeologicalSite(site_id),
    FOREIGN KEY (collection_id) REFERENCES Collections(collection_id),
    FOREIGN KEY (room_id)       REFERENCES Room(room_id)
);

CREATE TABLE Ticket (
    ticket_id    INT IDENTITY(1,1) PRIMARY KEY,
    visitor_name VARCHAR(255) NOT NULL,
    is_student   BIT NOT NULL
);

--many to many
CREATE TABLE TicketCollection (
    ticket_id     INT NOT NULL,
    collection_id INT NOT NULL,
    PRIMARY KEY (ticket_id, collection_id),
    FOREIGN KEY (ticket_id)     REFERENCES Ticket(ticket_id),
    FOREIGN KEY (collection_id) REFERENCES Collections(collection_id)
);


CREATE PROCEDURE InsertNewExhibit
  @inventory_name VARCHAR(255),
  @latitude       DECIMAL(8,5),
  @longitude      DECIMAL(8,5),
  @collection_id  INT,
  @room_id        INT
AS
BEGIN

    -- message if it is empty
    IF NOT EXISTS (
        SELECT 1
        FROM ArchaeologicalSite
        WHERE latitude = @latitude
          AND longitude = @longitude
    )
    BEGIN
        PRINT 'No archeological site found.';
        RETURN;
    END

    -- insert new exhibit
    INSERT INTO Exhibit (inventory_name, site_id, collection_id, room_id)
    SELECT 
        @inventory_name,
        site_id,
        @collection_id,
        @room_id
    FROM ArchaeologicalSite
    WHERE latitude = @latitude
      AND longitude = @longitude;

    PRINT 'Exhibit inserted';
END;

INSERT INTO ArchaeologicalSite (latitude, longitude)
VALUES (10.12345, 20.12345);

INSERT INTO Collections (collection_name, epoch, visitation_fee)
VALUES ('Test Collection', 'Medieval', 15.00);

INSERT INTO Room (temperature, humidity)
VALUES (25.00, 40.00);

EXEC InsertNewExhibit
     @inventory_name = 'Exhibit006',
     @latitude       = 10.12345,
     @longitude      = 20.12345,
     @collection_id  = 1, 
     @room_id        = 1;

select * from Exhibit;
EXEC InsertNewExhibit
	@inventory_name = 'Exhibit007',
	@latitude = 11,
	@longitude = 12,
	@collection_id = 1,
	@room_id = 1;

SELECT *
FROM Exhibit
WHERE inventory_name = 'Exhibit005';



CREATE VIEW AvgTempHumidityByEpoch
AS
SELECT 
    c.epoch,
    AVG(r.temperature) AS AvgTemperature,
    AVG(r.humidity)    AS AvgHumidity
FROM Exhibit e
JOIN Collections c --to get epoch
    ON e.collection_id = c.collection_id 
JOIN Room r --to get temp and humidity
    ON e.room_id = r.room_id
GROUP BY c.epoch;

SELECT * 
FROM AvgTempHumidityByEpoch;

INSERT INTO ArchaeologicalSite (latitude, longitude)
VALUES
    (10.00000, 10.00000),
    (15.12345, 20.54321),
    (45.67890, 50.12345);

INSERT INTO Collections (collection_name, epoch, visitation_fee)
VALUES
    ('Painting',  'Prehistoric', 10.00),
    ('Vase', 'Ancient',     15.00),
    ('Weapons','Medieval',    20.00);

INSERT INTO Room (temperature, humidity)
VALUES
    (18.5, 45.0),
    (21.0, 50.0),
    (25.0, 40.0);

INSERT INTO Exhibit (inventory_name, site_id, collection_id, room_id)
VALUES
    ('Exhibit001', 1, 1, 1),
    ('Exhibit002', 1, 2, 2), 
    ('Exhibit003', 2, 2, 2), 
    ('Exhibit004', 3, 3, 3),
	('Exhibit005', 1, 4, 2);

