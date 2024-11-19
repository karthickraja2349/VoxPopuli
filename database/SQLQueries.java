package database;

public final class SQLQueries {
    //create electoral_area_table
    public static final String CREATE_ELECTORAL_AREA_TABLE =
       "CREATE TABLE IF NOT EXISTS electoral_area (" +
       "electoral_area_id SMALLINT PRIMARY KEY NOT NULL, " +
       "electoral_area_name VARCHAR(25), " +
       "male INT NOT NULL, " +
       "female INT NOT NULL, " +
       "transgender INT NOT NULL" +
       ")";

    //create candidate_table
    public static final String CREATE_CANDIDATE_TABLE =
      "CREATE TABLE IF NOT EXISTS candidate (" +
      "candidate_id INT PRIMARY KEY NOT NULL, " +
      "candidate_name VARCHAR(50) NOT NULL, " +
      "party VARCHAR(25) NOT NULL, " +
      "electoral_area_id INT, " +
      "candidate_age TINYINT NOT NULL, " +
      "gender VARCHAR(11) NOT NULL, " +
      "FOREIGN KEY (electoral_area_id) REFERENCES electoral_area(electoral_area_id)" +
      ")";
     
    //create voters_table 
    public static final String CREATE_VOTERS_TABLE =
      "CREATE TABLE IF NOT EXISTS voters (" +
      "voter_id VARCHAR(10) PRIMARY KEY NOT NULL, " +
      "voter_name VARCHAR(50) NOT NULL, " +
      "voter_age TINYINT NOT NULL, " +
      "gender VARCHAR(11) NOT NULL, " +
      "electoral_area_id SMALLINT, " +
      "FOREIGN KEY (electoral_area_id) REFERENCES electoral_area(electoral_area_id)" +
      ")";

     //create poll_table
     public static final String CREATE_POLL_TABLE =
       "CREATE TABLE IF NOT EXISTS poll (" +
       "voter_id VARCHAR(10) NOT NULL, " +
       "candidate_id INT NOT NULL, " +
       "electoral_area_id SMALLINT NOT NULL, " +
       "reason_for_vote VARCHAR(60) NOT NULL, " +
       "FOREIGN KEY (voter_id) REFERENCES voters(voter_id), " +
       "FOREIGN KEY (candidate_id) REFERENCES candidate(candidate_id), " +
       "FOREIGN KEY (electoral_area_id) REFERENCES electoral_area(electoral_area_id)" +
       ")";
     
    //create admin_table 
    public static final String CREATE_ADMIN_TABLE =
      "CREATE TABLE IF NOT EXISTS admin (" +
      "admin_id INT PRIMARY KEY AUTO_INCREMENT, " +
      "admin_name VARCHAR(50) NOT NULL, " +
      "admin_userName VARCHAR(10) NOT NULL, " +
      "admin_password VARCHAR(8) NOT NULL" +
      ")";
    
    //authenticate admin   
    public static final String ADMIN_CHECK = 
       "SELECT admin_username, admin_password FROM admin WHERE admin_username = ?";   
       
    //check presence of admin
    public static final String CHECK_ADMIN_EXISTENCE =
	"SELECT COUNT(*) FROM admin WHERE admin_Username = ?";        
    
    //add admin
    public static final String ADD_ADMIN = 
        "INSERT INTO admin(admin_name, admin_userName, admin_password) VALUES (?,?,?)";	   
           
    //check presence of area
    public static final String CHECK_AREA_EXISTENCE =
        "SELECT COUNT(*) FROM electoral_area WHERE electoral_area_name = ?";
    
    //add area
    public static final String ADD_ELECTORAL_AREA = 
        "INSERT INTO electoral_area(electoral_area_id ,electoral_area_name, male, female, transgender)VALUES (?,?,?,?,?)";
     
    //check presence of voter    
    public static final String CHECK_VOTER_EXISTENCE = 
        "SELECT COUNT(*) FROM voters WHERE voter_id = ?";
    
