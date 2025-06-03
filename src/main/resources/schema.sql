CREATE TABLE Adresa (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        strada VARCHAR(100) NOT NULL,
                        numar VARCHAR(10) NOT NULL,
                        oras VARCHAR(50) NOT NULL,
                        cod_postal VARCHAR(10) NOT NULL
);
CREATE TABLE Utilizator (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nume VARCHAR(20) NOT NULL ,
    email VARCHAR(50) NOT NULL UNIQUE ,
    telefon VARCHAR(20) NOT NULL ,
    adresa_id INT NOT NULL ,
    FOREIGN KEY (adresa_id) REFERENCES Adresa(id)
);

CREATE TABLE Restaurant(
    id INT PRIMARY KEY AUTO_INCREMENT,
    rating DOUBLE DEFAULT 0,
    nume VARCHAR(50) NOT NULL ,
    tip_bucatarie VARCHAR(50) NOT NULL,
    adresa_id INT NOT NULL ,
    foreign key (adresa_id) REFERENCES Adresa(id)
);

CREATE TABLE Produs (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        nume VARCHAR(100) NOT NULL,
                        pret DOUBLE NOT NULL,
                        descriere TEXT,
                        restaurant_id INT NOT NULL,
                        FOREIGN KEY (restaurant_id) REFERENCES Restaurant(id)
);

CREATE TABLE Comanda (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         utilizator_id INT NOT NULL,
                         restaurant_id INT NOT NULL,
                         cost DOUBLE NOT NULL,
                         status VARCHAR(20) NOT NULL,
                         data_plasare DATETIME NOT NULL,
                         data_livrare DATETIME,
                         FOREIGN KEY (utilizator_id) REFERENCES Utilizator(id),
                         FOREIGN KEY (restaurant_id) REFERENCES Restaurant(id)
);

CREATE TABLE Comanda_Produs (
                                comanda_id INT NOT NULL,
                                produs_id INT NOT NULL,
                                cantitate INT DEFAULT 1,
                                PRIMARY KEY (comanda_id, produs_id),
                                FOREIGN KEY (comanda_id) REFERENCES Comanda(id),
                                FOREIGN KEY (produs_id) REFERENCES Produs(id)
);

CREATE TABLE Recenzie (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          comanda_id INT NOT NULL,
                          nota DOUBLE NOT NULL,
                          comentariu TEXT,
                          data_recenzie DATETIME NOT NULL,
                          utilizator_id INT,
                          FOREIGN KEY (comanda_id) REFERENCES Comanda(id),
                          FOREIGN KEY (utilizator_id) REFERENCES Utilizator(id)
                      );