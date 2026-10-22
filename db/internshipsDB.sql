-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema InternshipsModel
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `InternshipsModel` ;
-- -----------------------------------------------------
-- Schema InternshipsModel
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `InternshipsModel` DEFAULT CHARACTER SET utf8 ;
USE `InternshipsModel` ;

-- -----------------------------------------------------
-- Table `InternshipsModel`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `InternshipsModel`.`User` (
  `idUser` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(500) NOT NULL,
  `role` ENUM('STUDENT', 'FACULTY', 'COMPANY') NOT NULL,
  PRIMARY KEY (`idUser`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InternshipsModel`.`Student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `InternshipsModel`.`Student` (
  `firstName` VARCHAR(255) NOT NULL,
  `lastName` VARCHAR(255) NOT NULL,
  `indeks` VARCHAR(45) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `idFaculty` INT NOT NULL,
  `idUser` INT NOT NULL,
  PRIMARY KEY (`idUser`),
  INDEX `fk_Internship_Faculty1_idx` (`idFaculty` ASC) VISIBLE,
  CONSTRAINT `fk_Student_User`
    FOREIGN KEY (`idUser`)
    REFERENCES `InternshipsModel`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_Student_Faculty`
    FOREIGN KEY (`idFaculty`)
    REFERENCES `InternshipsModel`.`Faculty` (`idUser`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InternshipsModel`.`Faculty`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `InternshipsModel`.`Faculty` (
  `name` VARCHAR(500) NOT NULL,
  `idUser` INT NOT NULL,
  PRIMARY KEY (`idUser`),
  CONSTRAINT `fk_Faculty_User1`
    FOREIGN KEY (`idUser`)
    REFERENCES `InternshipsModel`.`User` (`idUser`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InternshipsModel`.`Company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `InternshipsModel`.`Company` (
  `idUser` INT NOT NULL,
  `name` VARCHAR(500) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `active` TINYINT(1) NOT NULL,
  `address` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`idUser`),
  CONSTRAINT `fk_Company_User1`
    FOREIGN KEY (`idUser`)
    REFERENCES `InternshipsModel`.`User` (`idUser`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InternshipsModel`.`Internship`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `InternshipsModel`.`Internship` (
  `idInternship` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `startDate` DATE NOT NULL,
  `endDate` DATE NOT NULL,
  `available` TINYINT(1) NOT NULL,
  `about` TEXT NOT NULL,
  `technologies` TEXT NOT NULL,
  `skills` TEXT NOT NULL,
  `idCompany` INT NOT NULL,
  `requirements` TEXT NOT NULL,
  PRIMARY KEY (`idInternship`),
  INDEX `fk_Internship_Company1_idx` (`idCompany` ASC) VISIBLE,
  CONSTRAINT `fk_Internship_Company1`
    FOREIGN KEY (`idCompany`)
    REFERENCES `InternshipsModel`.`Company` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InternshipsModel`.`CV`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `InternshipsModel`.`CV` (
  `idCV` INT NOT NULL AUTO_INCREMENT,
  `description` TEXT NULL,
  `imagePath` VARCHAR(500) NULL,
  `school` TEXT NOT NULL,
  `internships` TEXT NULL,
  `experience` TEXT NULL,
  `contact` VARCHAR(255) NOT NULL,
  `interests` TEXT NOT NULL,
  `skills` TEXT NOT NULL,
  `firstName` VARCHAR(255) NOT NULL,
  `lastName` VARCHAR(255) NOT NULL,
  `training` TEXT NULL,
  `idStudent` INT NOT NULL,
  PRIMARY KEY (`idCV`),
  INDEX `fk_CV_Student1_idx` (`idStudent` ASC) VISIBLE,
  CONSTRAINT `fk_CV_Student1`
    FOREIGN KEY (`idStudent`)
    REFERENCES `InternshipsModel`.`Student` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InternshipsModel`.`Application`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `InternshipsModel`.`Application` (
  `idApplication` INT NOT NULL AUTO_INCREMENT,
  `status` ENUM('pending', 'declined', 'accepted') NOT NULL,
  `date` DATE NOT NULL,
  `idInternship` INT NOT NULL,
  `idStudent` INT NOT NULL,
  PRIMARY KEY (`idApplication`),
 UNIQUE (`idStudent`, `idInternship`),
  INDEX `fk_Application_Internship1_idx` (`idInternship` ASC) VISIBLE,
  INDEX `fk_Application_Student1_idx` (`idStudent` ASC) VISIBLE,
  CONSTRAINT `fk_Application_Internship1`
    FOREIGN KEY (`idInternship`)
    REFERENCES `InternshipsModel`.`Internship` (`idInternship`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Application_Student1`
    FOREIGN KEY (`idStudent`)
    REFERENCES `InternshipsModel`.`Student` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InternshipsModel`.`WorkEntry`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `InternshipsModel`.`WorkEntry` (
  `idWorkEntry` INT NOT NULL AUTO_INCREMENT,
  `activity` TEXT NOT NULL,
  `weekStart` DATE NOT NULL,
  `idInternship` INT NOT NULL,
  `idStudent` INT NOT NULL,
  PRIMARY KEY (`idWorkEntry`),
  
  INDEX `fk_WorkEntry_Internship1_idx` (`idInternship` ASC) VISIBLE,
  INDEX `fk_WorkEntry_Student1_idx` (`idStudent` ASC) VISIBLE,
  CONSTRAINT `fk_WorkEntry_Internship1`
    FOREIGN KEY (`idInternship`)
    REFERENCES `InternshipsModel`.`Internship` (`idInternship`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_WorkEntry_Student1`
    FOREIGN KEY (`idStudent`)
    REFERENCES `InternshipsModel`.`Student` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InternshipsModel`.`Grade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `InternshipsModel`.`Grade` (
  `idGrade` INT NOT NULL AUTO_INCREMENT,
  `value` INT NOT NULL,
  `idFaculty` INT NOT NULL,
  `idStudent` INT NOT NULL,
   `idInternship` INT NOT NULL,
  PRIMARY KEY (`idGrade`),
  UNIQUE (`idStudent`, `idInternship`),
  INDEX `fk_Grade_Faculty1_idx` (`idFaculty` ASC) VISIBLE,
  INDEX `fk_Grade_Student1_idx` (`idStudent` ASC) VISIBLE,
   INDEX `fk_Grade_Internship1_idx` (`idInternship` ASC) VISIBLE,
  CONSTRAINT `fk_Grade_Faculty1`
    FOREIGN KEY (`idFaculty`)
    REFERENCES `InternshipsModel`.`Faculty` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Grade_Student1`
    FOREIGN KEY (`idStudent`)
    REFERENCES `InternshipsModel`.`Student` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
     CONSTRAINT `fk_Grade_Internship1`
    FOREIGN KEY (`idInternship`)
    REFERENCES `InternshipsModel`.`Internship` (`idInternship`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InternshipsModel`.`Evaluation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `InternshipsModel`.`Evaluation` (
  `idEvaluation` INT NOT NULL AUTO_INCREMENT,
  `description` TEXT NOT NULL,
  `idCompany` INT NOT NULL,
  `idStudent` INT NOT NULL,
  `idInternship` INT NOT NULL,
  PRIMARY KEY (`idEvaluation`),
   UNIQUE (`idStudent`, `idInternship`),
  INDEX `fk_Evaluation_Company1_idx` (`idCompany` ASC) VISIBLE,
  INDEX `fk_Evaluation_Student1_idx` (`idStudent` ASC) VISIBLE,
  INDEX `fk_Evaluation_Internship1_idx` (`idInternship` ASC) VISIBLE,
  CONSTRAINT `fk_Evaluation_Company1`
    FOREIGN KEY (`idCompany`)
    REFERENCES `InternshipsModel`.`Company` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Evaluation_Student1`
    FOREIGN KEY (`idStudent`)
    REFERENCES `InternshipsModel`.`Student` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
     CONSTRAINT `fk_Evaluation_Internship1`
    FOREIGN KEY (`idInternship`)
    REFERENCES `InternshipsModel`.`Internship` (`idInternship`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO User (username, password, role) VALUES
('student1', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'STUDENT'),
('student2', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'STUDENT'),
('student3', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'STUDENT'),
('student4', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'STUDENT'),
('student5', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'STUDENT'),
('student6', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'STUDENT'),
('student7', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'STUDENT'),
('student8', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'STUDENT'),
('student9', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'STUDENT'),
('student10', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'STUDENT'),

('faculty1', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'FACULTY'),
('faculty2', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'FACULTY'),
('faculty3', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'FACULTY'),
('faculty4', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'FACULTY'),
('faculty5', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'FACULTY'),

('company1', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'COMPANY'),
('company2', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'COMPANY'),
('company3', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'COMPANY'),
('company4', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'COMPANY'),
('company5', '$2a$12$EnDqivKCOQO3rUHWRBdG3.1RAqMjsWY8uGSdUkAJg6/J3V.abxvIC', 'COMPANY');

INSERT INTO Faculty (idUser, name) VALUES
(11, 'Elektrotehnicki fakultet'),
(12, 'Prirodno-matematicki fakultet'),
(13, 'Ekonomski fakultet'),
(14, 'Masinski fakultet'),
(15, 'Medicinski fakultet');

INSERT INTO Company (idUser, name, email, active, address) VALUES
(16, 'Tech Solutions', 'tech@company.com', 1, 'Banja Luka'),
(17, 'Code Factory', 'code@company.com', 1, 'Sarajevo'),
(18, 'IT Systems', 'it@company.com', 1, 'Banja Luka'),
(19, 'SoftDev', 'soft@company.com', 1, 'Banja Luka'),
(20, 'DataTech', 'data@company.com', 1, 'Bijeljina');

INSERT INTO Student (idUser, firstName, lastName, indeks, email, idFaculty) VALUES
(1, 'Marko', 'Markovic', '1101/19', 'marko@mail.com', 11),
(2, 'Ana', 'Anic', '1101/19', 'ana@mail.com', 12),
(3, 'Ivan', 'Ivic', '1102/19', 'ivan@mail.com', 11),
(4, 'Sara', 'Saric', '1103/19', 'sara@mail.com', 13),
(5, 'Luka', 'Lukic', '1201/19', 'luka@mail.com', 11),
(6, 'Mihajlo', 'Ilic', '1202/19', 'mihajlo@mail.com', 12),
(7, 'Jelena', 'Anic', '1105/19', 'jelena@mail.com', 13),
(8, 'Janko', 'Popovic', '1301/19', 'janko@mail.com', 14),
(9, 'Kristina', 'Savic', '1302/19', 'kris@mail.com', 15),
(10, 'Jana', 'Savic', '1111/19', 'janaa@mail.com', 15);

INSERT INTO Internship 
(name, startDate, endDate, available, about, technologies, skills, idCompany, requirements) VALUES
('Backend praksa', '2026-03-01', '2026-06-01', 1, 'Backend razvoj', 'Java, Spring', 'OOP', 16, 'Osnove Jave'),
('Frontend praksa', '2026-03-01', '2026-06-01', 1, 'Frontend razvoj', 'React, JS', 'HTML/CSS', 17, 'Osnove weba'),
('Data praksa', '2026-04-01', '2026-07-01', 1, 'Rad sa podacima', 'Python, SQL', 'Analitika', 18, 'Osnove Pythona'),
('QA praksa', '2026-05-01', '2026-08-01', 1, 'Testiranje', 'Selenium', 'Testiranje softvera', 19, 'Osnove testiranja'),
('DevOps praksa', '2026-06-01', '2026-09-01', 1, 'DevOps poslovi', 'Docker', 'Linux', 20, 'Osnove Linuxa'),('Full Stack Development praksa', '2026-03-15', '2026-07-15', 1,
'Tokom ove prakse studenti će raditi na razvoju kompletnih web aplikacija koristeći moderne backend i frontend tehnologije. Fokus je na izgradnji REST API servisa, povezivanju sa bazama podataka i implementaciji responzivnog korisničkog interfejsa. Kandidati će učestvovati u timskom radu, code review procesu i agilnom načinu razvoja softvera.',
'Java, Spring Boot, Angular, PostgreSQL, REST',
'Napredno poznavanje OOP-a, rad sa bazama podataka, Git, timski rad',
20,
'Osnove Jave i JavaScript-a, razumijevanje HTTP protokola, rad sa Git-om'),

('Mobile Development praksa', '2026-04-01', '2026-08-01', 1,
'Praksa je usmjerena na razvoj mobilnih aplikacija za Android i iOS platforme. Stažisti će raditi na implementaciji novih funkcionalnosti, optimizaciji performansi i integraciji sa backend servisima. Poseban akcenat je na pisanju čistog i održivog koda, testiranju aplikacija i radu sa REST API servisima.',
'Kotlin, Swift, Flutter, Firebase, REST API',
'Rad sa mobilnim UI komponentama, debugovanje, verzionisanje koda',
19,
'Osnove programiranja, poznavanje bar jedne mobilne platforme, razumijevanje rada sa API-jem'),

('Cyber Security praksa', '2026-05-01', '2026-09-01', 1,
'Ova praksa pruža priliku studentima da se upoznaju sa osnovama i naprednim konceptima sajber sigurnosti. Učesnici će raditi na analizi sigurnosnih propusta, testiranju ranjivosti aplikacija i implementaciji sigurnosnih mjera. Praksa uključuje rad sa alatima za penetration testing i praćenje sigurnosnih incidenata.',
'Kali Linux, Wireshark, Metasploit, OWASP',
'Analitičko razmišljanje, rad u Linux okruženju, razumijevanje mreža',
18,
'Osnove mreža, poznavanje Linux sistema, interesovanje za sigurnost'),

('AI i Machine Learning praksa', '2026-04-15', '2026-08-15', 0,
'Studenti će tokom ove prakse raditi na razvoju i treniranju modela mašinskog učenja. Učesnici će analizirati skupove podataka, pripremati podatke za treniranje i evaluirati performanse modela. Poseban fokus je na praktičnoj primjeni algoritama u realnim poslovnim problemima i radu sa velikim količinama podataka.',
'Python, TensorFlow, Scikit-learn, Pandas, NumPy',
'Statistika, rad sa podacima, analitičko razmišljanje, osnovno poznavanje ML algoritama',
17,
'Osnove Pythona, linearna algebra, statistika'),

('Cloud Engineering praksa', '2026-06-15', '2026-10-15', 1,
'Praksa je namijenjena studentima koji žele steći praktično iskustvo u radu sa cloud infrastrukturom. Učesnici će učiti kako da postave, konfigurišu i održavaju cloud servise, automatizuju deployment procese i prate performanse sistema. Rad uključuje implementaciju CI/CD pipeline-a i upravljanje kontejnerima.',
'AWS, Azure, Docker, Kubernetes, CI/CD',
'Rad sa cloud servisima, skriptovanje, DevOps principi',
16,
'Osnove Linuxa, rad sa komandnom linijom, osnovno znanje mreža'),
('Embedded Systems praksa', '2026-08-15', '2026-11-15', 1, 
'Rad na razvoju softvera za mikrokontrolere i ugrađene sisteme. Fokus na optimizaciju koda i direktnu interakciju sa hardverom.', 
'C, C++, RTOS, STM32', 'Poznavanje arhitekture računara, debagovanje hardvera', 
16, 'Osnovno poznavanje elektronike i C programiranja'),

('Digital Marketing & SEO praksa', '2026-05-15', '2026-08-15', 1, 
'Analiza tržišta, optimizacija sadržaja za pretraživače i vođenje kampanja na društvenim mrežama.', 
'Google Analytics, SEMrush, Meta Ads', 'Pisanje sadržaja, analitika, SEO strategija', 
18, 'Odlično poznavanje engleskog jezika, kreativnost'),

('Blockchain Development praksa', '2026-09-01', '2026-12-01', 1, 
'Istraživanje i razvoj pametnih ugovora i decentralizovanih aplikacija (DApps).', 
'Solidity, Ethereum, Web3.js', 'Kriptografija, logika pametnih ugovora', 
20, 'Jako poznavanje JavaScript-a ili C++-a'),

('Software QA Automation praksa', '2026-07-15', '2026-10-15', 0, 
'Pisanje automatizovanih testova za kompleksne web sisteme. Fokus na regresiono testiranje i CI/CD integraciju.', 
'Java, Selenium, TestNG, Jenkins', 'Preciznost, analitičnost, pisanje test planova', 
19, 'Osnove OOP-a, razumijevanje životnog ciklusa softvera'),

('Network Administration praksa', '2026-06-01', '2026-09-01', 1, 
'Konfiguracija mrežne opreme, održavanje servera i nadzor mrežnog saobraćaja u realnom vremenu.', 
'Cisco IOS, MikroTik, VPN, Firewall', 'Mrežni protokoli (TCP/IP), rješavanje mrežnih problema', 
18, 'Poznavanje osnova računarskih mreža (CCNA nivo)'),

('Business Intelligence praksa', '2026-08-01', '2026-11-01', 1, 
'Transformacija sirovih podataka u poslovne izvještaje. Rad na vizuelizaciji podataka i ETL procesima.', 
'Power BI, Tableau, SQL Server', 'Data modeling, komunikacija, vizuelizacija', 
16, 'Napredno poznavanje Excel-a i osnova SQL-a'),

('Cyber Forensics praksa', '2026-10-01', '2026-12-30', 1, 
'Analiza digitalnih tragova nakon sigurnosnih incidenata. Prikupljanje dokaza i dokumentovanje napada.', 
'Autopsy, Wireshark, EnCase', 'Istraživačke vještine, razumijevanje operativnih sistema', 
19, 'Završen kurs iz sajber sigurnosti ili srodna oblast'),

('Game Development praksa', '2026-07-01', '2026-11-01', 1, 
'Razvoj mehanika za 2D i 3D igre. Rad u timu sa dizajnerima i animatorima.', 
'Unity, C#, Blender', 'Matematika za igre, fizika u igrama', 
17, 'Odlično poznavanje C# i strast prema video igrama'),

('Product Management praksa', '2026-09-15', '2026-12-15', 1, 
'Koordinacija između razvojnog tima i klijenata. Definisanje funkcionalnosti proizvoda i roadmap-a.', 
'Jira, Trello, Confluence', 'Agilne metodologije (Scrum), liderstvo, komunikacija', 
20, 'Sposobnost organizacije i osnovno poznavanje IT procesa');


INSERT INTO CV (idStudent, firstName, lastName, contact, school, skills, interests, description) VALUES
(1, 'Marko', 'Markovic', '065/111-222', 'ETF Banja Luka', 'Java, SQL, Git', 'Backend development, Microservices', 'Student 4. godine, fokusiran na enterprise rješenja.'),
(2, 'Ana', 'Anic', '065/222-333', 'PMF Banja Luka', 'Python, R, SQL', 'Data Science, Statistics', 'Zanima me obrada podataka i mašinsko učenje.'),
(3, 'Ivan', 'Ivic', '065/333-444', 'ETF Banja Luka', 'C++, Linux, Docker', 'Cloud computing, System admin', 'Sistemski inženjer u nastajanju.'),
(6, 'Mihajlo', 'Ilic', '066/444-555', 'PMF Banja Luka', 'Python, TensorFlow', 'Artificial Intelligence', 'Fokus na neuronske mreže.');

INSERT INTO Application (status, date, idInternship, idStudent) VALUES
('accepted', '2026-02-15', 1, 1),
('accepted', '2026-03-20', 3, 2), 
('accepted', '2026-05-10', 10, 3), 
('declined', '2026-02-20', 1, 5),
('pending', '2026-03-01', 9, 6);


INSERT INTO WorkEntry (activity, weekStart, idInternship, idStudent) VALUES
('Upoznavanje sa timom i postavljanje razvojnog okruženja (Spring Boot).', '2026-03-02', 1, 1),
('Implementacija prvih REST endpointa za CRUD operacije korisnika.', '2026-03-09', 1, 1),
('Analiza baze podataka i pisanje kompleksnih SQL upita za izvještaje.', '2026-04-06', 3, 2),
('Konfiguracija Docker kontejnera za mikroservise.', '2026-06-20', 10, 3);

INSERT INTO Evaluation (description, idCompany, idStudent, idInternship) VALUES
('Marko je pokazao izuzetno poznavanje Java programskog jezika i brzo se uklopio u tim.', 16, 1, 1),
('Ana je veoma pedantna u radu sa podacima, ali bi trebala više komunicirati sa kolegama.', 18, 2, 3);


INSERT INTO Grade (value, idFaculty, idStudent, idInternship) VALUES
(10, 11, 1, 1),
(9, 12, 2, 3);