    //add voter
    public static final String ADD_VOTER =
        "INSERT INTO voters(voter_id, voter_name, voter_age , gender, electoral_area_id) VALUES(?,?,?,?,?)";   
    
    //check presence of candidate
    public static final String CHECK_CANDIDATE_EXISTENCE = 
        "SELECT COUNT(*) FROM candidate WHERE candidate_id = ?";      
    
    //add candidate
    public static final String ADD_CANDIDATE = 
        "INSERT INTO candidate(candidate_id, candidate_name, party, electoral_area_id, candidate_age, gender)VALUES(?,?,?,?,?,?)";       
    
    //view particular candidate without votes 
    public static final String VIEW_CANDIDATE = 
        "SELECT candidate_id, candidate_name, candidate_age, party, gender, electoral_area_id " +
        "FROM candidate " +
        "WHERE candidate_id = ? " +
        "AND electoral_area_id = ? " +
        "AND party = ?";
        
    //view all candidates    
    public static final String VIEW_ALL_CANDIDATES =
       "SELECT candidate_id, candidate_name, candidate_age, party , gender , electoral_area_id FROM candidate";    
    
    //find winner
    public static final String FIND_WINNER_IN_AN_AREA = 
       "SELECT c.candidate_id, c.candidate_name, c.party, COUNT(*) AS vote_count " +
       "FROM poll p " +  
       "JOIN candidate c ON p.candidate_id = c.candidate_id " + 
       "WHERE p.electoral_area_id = ? " + 
       "GROUP BY c.candidate_id " + 
       "ORDER BY vote_count DESC " + 
       "LIMIT 1";
    
    //find loser   
    public static final String FIND_WEAKER_IN_AN_AREA = 
       "SELECT c.candidate_id, c.candidate_name, c.party, COUNT(*) AS vote_count " +
       "FROM poll p " +
       "JOIN candidate c ON p.candidate_id = c.candidate_id " +
       "WHERE p.electoral_area_id = ? " +
       "GROUP BY c.candidate_id " +
       "ORDER BY vote_count ASC " +
       "LIMIT 1 ";
    
    //find popular party in the district
    public static final String FIND_POPULAR_PARTY = 
        "SELECT c.party , COUNT(*) AS total_votes " +
        "FROM poll p " + 
        "JOIN candidate c ON p.candidate_id = c.candidate_id " +
        "GROUP BY c.party ";
          
    //who votes for whom
    public static final String WHO_VOTES_FOR_WHOM =
        "SELECT v.voter_name, p.voter_id , c.candidate_name , c.party " +
        "FROM poll p " +
        "JOIN candidate c ON p.candidate_id = c.candidate_id " +
        "JOIN voters v ON p.voter_id = v.voter_id ";    
        
    //view candidates along with their votes    
    public static final String VIEW_CANDIDATES_OF_THE_PARTY = 
        "SELECT c.candidate_id, c.candidate_name, c.party, e.electoral_area_name, COUNT(p.voter_id) AS votes_received " +
        "FROM candidate c " +
        "LEFT JOIN poll p ON c.candidate_id = p.candidate_id " +
        "LEFT JOIN electoral_area e ON c.electoral_area_id = e.electoral_area_id " +
        "WHERE c.party = ? " +
        "GROUP BY c.candidate_id, c.candidate_name, c.party , e.electoral_area_name " ;
        
    //voters does not vote    
    public static final String VOTERS_DOESNOT_VOTE =
        "SELECT v.voter_id, v.voter_name, v.electoral_area_id "+
        "FROM voters v " +
        "LEFT JOIN poll p ON v.voter_id = p.voter_id " +
        "WHERE p.voter_id IS NULL " ;
        
/*
   CREATE TABLE IF NOT EXISTS electoral_area (
    electoral_area_id SMALLINT PRIMARY KEY NOT NULL,
    electoral_area_name VARCHAR(25),
    male INT NOT NULL,
    female INT NOT NULL,
    transgender INT NOT NULL
);

CREATE TABLE IF NOT EXISTS candidate (
    candidate_id smallint PRIMARY KEY NOT NULL,
    candidate_name VARCHAR(50) NOT NULL,
    party VARCHAR(25) NOT NULL,
    electoral_area_id smallint,
    candidate_age TINYINT,
    gender VARCHAR(11),
    FOREIGN KEY (electoral_area_id) REFERENCES electoral_area(electoral_area_id)
);

CREATE TABLE IF NOT EXISTS voters (
    voter_id VARCHAR(10) PRIMARY KEY NOT NULL,
    voter_name VARCHAR(50) NOT NULL,
    voter_age TINYINT NOT NULL,
    gender VARCHAR(11) NOT NULL,
    electoral_area_id SMALLINT,
    FOREIGN KEY (electoral_area_id) REFERENCES electoral_area(electoral_area_id)
);

CREATE TABLE IF NOT EXISTS poll (
    voter_id VARCHAR(10) NOT NULL,
    candidate_id INT NOT NULL,
    electoral_area_id SMALLINT NOT NULL,
    FOREIGN KEY (voter_id) REFERENCES voters(voter_id),
    FOREIGN KEY (candidate_id) REFERENCES candidate(candidate_id),
    FOREIGN KEY (electoral_area_id) REFERENCES electoral_area(electoral_area_id)
);

CREATE TABLE IF NOT EXISTS admin (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    admin_name VARCHAR(50) NOT NULL,
    admin_userName VARCHAR(10) NOT NULL,
    admin_password VARCHAR(8) NOT NULL
);

--authenticate admin 
SELECT admin_userName, admin_password 
FROM admin 
WHERE admin_userName = ?;

--check presence of admin
SELECT COUNT(*) 
FROM admin 
WHERE admin_userName = ?;

--add admin
INSERT INTO admin(admin_name, admin_userName, admin_password) 
VALUES (?, ?, ?);

--check presence of area
SELECT COUNT(*) 
FROM electoral_area 
WHERE electoral_area_name = ?;

--add area
INSERT INTO electoral_area(electoral_area_id, electoral_area_name, male, female, transgender)
VALUES (?, ?, ?, ?, ?);


--check presence of voter    
SELECT COUNT(*) 
FROM voters 
WHERE voter_id = ?;

--add voter
INSERT INTO voters(voter_id, voter_name, voter_age, gender, electoral_area_id) 
VALUES (?, ?, ?, ?, ?);

--check presence of candidate
SELECT COUNT(*) 
FROM candidate 
WHERE candidate_id = ?;

--add candidate
INSERT INTO candidate(candidate_id, candidate_name, party, electoral_area_id, candidate_age, gender)
VALUES (?, ?, ?, ?, ?, ?);

--view particular candidate without votes 
SELECT candidate_id, candidate_name, candidate_age, party, gender, electoral_area_id
FROM candidate 
WHERE candidate_id = ? 
AND electoral_area_id = ? 
AND party = ?;

--view all candidates 
SELECT candidate_id, candidate_name, candidate_age, party, gender, electoral_area_id 
FROM candidate;

--find winner
SELECT c.candidate_id, c.candidate_name, c.party, COUNT(*) AS vote_count
FROM poll p
JOIN candidate c ON p.candidate_id = c.candidate_id
WHERE p.electoral_area_id = ?
GROUP BY c.candidate_id
ORDER BY vote_count DESC
LIMIT 1;

--find loser 
SELECT c.candidate_id, c.candidate_name, c.party, COUNT(*) AS vote_count
FROM poll p
JOIN candidate c ON p.candidate_id = c.candidate_id
WHERE p.electoral_area_id = ?
GROUP BY c.candidate_id
ORDER BY vote_count ASC
LIMIT 1;

--find popular party in the district
SELECT c.party, COUNT(*) AS total_votes
FROM poll p
JOIN candidate c ON p.candidate_id = c.candidate_id
GROUP BY c.party;





--who votes for whom
SELECT v.voter_name, p.voter_id, c.candidate_name, c.party
FROM poll p
JOIN candidate c ON p.candidate_id = c.candidate_id
JOIN voters v ON p.voter_id = v.voter_id;


--view candidates along with their votes
SELECT c.candidate_id, c.candidate_name, c.party, e.electoral_area_name, COUNT(p.voter_id) AS votes_received
FROM candidate c
LEFT JOIN poll p ON c.candidate_id = p.candidate_id
LEFT JOIN electoral_area e ON c.electoral_area_id = e.electoral_area_id
WHERE c.party = ?
GROUP BY c.candidate_id, c.candidate_name, c.party, e.electoral_area_name;


--voters does not vote  
SELECT v.voter_id, v.voter_name, v.electoral_area_id
FROM voters v
LEFT JOIN poll p ON v.voter_id = p.voter_id
WHERE p.voter_id IS NULL;




*/           


       
/*
//vote for particular candidate   

SELECT v.voter_name, v.voter_id 
FROM voters v 
JOIN poll p ON v.voter_id = p.voter_id 
WHERE p.candidate_id = 61789;

//voters vote for particular candidate along with their area

SELECT v.voter_name, v.voter_id, e.electoral_area_name
FROM voters v
JOIN poll p ON v.voter_id = p.voter_id
JOIN electoral_area e ON e.electoral_area_id = p.electoral_area_id
WHERE p.candidate_id = 28716;

//voters vote for paticular party
SELECT v.voter_name, v.voter_id, e.electoral_area_name
FROM voters v
JOIN poll p ON v.voter_id = p.voter_id
JOIN candidate c ON p.candidate_id = c.candidate_id
JOIN electoral_area e ON e.electoral_area_id = p.electoral_area_id
WHERE c.party = 'ADMK';

//popular party in an area
SELECT c.party, COUNT(*) AS total_votes
FROM poll p
JOIN candidate c ON p.candidate_id = c.candidate_id
WHERE p.electoral_area_id = 222
GROUP BY c.party;

//voters vote in a particular constitutency
SELECT p.voter_id, v.voter_name, p.candidate_id, c.candidate_name, p.electoral_area_id, e.electoral_area_name
FROM poll p
JOIN voters v ON p.voter_id = v.voter_id
JOIN candidate c ON p.candidate_id = c.candidate_id
JOIN electoral_area e ON p.electoral_area_id = e.electoral_area_id where e.electoral_area_id = 223;

//voters in an area
SELECT electoral_area_id, COUNT(*) AS voter_count FROM voters GROUP BY electoral_area_id;

//voters vote for candidate
 SELECT COUNT(*) AS total_votes, v.voter_name, c.candidate_name FROM poll p JOIN voters v ON p.voter_id = v.voter_id JOIN candidate c ON p.candidate_id = c.candidate_id JOIN electoral_area e ON p.electoral_area_id = e.electoral_area_id WHERE e.electoral_area_id = 220 AND c.candidate_id = 1943 GROUP BY c.candidate_id, v.voter_id, v.voter_name, c.candidate_name;


//winning percentage
SELECT 
    UPPER(c.party) AS party_name,
    COUNT(p.voter_id) AS total_votes_for_party,
    (COUNT(p.voter_id) / (SELECT COUNT(*) FROM poll) * 100) AS winning_percentage
FROM 
    poll p
JOIN 
    candidate c ON p.candidate_id = c.candidate_id
GROUP BY 
    c.party
ORDER BY 
    winning_percentage DESC;


   
INSERT INTO electoral_area(electoral_area_id, electoral_area_name, male, female, transgender)
VALUES (?, ?, ?, ?, ?);


SELECT 
    CASE 
        WHEN v.voter_age BETWEEN 18 AND 30 THEN '18-30'
        WHEN v.voter_age BETWEEN 30 AND 50 THEN '30-50'
        WHEN v.voter_age BETWEEN 50 AND 100 THEN '50-100'
        ELSE 'Other'
    END AS age_group,
    COUNT(*) AS total_votes
FROM voters v
JOIN poll p ON v.voter_id = p.voter_id
GROUP BY age_group
ORDER BY age_group;

        */ 
      


}

